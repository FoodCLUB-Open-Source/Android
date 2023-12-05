package android.kotlin.foodclub.viewModels.home

import android.content.Context
import android.graphics.BitmapFactory
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.MyBasketCache
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.kotlin.foodclub.views.home.discover.DiscoverState
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val profileRepo: ProfileRepository,
    private val productsRepo: ProductRepository,
    private val sessionCache: SessionCache,
    val myBasketCache: MyBasketCache
) : ViewModel() {

    companion object {
        private val TAG = DiscoverViewModel::class.java.simpleName
    }

    private val _state = MutableStateFlow(DiscoverState.default())
    val state: StateFlow<DiscoverState>
        get() = _state

    private var searchJob: Job? = null

    // TODO add real data for scanResultItemList

    init {
        viewModelScope.launch {
            fetchProductsDatabase(state.value.mainSearchText)
        }
    }

    fun onMainSearchTextChange(text: String) {
        _state.update { it.copy(mainSearchText = text) }
    }

    fun onSubSearchTextChange(text: String) {
        _state.update { it.copy(ingredientSearchText = text) }
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            if (text != "") {
                delay(1000)
                fetchProductsDatabase(state.value.ingredientSearchText)
            }
        }
    }

    fun addToUserIngredients(ingredient: Ingredient) {
        val updatedList = state.value.userIngredients.toMutableList()
        updatedList.add(ingredient)
        _state.update {
            it.copy(
                userIngredients = updatedList,
                ingredientSearchText = ""
            )
        }
    }

    fun addScanListToUserIngredients(ingredient: List<Ingredient>) {
        val updatedList = state.value.userIngredients.toMutableList()
        updatedList.addAll(ingredient)
        _state.update {
            it.copy(
                userIngredients = updatedList,
                ingredientSearchText = ""
            )
        }
    }

    fun deleteIngredientFromList(ingredient: Ingredient) {
        val updatedList = state.value.userIngredients.toMutableList()
        updatedList.remove(ingredient)
        _state.update {
            it.copy(
                userIngredients = updatedList
            )
        }
    }

    fun updateIngredient(ingredient: Ingredient) {
        _state.update {
            it.copy(
                userIngredients = state.value.userIngredients.map { item ->
                    if (item.id == ingredient.id) {
                        Ingredient(
                            id = item.id,
                            quantity = ingredient.quantity,
                            unit = ingredient.unit,
                            type = ingredient.type,
                            expirationDate = ingredient.expirationDate,
                            imageUrl = ingredient.imageUrl
                        )
                    } else {
                        item
                    }
                }
            )
        }

        _state.update { it.copy(ingredientToEdit = ingredient) }

        Log.i(TAG, "LIST AFTER ${state.value.userIngredients[0].quantity}")
    }

    private suspend fun fetchProductsDatabase(searchText: String) {
        Log.e(TAG, "made call $searchText")

        when (val resource = productsRepo.getProductsList(searchText)) {
            is Resource.Success -> {
                Log.e(TAG, "SUCCESS")
                _state.update {
                    it.copy(
                        error = "",
                        productsData = resource.data!!
                    )
                }
            }

            is Resource.Error -> {
                _state.update { it.copy(error = resource.message!!) }
            }
        }
    }

    fun getPostsByWorld(worldCategory: Long) {
        _state.update {
            it.copy(
                sessionUserId = sessionCache.getActiveSession()!!.sessionUser.userId.toString(),
            )
        }

        viewModelScope.launch(Dispatchers.Main) {
            when (val resource = postRepository.getWorldCategoryPosts(worldCategory, 10, 1)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            postList = resource.data!!
                        )
                    }
                }

                is Resource.Error -> {
                    // TODO deal with error
                }
            }
        }
    }


    fun getPostData(postId: Long) {
        val userId = sessionCache.getActiveSession()?.sessionUser?.userId ?: return
        viewModelScope.launch {
            when (val resource = postRepository.getPost(
                id = postId,
                userId = userId
            )) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            sessionUserUsername = resource.data!!.authorDetails,
                        )
                    }
                }

                is Resource.Error -> {
                    // TODO deal with error
                }
            }
        }
    }

    fun getPostsByUserId() {
        viewModelScope.launch {
            when (val resource = postRepository.getHomepagePosts(
                userId = state.value.sessionUserId.toLong(),
                pageSize = 10,
                pageNo = 1
            )) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            postList = resource.data!!
                        )
                    }
                }

                is Resource.Error -> {
                    // TODO deal with error
                }
            }
        }

    }

    fun myFridgePosts() {
        viewModelScope.launch {
            when (val resource =
                profileRepo.retrieveProfileData(
                    userId = state.value.sessionUserId.toLong(),
                    pageNo = 10,
                    pageSize = 1
                )) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            myFridgePosts = resource.data!!.userPosts
                        )
                    }
                }

                is Resource.Error -> {
                    // TODO deal with error
                }
            }
        }

    }

    fun scan(imageCapture: ImageCapture, context: Context) {
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    val buffer = image.planes[0].buffer
                    val bytes = ByteArray(buffer.remaining())

                    buffer.get(bytes)

                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    _state.update {
                        it.copy(
                            capturedImage = bitmap.asImageBitmap()
                        )
                    }
                    image.close()
                }

                override fun onError(exception: ImageCaptureException) {
                    // Handle capture error
                }
            }
        )
    }


}



