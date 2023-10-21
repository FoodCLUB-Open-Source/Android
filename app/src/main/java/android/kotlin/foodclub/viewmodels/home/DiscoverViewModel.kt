package android.kotlin.foodclub.viewmodels.home

import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.data.models.UserPostsModel
import android.kotlin.foodclub.data.models.VideoModel
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
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
    private val profileRepo: ProfileRepository,
    private val productsRepo: ProductRepository,
    private val sessionCache: SessionCache
):ViewModel() {

    private val _postList = MutableStateFlow<List<VideoModel>>(listOf())
    val postList: StateFlow<List<VideoModel>> get() = _postList

    private val _postListPerCategory = MutableStateFlow<List<VideoModel>>(listOf())
    val postListPerCategory: StateFlow<List<VideoModel>> get() = _postListPerCategory

    private val _myFridgePosts = MutableStateFlow<List<UserPostsModel>>(listOf())
    val myFridgePosts: StateFlow<List<UserPostsModel>> get() = _myFridgePosts

    private val _sessionUserId = MutableStateFlow<String>("")
    val sessionUserId: MutableStateFlow<String> get() = _sessionUserId

    private val _sessionUserName = MutableStateFlow<String>("")
    val sessionUserName: MutableStateFlow<String> get() = _sessionUserName

    private val _productsDatabase = MutableStateFlow(ProductsData("", "", listOf()))
    val productsDatabase: StateFlow<ProductsData> get() = _productsDatabase

    private val _error = MutableStateFlow("")

    init {
        fetchProductsDatabase("")
    }

    private fun fetchProductsDatabase(searchText: String) {
        viewModelScope.launch() {
            when(val resource = productsRepo.getProductsList(searchText)) {
                is Resource.Success -> {
                    _error.value = ""
                    _productsDatabase.value = resource.data!!
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                }
            }
        }

    }

    fun getPostsByWorld(worldCategory: Long) {

        _sessionUserId.value = sessionCache.getActiveSession()!!.sessionUser.userId.toString()


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


    fun getPostData(postId:Long){
        viewModelScope.launch {


            when (val resource = repository.getPost(postId)) {
                is Resource.Success -> {
                    _sessionUserName.value =  resource.data!!.authorDetails
                }

                is Resource.Error -> {

                }
            }

        }
    }

    fun getPostsByUserId() {

        viewModelScope.launch {
            when(val resource = repository.getHomepagePosts(sessionUserId.value.toLong(), 10, 1)) {
                is Resource.Success -> {
                    _postList.value = resource.data!!
                }
                is Resource.Error -> {

                }
            }

        }

    }

    fun myFridgePosts() {

        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitInstance.retrofitApi.retrieveProfileData(sessionUserId.value.toLong(), 1, 1)

                if (response.isSuccessful) {
                    _myFridgePosts.value = response.body()!!.data.userPosts
                }

            } catch (e: Exception) {

            }

        }

    }

}



