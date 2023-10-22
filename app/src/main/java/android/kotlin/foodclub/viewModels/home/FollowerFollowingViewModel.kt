package android.kotlin.foodclub.viewModels.home

import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.utils.helpers.Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowerFollowingViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {
    private val _followersList = MutableStateFlow<List<SimpleUserModel>>(listOf())
    val followersList: StateFlow<List<SimpleUserModel>> get() = _followersList

    private val _followingList = MutableStateFlow<List<SimpleUserModel>>(listOf())
    val followingList: StateFlow<List<SimpleUserModel>> get() = _followingList

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> get() = _error

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> get() = _title

    fun getFollowersList(userId: Long) {
        viewModelScope.launch() {
            when(val resource = repository.retrieveProfileFollowers(userId)) {
                is Resource.Success -> {
                    _error.value = ""
                    _followersList.value = resource.data!!
                    _title.value = "Followers"
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                }
            }
        }
    }

    fun getFollowingList(userId: Long) {
        viewModelScope.launch() {
            when (val resource = repository.retrieveProfileFollowing(userId)) {
                is Resource.Success -> {
                    _error.value = ""
                    _followingList.value = resource.data!!
                    _title.value = "Following"
                }

                is Resource.Error -> {
                    _error.value = resource.message!!
                }
            }
        }
    }
}