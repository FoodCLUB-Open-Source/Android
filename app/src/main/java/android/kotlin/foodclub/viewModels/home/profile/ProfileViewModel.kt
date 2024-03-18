package android.kotlin.foodclub.viewModels.home.profile

import android.kotlin.foodclub.domain.models.products.MyBasketCache
import android.kotlin.foodclub.domain.models.profile.UserProfile
import android.kotlin.foodclub.localdatasource.room.entity.toVideoModel
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.repositories.RecipeRepository
import android.kotlin.foodclub.utils.helpers.StoreData
import android.kotlin.foodclub.views.home.profile.ProfileState
import android.net.Uri
import android.util.Log
import androidx.media3.exoplayer.ExoPlayer
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val profileRepository: ProfileRepository,
    val sessionCache: SessionCache,
    private val storeData: StoreData,
    private val recipeRepository: RecipeRepository,
    private val basketCache: MyBasketCache,
    val exoPlayer: ExoPlayer
) : ViewModel(), ProfileEvents {

    companion object {
        private val TAG = ProfileViewModel::class.java.simpleName
    }

    private val _state = MutableStateFlow(ProfileState.default(exoPlayer))
    private val _profileUserId = MutableStateFlow(0L)

    val userPostsPagingFlow = _profileUserId
        .flatMapLatest { userId -> profileRepository.getUserPosts(userId).flow
            .map { pagingData -> pagingData.map { it.toVideoModel() } }
        }.cachedIn(viewModelScope)

    val userBookmarksPagingFlow = _profileUserId
        .flatMapLatest { userId -> profileRepository.getBookmarkedVideos(userId).flow
            .map { pagingData -> pagingData.map { it.toVideoModel() } }
        }.cachedIn(viewModelScope)

    private val _profile = userPostsPagingFlow
        .flatMapLatest { _ -> profileRepository.getUserProfileData(_profileUserId.value) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UserProfile.default())

    private val _isFollowed: StateFlow<Boolean> = _profileUserId
        .flatMapLatest { userId -> flow { emit(isFollowedBy(userId)) } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val state = combine(_state, _profileUserId, _profile, _isFollowed)
    { state, profileUserId, profile, isFollowed ->
        state.copy(
            profileUserId = profileUserId,
            userProfile = profile,
            isFollowed = isFollowed
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),
        ProfileState.default(exoPlayer))

    init {
        exoPlayer.prepare()
        if(sessionCache.getActiveSession()?.sessionUser?.userId != null) {
            _state.update { it.copy(
                dataStore = storeData,
                sessionUserId = sessionCache.getActiveSession()!!.sessionUser.userId)
            }
        }
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
        if(sessionCache.getActiveSession()?.sessionUser?.userId == null) return
        if (newUserId != _profileUserId.value || newUserId == 0L) {
            val userId = if (newUserId == 0L)
                sessionCache.getActiveSession()!!.sessionUser.userId
            else
                newUserId
            _profileUserId.update { userId }
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

    private suspend fun isFollowedBy(userId: Long): Boolean {
        when (val resource = profileRepository.retrieveProfileFollowers(userId)) {
            is Resource.Success -> {
                return resource.data!!.any {
                    it.userId.toLong() == _state.value.sessionUserId
                }
            }

            is Resource.Error -> {
            }
        }
        return _state.value.isFollowed
    }

    override fun isFollowedByUser(userId: Long) {
        viewModelScope.launch {
            when (val resource = profileRepository.retrieveProfileFollowers(userId)) {
                is Resource.Success -> {
                    _state.update {state->
                        state.copy(
                            error = "",
                            isFollowed = resource.data!!.any {
                                it.userId.toLong() == _state.value.sessionUserId
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

    override fun deletePost(postId: Long) {
        Log.i(TAG, "DELETED POST ID: $postId")
        viewModelScope.launch {
            when (val resource = postRepository.deletePost(postId)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            error = ""
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update { it.copy(error = resource.message!!) }
                }
            }
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

    override suspend fun userViewsPost(postId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun updatePostLikeStatus(postId: Long, isLiked: Boolean) {
        TODO("Not yet implemented")
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

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }
}