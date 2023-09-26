package android.kotlin.foodclub.viewmodels.home

import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.kotlin.foodclub.data.models.UserPostsModel
import android.kotlin.foodclub.data.models.UserProfileModel
import android.kotlin.foodclub.data.models.VideoModel
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.utils.helpers.SessionCache
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val repository: PostRepository,
    private val profileRepo: ProfileRepository
):ViewModel() {

    private val _postList = MutableStateFlow<List<VideoModel>>(listOf())
    val postList: StateFlow<List<VideoModel>> get() = _postList

    private val _postListPerCategory = MutableStateFlow<List<VideoModel>>(listOf())
    val postListPerCategory: StateFlow<List<VideoModel>> get() = _postListPerCategory

    private val _myFridgePosts = MutableStateFlow<List<UserPostsModel>>(listOf())
    val myFridgePosts: StateFlow<List<UserPostsModel>> get() = _myFridgePosts

    private val _profileData = MutableStateFlow<List<UserProfileModel>>(listOf())
    val profileData: MutableStateFlow<List<UserProfileModel>> get() = _profileData

    lateinit var session:SessionCache

    fun getPostsByWorld(worldCategory: Long) {

        viewModelScope.launch(Dispatchers.Main) {
            when(val resource = repository.getWorldCategoryPosts(worldCategory, 10, 1)) {
                is Resource.Success -> {
                    _postListPerCategory.value = resource.data!!
                }
                is Resource.Error -> {

                }
            }

        }


    }

    fun getUserData(){
        viewModelScope.launch {

            when (val resource = profileRepo.retrieveProfileData(5)) {
                is Resource.Success -> {
                    _profileData.value = listOf( resource.data!!)
                }

                is Resource.Error -> {

                }
            }

        }
    }

    fun getPostsByUserId(id: Long) {

        viewModelScope.launch {
            when(val resource = repository.getHomepagePosts(id, 10, 1)) {
                is Resource.Success -> {
                    _postList.value = resource.data!!
                }
                is Resource.Error -> {

                }
            }

        }

    }

    fun myFridgePosts(id: Long) {

        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitInstance.retrofitApi.retrieveProfileData(id, 1, 1)

                if (response.isSuccessful) {
                    _myFridgePosts.value = response.body()!!.data.userPosts
                }

            } catch (e: Exception) {

            }

        }

    }

}



