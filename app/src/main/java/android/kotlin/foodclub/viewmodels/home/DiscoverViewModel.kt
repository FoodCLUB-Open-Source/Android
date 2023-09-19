package android.kotlin.foodclub.viewmodels.home

import android.kotlin.foodclub.api.authentication.PostById
import android.kotlin.foodclub.api.authentication.PostByWorld
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.kotlin.foodclub.data.models.UserPostsModel
import android.kotlin.foodclub.data.models.VideoModel
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.utils.helpers.Resource
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
    private val repository: PostRepository
):ViewModel() {

    private val _postList = MutableStateFlow<List<VideoModel>>(listOf())
    val postList: StateFlow<List<VideoModel>> get() = _postList

    private val _postListPerCategory = MutableStateFlow<List<PostByWorld>>(listOf())
    val postListPerCategory: StateFlow<List<PostByWorld>> get() = _postListPerCategory

    private val _postInfo = MutableStateFlow<List<PostById>>(listOf())
    val postInfo: StateFlow<List<PostById>> get() = _postInfo

    private val _myFridgePosts = MutableStateFlow<List<UserPostsModel>>(listOf())
    val myFridgePosts: StateFlow<List<UserPostsModel>> get() = _myFridgePosts


    fun getPostsByWorld(worldCategory: Long) {


        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitInstance.retrofitApi.getPostByWorldCategory(worldCategory)

                if (response.isSuccessful) {
                    _postListPerCategory.value = response.body()!!.posts
                }

            } catch (e: Exception) {

            }

        }


    }


    fun getPostInfoById(id: Long) {
        viewModelScope.launch(Dispatchers.Main) {

            try {

                val response1 = RetrofitInstance.retrofitApi.getPostById(id)


                if (response1.isSuccessful) {
                    _postInfo.value = response1.body()!!.data
                }

            } catch (e: Exception) {

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



