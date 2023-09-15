package android.kotlin.foodclub.viewmodels.home

import android.kotlin.foodclub.data.models.UserPostsModel
import android.kotlin.foodclub.data.models.UserProfileModel
import android.kotlin.foodclub.data.models.MyRecipeModel
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.utils.helpers.SessionCache
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import android.kotlin.foodclub.navigation.graphs.Graph
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel @AssistedInject constructor(
    private val repository: ProfileRepository,
    private val sessionCache: SessionCache,
    @Assisted private val userId: Long,
    @Assisted private val navController: NavController
) : ViewModel() {
    private val _myUserId = MutableStateFlow(sessionCache.getActiveSession()?.userId ?: 0)
    val myUserId: StateFlow<Long> get() = _myUserId

    private val _profileModel = MutableStateFlow<UserProfileModel?>(null)
    val profileModel: StateFlow<UserProfileModel?> get() = _profileModel

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> get() = _error

    private val _isFollowedByUser = MutableStateFlow(false)
    val isFollowedByUser: StateFlow<Boolean> get() = _isFollowedByUser

    init {
        if(sessionCache.getActiveSession()?.userId == null) {
            navController.navigate(Graph.AUTHENTICATION) {
                popUpTo(Graph.HOME) { inclusive = true }
            }
        } else {
            getProfileModel(if(userId != 0L) userId else sessionCache.getActiveSession()!!.userId)
        }
    }

    val tabItems = listOf(
        MyRecipeModel(
            "image", "0", true
        ), MyRecipeModel(
            "image", "0", false
        ), MyRecipeModel(
            "image", "0", true
        ), MyRecipeModel(
            "image", "0", false
        )
    )

    fun setUser(newUserId: Long) {
        if(newUserId != userId) {
            getProfileModel(if(newUserId != 0L) newUserId else sessionCache.getActiveSession()!!.userId)
        }
    }

    private fun getProfileModel(userId: Long) {
        viewModelScope.launch() {
            when(val resource = repository.retrieveProfileData(userId)) {
                is Resource.Success -> {
                    _error.value = ""
                    _profileModel.value = resource.data
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
            when(val resource = repository.unfollowUser(followerId, userId)) {
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
            when(val resource = repository.followUser(followerId, userId)) {
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
            when(val resource = repository.retrieveProfileFollowers(userId)) {
                is Resource.Success -> {
                    _error.value = ""
                    _isFollowedByUser.value = resource.data!!.any { it.userId.toLong() == followerId }
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                }
            }
        }
    }

    fun getListOfMyRecipes(): List<UserPostsModel> {
        return _profileModel.value!!.userPosts
    }

    fun getListOfBookmarkedRecipes(): List<UserPostsModel> {
        return _profileModel.value!!.userPosts
//        return tabItems.filter { unit -> return@filter (unit.bookMarked == true) };

    }

    @AssistedFactory
    interface Factory {
        fun create(userId: Long, navController: NavController): ProfileViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory, userId: Long, navController: NavController
        ):ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(userId, navController) as T
            }
        }
    }

}
