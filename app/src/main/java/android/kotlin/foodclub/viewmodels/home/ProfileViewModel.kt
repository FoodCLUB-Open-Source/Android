package android.kotlin.foodclub.viewmodels.home

import android.kotlin.foodclub.data.models.MyPostsModel
import android.kotlin.foodclub.data.models.MyProfileModel
import android.kotlin.foodclub.data.models.MyRecipeModel
import android.kotlin.foodclub.repositories.MyProfileRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val userId: Long) : ViewModel() {

    private val _profileModel = MutableStateFlow<MyProfileModel?>(null)
    val profileModel: StateFlow<MyProfileModel?> get() = _profileModel

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
        getMyProfileModel(userId)
    }

    fun getMyProfileModel(userId: Long) {
        viewModelScope.launch() {
            Log.d("ProfileViewModel", "test")
            when(val resource = MyProfileRepository().retrieveProfileData(userId)) {
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


    fun getListOfMyRecipes(): List<MyPostsModel> {
        return _profileModel.value!!.userPosts
    }

    fun getListOfBookmarkedRecipes(): List<MyPostsModel> {
        return _profileModel.value!!.userPosts
//        return tabItems.filter { unit -> return@filter (unit.bookMarked == true) };

    }


}
