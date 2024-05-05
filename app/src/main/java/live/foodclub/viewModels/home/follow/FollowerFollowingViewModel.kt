package live.foodclub.viewModels.home.follow

import live.foodclub.repositories.ProfileRepository
import live.foodclub.utils.helpers.Resource
import live.foodclub.views.home.followerFollowing.FollowerFollowingState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowerFollowingViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel(), FollowEvents {

    companion object {
        private val TAG = FollowerFollowingViewModel::class.java.simpleName
    }

    private val _state = MutableStateFlow(FollowerFollowingState.default())
    val state: StateFlow<FollowerFollowingState>
        get() = _state

    override fun getFollowersList(userId: Long) {
        viewModelScope.launch {
            when (val resource = repository.retrieveProfileFollowers(userId)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            followersList = resource.data!!,
                            title = "Followers",
                            error = ""
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = resource.message!!
                        )
                    }
                }
            }
        }
    }

    override fun getFollowingList(userId: Long) {
        viewModelScope.launch {
            when (val resource = repository.retrieveProfileFollowing(userId)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            followingList = resource.data!!,
                            title = "Following",
                            error = ""
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = resource.message!!
                        )
                    }
                }
            }
        }
    }
}