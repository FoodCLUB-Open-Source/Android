package android.kotlin.foodclub.viewModels.home

import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.profile.UserDetailsModel
import android.kotlin.foodclub.domain.models.profile.UserPosts
import android.kotlin.foodclub.domain.models.profile.UserProfile
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.room.entity.ProfileModel
import android.kotlin.foodclub.utils.helpers.StoreData
import android.kotlin.foodclub.utils.helpers.UiEvent
import android.net.Uri
import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val profileRepository: ProfileRepository,
    val sessionCache: SessionCache,
    val storeData: StoreData
) : ViewModel() {

    private val _myUserId = MutableStateFlow(
        sessionCache.getActiveSession()?.sessionUser?.userId ?: 0
    )
    val myUserId: StateFlow<Long> get() = _myUserId

    private val _profileModel = MutableStateFlow<UserProfile?>(null)
    val profileModel: StateFlow<UserProfile?> get() = _profileModel

    private var _userDetails = MutableStateFlow<UserDetailsModel?>(null)

    private val _bookmarkedPosts = MutableStateFlow<List<UserPosts>>(listOf())
    val bookmarkedPosts: StateFlow<List<UserPosts>> get() = _bookmarkedPosts

    private val _userPosts = MutableStateFlow<List<UserPosts>>(listOf())
    val userPosts: StateFlow<List<UserPosts>> get() = _userPosts


    private val _error = MutableStateFlow("")
    val error: StateFlow<String> get() = _error

    private val _isFollowedByUser = MutableStateFlow(false)
    val isFollowedByUser: StateFlow<Boolean> get() = _isFollowedByUser

    // Below variables are related to DeleteRecipeView Composable
    private val _postData = MutableStateFlow<VideoModel?>(null)
    val postData: StateFlow<VideoModel?> get() = _postData

    private val _uiEvent =  MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        if(sessionCache.getActiveSession()?.sessionUser?.userId == null) {
            sendUiEvent(UiEvent.Navigate(Graph.AUTHENTICATION))
        } else {
            val id = sessionCache.getActiveSession()!!.sessionUser.userId
            getProfileModel(id)
            getBookmarkedPosts(id)
            getUserDetails(id)
        }
    }


    fun setUser(newUserId: Long) {
        if(newUserId != sessionCache.getActiveSession()!!.sessionUser.userId) {
            getProfileModel(
                if(newUserId != 0L) newUserId
                else sessionCache.getActiveSession()!!.sessionUser.userId
            )
        }
    }

    private fun getProfileModel(userId: Long) {
        viewModelScope.launch {
            when(val resource = profileRepository.retrieveProfileData(userId)) {
                is Resource.Success -> {
                    _error.value = ""
                    _profileModel.value = resource.data
                    resource.data.let { user->
                        _userPosts.value = user!!.userPosts
                    }
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                }
            }
        }
    }

    /**
     * to make offline-first app,
     * store room response data to profileModel or userDetails(not used right now)
     * populate UI with room response data
     * then check for internet connection(utils.helpers.ConnectivityUtils) and make api call
     * compare the room db response and api call response
     * update room db with profileModel or userDetails values
     * */
    private fun retrieveLocalUserDetails(id: Long){
        viewModelScope.launch {
            when(val dbResponse = profileRepository.retrieveLocalUserDetails(id)){
                is Resource.Success -> {
                    dbResponse.data!!.collect {
                        Log.i("MYTAG","room db response success $it")
                    }
                }
                is Resource.Error -> {
                    val dbResponseError = dbResponse.message
                    Log.e("MYTAG","error room db response $dbResponseError")
                }
            }
        }
    }

    private fun insertLocalUserDetails(profileModel: ProfileModel){
        viewModelScope.launch {
            profileRepository.insertLocalUserDetails(profileModel)
        }
    }

    private fun compareLocalAndRemoteData(remote: UserDetailsModel){
        // or profileModel.value
        // this can further be detailed for only updating a single data
        // instead of adding all the remote data values to ProfileModel
        if (_userDetails.value != remote){
            viewModelScope.launch {
                profileRepository.updateLocalProfileData(
                    ProfileModel(
                        remote.id,
                        remote.userName,
                        remote.email,
                        remote.profilePicture,
                        remote.createdAt,
                    )
                )
            }
        }
    }

    private fun getUserDetails(id: Long){
        viewModelScope.launch {
            when(val resource = profileRepository.retrieveUserDetails(id)){
                is Resource.Success -> {
                    _userDetails.value = resource.data
                    Log.i("MYTAG","getUserDetails success: ${resource.data}")
                }
                is Resource.Error -> {
                    Log.i("MYTAG","getUserDetails failed: ${resource.message}")
                }
            }
        }
    }


    fun updateUserProfileImage(id: Long, file: File, uri: Uri) {
        viewModelScope.launch {
            when(val resource = profileRepository.updateUserProfileImage(id, file)){
                is Resource.Success-> {
                    Log.i("MYTAG", "SUCCESSFULLY UPDATED IMAGE ${resource.data}")
                }
                is Resource.Error -> {
                    Log.i("MYTAG", "ERROR UPDATING IMAGE ${resource.message}")
                }
            }
        }
        _userDetails.value!!.profilePicture = uri.toString()
        Log.i("MYTAG","USER UPDATED IMG: ${_userDetails.value!!.profilePicture}")
    }

    private fun getBookmarkedPosts(userId: Long) {
        viewModelScope.launch {
            when(val resource = profileRepository.retrieveBookmarkedPosts(userId)) {
                is Resource.Success -> {
                    _error.value = ""
                    _bookmarkedPosts.value = resource.data!!
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                }
            }
        }
    }

    private fun getListFromDatabase() {

        /// Hardcoded List used, need to fetch from API---->


    }

    fun unfollowUser(followerId: Long, userId: Long) {
        viewModelScope.launch {
            when(val resource = profileRepository.unfollowUser(followerId, userId)) {
                is Resource.Success -> {
                    _error.value = ""
                    _isFollowedByUser.value = false
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                }
            }
        }
    }

    fun followUser(followerId: Long, userId: Long) {
        viewModelScope.launch {
            when(val resource = profileRepository.followUser(followerId, userId)) {
                is Resource.Success -> {
                    _error.value = ""
                    _isFollowedByUser.value = true
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                }
            }
        }
    }

    fun isFollowedByUser(followerId: Long, userId: Long) {
        viewModelScope.launch {
            when(val resource = profileRepository.retrieveProfileFollowers(userId)) {
                is Resource.Success -> {
                    _error.value = ""
                    _isFollowedByUser.value = resource.data!!.any {
                        it.userId.toLong() == followerId
                    }
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                }
            }
        }
    }

    fun getListOfMyRecipes(): List<UserPosts> {
        return _profileModel.value!!.userPosts
    }

    fun getListOfBookmarkedRecipes(): List<UserPosts> {
        return _profileModel.value!!.userPosts
//        return tabItems.filter { unit -> return@filter (unit.bookMarked == true) };

    }

    // Below functions are related to delete recipe composable
    fun getPostData(postId: Long) {
        val userId = sessionCache.getActiveSession()?.sessionUser?.userId ?: return

        viewModelScope.launch {
            when(val resource = postRepository.getPost(postId, userId)) {
                is Resource.Success -> {
                    _error.value = ""
                    _postData.value = resource.data
                    setTestPostData(_postData.value!!.videoLink, _postData.value!!.thumbnailLink)
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                }
            }
        }
    }

    fun deleteCurrentPost(postId: Long) {
        Log.i("MYTAG", "DELETED POST ID: $postId")
        viewModelScope.launch {
            when(val resource = postRepository.deletePost(postId)) {
                is Resource.Success -> {
                    _error.value = ""
                    _postData.value = null
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                }
            }
        }
    }

    fun updatePosts(postId: Long){
        _userPosts.update { currentList ->
            currentList.filter { post ->
                post.id != postId.toInt()
            }
        }
    }
    private fun setTestPostData(videoLink: String, thumbnailLink: String) {
        if(_postData.value == null) return
        val post = _postData.value!!
        _postData.value = VideoModel(post.videoId, post.authorDetails, post.videoStats,
            videoLink,
            post.currentViewerInteraction, post.description, post.createdAt,
            thumbnailLink)
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiEvent.emit(event)
        }
    }

//    @AssistedFactory
//    interface Factory {
//        fun create(userId: Long, navController: NavController): ProfileViewModel
//    }
//
//    companion object {
//        fun provideFactory(
//            assistedFactory: Factory, userId: Long, navController: NavController
//        ):ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return assistedFactory.create(userId, navController) as T
//            }
//        }
//    }
}
