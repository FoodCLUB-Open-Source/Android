package android.kotlin.foodclub.viewModels.home.profile

import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.products.MyBasketCache
import android.kotlin.foodclub.localdatasource.room.entity.UserDetailsModel
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.localdatasource.room.entity.OfflineProfileVideosModel
import android.kotlin.foodclub.utils.helpers.ConnectivityUtils
import android.kotlin.foodclub.repositories.RecipeRepository
import android.kotlin.foodclub.utils.helpers.StoreData
import android.kotlin.foodclub.utils.helpers.UiEvent
import android.kotlin.foodclub.views.home.profile.ProfileState
import android.net.Uri
import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val profileRepository: ProfileRepository,
    val sessionCache: SessionCache,
    private val connectivityUtil: ConnectivityUtils,
    private val storeData: StoreData,
    private val recipeRepository: RecipeRepository,
    private val basketCache: MyBasketCache
) : ViewModel(), ProfileEvents {

    companion object {
        private val TAG = ProfileViewModel::class.java.simpleName
    }

    private val _state = MutableStateFlow(ProfileState.default())
    val state: StateFlow<ProfileState>
        get() = _state

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        if (sessionCache.getActiveSession()?.sessionUser?.userId == null) {
            sendUiEvent(UiEvent.Navigate(Graph.AUTHENTICATION))
        }
    }

    override fun updateUserProfileImage(id: Long, file: File, uri: Uri) {
        viewModelScope.launch {
            when (val resource = profileRepository.updateUserProfileImage(id, file)) {
                is Resource.Success -> {
                    Log.i(TAG, "SUCCESSFULLY UPDATED IMAGE ${resource.data}")
                }

                is Resource.Error -> {
                    Log.i(TAG, "ERROR UPDATING IMAGE ${resource.message}")
                }
            }
        }
        _state.update {
            it.copy(
                userProfile = it.userProfile!!.copy(
                    profilePictureUrl = uri.toString()
                )
            )
        }
        Log.i(TAG, "USER UPDATED IMG: ${state.value.userProfile!!.profilePictureUrl}")
    }


    override fun setUser(newUserId: Long) {
        if (newUserId != sessionCache.getActiveSession()!!.sessionUser.userId) {
            val userId = if(newUserId == 0L) sessionCache.getActiveSession()!!.sessionUser.userId else
                newUserId

            _state.update { it.copy(dataStore = storeData) }

            if (connectivityUtil.isNetworkAvailable()){
                Log.i(TAG,"INTERNET CONNECTED")
                viewModelScope.launch {
                    getProfileModel(userId)
                    getBookmarkedPosts(userId)
                    //getUserDetails(userId)
                }
//                viewModelScope.launch {
//                    delay(2000)
//                    insertLocalUserDetails()
//                }
            }else{
                Log.i(TAG,"INTERNET NOT CONNECTED")
                retrieveLocalUserDetails(userId)
                retrieveAllOfflineProfileVideos()
            }
        }
    }
    override fun unfollowUser(sessionUserId: Long, userId: Long) {
        viewModelScope.launch {
            when (val resource = profileRepository.unfollowUser(sessionUserId, userId)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            error = "",
                            isFollowed = false
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update { it.copy(error = resource.message!!) }
                }
            }
        }
    }

    override fun followUser(sessionUserId: Long, userId: Long) {
        viewModelScope.launch {
            when (val resource = profileRepository.followUser(sessionUserId, userId)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            error = "",
                            isFollowed = true
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update { it.copy(error = resource.message!!) }
                }
            }
        }
    }

    override fun isFollowedByUser(sessionUserId: Long, userId: Long) {
        viewModelScope.launch {
            when (val resource = profileRepository.retrieveProfileFollowers(userId)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            error = "",
                            isFollowed = resource.data!!.any {
                                it.userId.toLong() == sessionUserId
                            }
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update { it.copy(error = resource.message!!) }
                }
            }
        }
    }

    override fun getPostData(postId: Long) {
//        val userId = sessionCache.getActiveSession()?.sessionUser?.userId ?: return

        _state.update {
            it.copy(
                postData = it.userPosts.filter { it.videoId == postId }.getOrNull(0)
            )
        }

//        viewModelScope.launch {
//            when (val resource = postRepository.getPost(postId, userId)) {
//                is Resource.Success -> {
//                    _state.update {
//                        it.copy(
//                            error = "",
//                            postData = resource.data
//                        )
//                    }
//                    setTestPostData(resource.data!!.videoLink, resource.data.thumbnailLink)
//                }
//
//                is Resource.Error -> {
//                    _state.update { it.copy(error = resource.message!!) }
//                }
//            }
//        }
    }

    override fun deleteCurrentPost(postId: Long) {
        Log.i(TAG, "DELETED POST ID: $postId")
        viewModelScope.launch {
            when (val resource = postRepository.deletePost(postId)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            error = "",
                            postData = null
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update { it.copy(error = resource.message!!) }
                }
            }
        }
    }

    override fun updatePosts(postId: Long) {
        _state.update {
            it.copy(
                error = "",
                userPosts = it.userPosts.filter { post ->
                    post.videoId != postId
                }
            )
        }
    }

    override fun addIngredientsToBasket() {
        val basket = basketCache.getBasket()
        val selectedIngredients = _state.value.recipe?.ingredients?.filter { it.isSelected }
        selectedIngredients?.forEach {
            it.isSelected = false
            basket.addIngredient(it.copy())
        }
        basketCache.saveBasket(basket)
    }

    private suspend fun getProfileModel(userId: Long) {
        viewModelScope.launch {
            when (val resource = profileRepository.retrieveProfileData(userId)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            error = "",
                            userProfile = resource.data,
                            sessionUserId = sessionCache.getActiveSession()!!.sessionUser.userId,
                            dataStore = storeData,
                            userPosts = resource.data!!.userPosts,
                            myUserId = sessionCache.getActiveSession()!!.sessionUser.userId
                        )
                    }
                    insertLocalProfileVideos()
                }

                is Resource.Error -> {
                    _state.update { it.copy(error = resource.message!!) }
                }
            }
        }
    }

    /**
     * This is the job of the repository - to be single source of data truth for the VM
     * to make offline-first app,
     * store room response data to profileModel or userDetails(not used right now)
     * populate UI with room response data
     * then check for internet connection(utils.helpers.ConnectivityUtils) and make api call
     * compare the room db response and api call response
     * update room db with profileModel or userDetails values
     * */
    private fun insertLocalUserDetails(){
        viewModelScope.launch {
            val combined = UserDetailsModel(
                id = state.value.userDetails!!.id,
                userName = state.value.userDetails!!.userName,
                email = state.value.userDetails!!.email,
                profilePicture = state.value.userDetails!!.profilePicture ?: "",
                userBio = state.value.userDetails!!.userBio ?: "",
                gender = state.value.userDetails!!.gender ?: "",
                createdAt = state.value.userDetails!!.createdAt,
                dateOfBirth = state.value.userDetails!!.dateOfBirth ?: "",
                dietaryPrefs = state.value.userDetails!!.dietaryPrefs ?: listOf(),
                country = state.value.userDetails!!.country ?: "",
                shippingAddress = state.value.userDetails!!.shippingAddress ?: "",
                fullName = state.value.userDetails!!.fullName ?: ""
            )
            profileRepository.insertLocalUserDetails(combined)
        }
    }

    private fun retrieveLocalUserDetails(id: Long) {
        viewModelScope.launch {
            when (val resource = profileRepository.retrieveLocalUserDetails(id)) {
                is Resource.Success -> {
                    val user = resource.data
                    _state.update {
                        it.copy(
                            offlineUserData = user
                        )
                    }
                    Log.i(TAG, "LOCAL USER DETAILS $user")
                }
                is Resource.Error -> {
                    // Handle the error case if needed
                    Log.e(TAG, "Error retrieving local user details: ${resource.message}")
                }
            }
        }
    }

    fun getRecipe(postId: Long) {
        viewModelScope.launch {
            when(val resource = recipeRepository.getRecipe(postId)) {
                is Resource.Success -> {
                    _state.update { it.copy(
                        recipe = resource.data
                    ) }
                }
                is Resource.Error -> {
                    _state.update { it.copy(error = resource.message!!) }
                }
            }
        }
    }

    /**
     * This insert can not be used to batch "right now" because it is not specified on video details
     * This function uses async await to prevent race conditions
     * */
    private fun insertLocalProfileVideos() {
        viewModelScope.launch {
            val deferredUserPosts = async {
                Log.i(TAG, "Inserting UserPosts...")
                insertVideos(state.value.userProfile?.userPosts?.take(10), true)
            }
            deferredUserPosts.await()

            val deferredBookmarkedPosts = async {
                Log.i(TAG, "Inserting BookmarkedPosts...")
                insertVideos(state.value.bookmarkedPosts.take(10), false)
            }
            deferredBookmarkedPosts.await()
        }
    }

    private suspend fun insertVideos(videos: List<VideoModel>?, isVideoPost: Boolean) {
        try {
            videos?.forEach {
                val video = OfflineProfileVideosModel(
                    it.videoId,
                    isVideoPost,
                    it.authorDetails,
                    it.videoLink,
                    it.thumbnailLink,
                    it.currentViewerInteraction.isLiked,
                    it.currentViewerInteraction.isBookmarked,
                    it.description,
                    it.createdAt,
                    it.videoStats.like,
                    it.videoStats.comment,
                    it.videoStats.share,
                    it.videoStats.favourite,
                    it.videoStats.views
                )
                profileRepository.insertProfileVideosData(video)
            }
        }catch (e: Exception){
            Log.e(TAG, "Error inserting videos: ${e.message}")
        }
    }

    private fun retrieveAllOfflineProfileVideos() {
        viewModelScope.launch {
            when(val response = profileRepository.retrieveAllLocalProfileVideos()){
                is Resource.Success -> {
                    Log.i(TAG,"Offline Profile Videos ${response.data}")
                }
                is Resource.Error -> {
                    Log.e(TAG,"ERROR Offline Profile Videos ${response.message}")
                }
            }
        }
    }

    private fun getUserDetails(id: Long){
        viewModelScope.launch {
            when (val resource = profileRepository.retrieveUserDetails(id)) {
                is Resource.Success -> {
                    _state.update { it.copy(userDetails = resource.data) }
                }

                is Resource.Error -> {
                    Log.i(TAG, "getUserDetails failed: ${resource.message}")
                }
            }
        }
    }

    private fun getBookmarkedPosts(userId: Long) {
        viewModelScope.launch {
            when (val resource = profileRepository.retrieveBookmarkedPosts(userId)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            error = "",
                            bookmarkedPosts = resource.data!!
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update { it.copy(error = resource.message!!) }
                }
            }
        }
    }

    private fun getListFromDatabase() {

        //  TODO Hardcoded List used, need to fetch from API---->


    }

    fun getListOfMyRecipes(): List<VideoModel> {
        return state.value.userProfile!!.userPosts
    }

    fun getListOfBookmarkedRecipes(): List<VideoModel> {
        return state.value.userProfile!!.userPosts
//        return tabItems.filter { unit -> return@filter (unit.bookMarked == true) };

    }

    private fun setTestPostData(videoLink: String, thumbnailLink: String) {
        if (state.value.postData == null) return
        val post = state.value.postData!!
        _state.update {
            it.copy(
                error = "",
                postData = VideoModel(
                    post.videoId, post.authorDetails, post.videoStats,
                    videoLink,
                    post.currentViewerInteraction, post.description, post.createdAt,
                    thumbnailLink
                )
            )
        }
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
