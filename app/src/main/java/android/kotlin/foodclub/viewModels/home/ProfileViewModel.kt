package android.kotlin.foodclub.viewModels.home

import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.profile.UserPosts
import android.kotlin.foodclub.domain.models.profile.UserProfile
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.utils.helpers.UiEvent
import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val postRepository: PostRepository,
    val profileRepository: ProfileRepository,
    val sessionCache: SessionCache
) : ViewModel() {

    private val _myUserId = MutableStateFlow(
        sessionCache.getActiveSession()?.sessionUser?.userId ?: 0
    )
    val myUserId: StateFlow<Long> get() = _myUserId

    private val _profileModel = MutableStateFlow<UserProfile?>(null)
    val profileModel: StateFlow<UserProfile?> get() = _profileModel

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
        viewModelScope.launch() {
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

    private fun getBookmarkedPosts(userId: Long) {
        viewModelScope.launch() {
            when(val resource = profileRepository.retrieveBookmaredPosts(userId)) {
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
        viewModelScope.launch() {
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
        viewModelScope.launch() {
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
        viewModelScope.launch() {
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
