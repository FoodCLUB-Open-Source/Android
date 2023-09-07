package android.kotlin.foodclub.viewmodels.home

import android.kotlin.foodclub.data.models.UserPostsModel
import android.kotlin.foodclub.data.models.UserProfileModel
import android.kotlin.foodclub.data.models.MyRecipeModel
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val userId: Long) : ViewModel() {

    private val _profileModel = MutableStateFlow<UserProfileModel?>(null)
    val profileModel: StateFlow<UserProfileModel?> get() = _profileModel

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> get() = _error

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

    init {
        getProfileModel(userId)
    }

    private fun getProfileModel(userId: Long) {
        viewModelScope.launch() {
            when(val resource = ProfileRepository().retrieveProfileData(userId)) {
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


    fun getListOfMyRecipes(): List<UserPostsModel> {
        return _profileModel.value!!.userPosts
    }

    fun getListOfBookmarkedRecipes(): List<UserPostsModel> {
        return _profileModel.value!!.userPosts
//        return tabItems.filter { unit -> return@filter (unit.bookMarked == true) };

    }


}
