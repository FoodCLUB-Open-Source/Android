package android.kotlin.foodclub.viewModels.home.profile

import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.products.MyBasketCache
import android.kotlin.foodclub.localdatasource.room.entity.OfflineUserPostsModel
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.SharedVideoMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.UserLocalPostsMapper
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.repositories.RecipeRepository
import android.kotlin.foodclub.utils.helpers.StoreData
import android.kotlin.foodclub.utils.helpers.UiEvent
import android.kotlin.foodclub.views.home.profile.ProfileState
import android.net.Uri
import android.util.Log
import androidx.media3.exoplayer.ExoPlayer
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val profileRepository: ProfileRepository,
    val sessionCache: SessionCache,
    pager: Pager<Int, OfflineUserPostsModel>,
    private val sharedVideoMapper: SharedVideoMapper,
    private val storeData: StoreData,
    private val recipeRepository: RecipeRepository,
    private val basketCache: MyBasketCache,
    val exoPlayer: ExoPlayer
) : ViewModel(), ProfileEvents {

    companion object {
        private val TAG = ProfileViewModel::class.java.simpleName
    }

    val userPostsPagingFlow = pager
        .flow
        .map { pagingData -> pagingData.map { sharedVideoMapper.mapToDomainModel(it) } }
        .cachedIn(viewModelScope)

    private val _state = MutableStateFlow(ProfileState.default())
    val state: StateFlow<ProfileState>
        get() = _state

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        exoPlayer.prepare()
        if (sessionCache.getActiveSession()?.sessionUser?.userId == null) {
            sendUiEvent(UiEvent.Navigate(Graph.AUTHENTICATION))
        }
        _state.update { it.copy(dataStore = storeData) }
    }

    override fun updateUserProfileImage(file: File, uri: Uri) {
        viewModelScope.launch {
            when (val resource = profileRepository.updateUserProfileImage(file)) {
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
            val userId = if (newUserId == 0L) sessionCache.getActiveSession()!!.sessionUser.userId else newUserId

            viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }

                val profileDeferred = async { getProfileModel(userId) }
                val bookmarkedDeferred = async { getBookmarkedPosts(userId) }

                awaitAll(profileDeferred, bookmarkedDeferred)

                _state.update { it.copy(isLoading = false) }
            }
        }
    }
    override fun unfollowUser(userId: Long) {
        viewModelScope.launch {
            when (val resource = profileRepository.unfollowUser(userId)) {
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

    override fun followUser(userId: Long) {
        viewModelScope.launch {
            when (val resource = profileRepository.followUser(userId)) {
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
                    _state.update {state->
                        state.copy(
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

        _state.update { state->
            state.copy(
                postData = state.userPosts.filter {
                    it.videoId == postId
                }.getOrNull(0)
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

    override fun deletePost(postId: Long) {
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

    override fun onRefreshUI() {
        _state.update {
            it.copy(isRefreshingUI = false)
        }
        setUser(_state.value.myUserId)
    }

    override suspend fun userViewsPost(postId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun updatePostLikeStatus(postId: Long, isLiked: Boolean) {
        TODO("Not yet implemented")
    }

    private suspend fun getProfileModel(userId: Long) {
//        when (val resource = profileRepository.retrieveProfileData(userId)) {
//            is Resource.Success -> {
//                _state.update {
//                    it.copy(
//                        error = "",
//                        userProfile = resource.data,
//                        sessionUserId = sessionCache.getActiveSession()!!.sessionUser.userId,
//                        dataStore = storeData,
//                        userPosts = resource.data!!.userPosts,
//                        myUserId = userId,
//                    )
//                }
//            }
//
//            is Resource.Error -> {
                val profileData = profileRepository.getUserProfileData(userId)
                val postVideosData = profileRepository.getUserPosts()
                _state.update { state->
                    state.copy(
                        userProfile = profileData,
                        userPosts = postVideosData,
//                        error = resource.message!!,
                    )
                }
//            }
//        }
    }

    override fun getRecipe(postId: Long) {
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

    override fun updatePostBookmarkStatus(postId: Long, isBookmarked: Boolean) {
        TODO("Not yet implemented")
    }

    private suspend fun getBookmarkedPosts(userId: Long) {
        when (val resource = profileRepository.retrieveBookmarkedPosts(userId)) {
            is Resource.Success -> {
                _state.update {
                    it.copy(
                        error = "",
                        bookmarkedPosts = resource.data!!,
                    )
                }
            }

            is Resource.Error -> {
                val bookmarkedVideosData = profileRepository.getBookmarkedVideos()
                _state.update {
                    it.copy(
                        bookmarkedPosts = bookmarkedVideosData,
                        error = resource.message!!,
                    )
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

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }
}