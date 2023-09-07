package android.kotlin.foodclub.viewmodels.home

import android.kotlin.foodclub.data.models.FollowerUserModel
import android.kotlin.foodclub.data.models.FollowingUserModel
import android.kotlin.foodclub.data.models.SimpleUserModel
import android.kotlin.foodclub.data.models.User
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Thread.State

class FollowerFollowingViewModel() : ViewModel(){
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
            when(val resource = ProfileRepository().retrieveProfileFollowers(userId)) {
                is Resource.Success -> {
                    _error.value = ""
                    _followersList.value = mapFollowersToSimpleList(resource.data!!)
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
            when(val resource = ProfileRepository().retrieveProfileFollowing(userId)) {
                is Resource.Success -> {
                    _error.value = ""
                    _followingList.value = mapFollowingToSimpleList(resource.data!!)
                    _title.value = "Following"
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                }
            }
        }
    }

    private fun mapFollowersToSimpleList(followersList: List<FollowerUserModel>): List<SimpleUserModel> {
        return followersList.map { SimpleUserModel(it.userId, it.username, it.profilePictureUrl) }
    }

    private fun mapFollowingToSimpleList(followingList: List<FollowingUserModel>): List<SimpleUserModel> {
        return followingList.map { SimpleUserModel(it.userId, it.username, it.profilePictureUrl) }
    }
}