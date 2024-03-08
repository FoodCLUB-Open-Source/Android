package android.kotlin.foodclub.viewModels.home.discover

import android.content.Context
import android.graphics.BitmapFactory
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.MyBasketCache
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.SharedVideoMapper
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.views.home.discover.DiscoverState
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val productsRepo: ProductRepository,
    private val sessionCache: SessionCache,
    private val myBasketCache: MyBasketCache,
    private val videoMapper: SharedVideoMapper,
    val exoPlayer: ExoPlayer
) : ViewModel(), DiscoverEvents {

    companion object {
        private val TAG = DiscoverViewModel::class.java.simpleName
    }

    private val _state = MutableStateFlow(DiscoverState.default())
    val state: StateFlow<DiscoverState>
        get() = _state

    // TODO add real data for scanResultItemList

    init {
        exoPlayer.prepare()
        _state.update {state->
            state.copy(
                username = sessionCache.getActiveSession()?.sessionUser?.username ?: "",
                myBasketCache = myBasketCache
            )
        }
        getPostsByWorld(197)
        getPostsByUserId()
        observeAndFetchSearchedIngredients()
    }

    override fun onAddIngredientsSearchTextChange(text: String) {
        _state.value = _state.value.copy(ingredientSearchText = text)
    }

    private fun observeAndFetchSearchedIngredients () {
        viewModelScope.launch {
            _state
                .map { it.ingredientSearchText }
                .debounce(1000)
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .collect { searchText ->
                    fetchProductsDatabase(searchText)
                }
        }
    }

    override fun addToUserIngredients(ingredient: Ingredient) {
        val updatedList = state.value.userIngredients.toMutableList()
        updatedList.add(ingredient)
        _state.update {
            it.copy(
                userIngredients = updatedList,
            )
        }
    }

    override fun deleteIngredientFromList(ingredient: Ingredient) {
        val updatedList = state.value.userIngredients.toMutableList()
        updatedList.remove(ingredient)
        _state.update {
            it.copy(
                userIngredients = updatedList
            )
        }
    }

    override fun updateIngredient(ingredient: Ingredient) {
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
    }

    override fun getPostData(postId: Long) {
        viewModelScope.launch {
            when (val resource = postRepository.getPost(id = postId)) {
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

                else -> {}
            }
        }
    }

    override fun getPostsByUserId() {
        viewModelScope.launch {
            when (val resource = postRepository.getHomepagePosts(
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

                else -> {}
            }
        }

    }

    override fun getPostsByWorld(worldCategory: Long) {
        _state.update {
            it.copy(
                sessionUsername = sessionCache.getActiveSession()!!.sessionUser.username,
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

                else -> {}
            }
        }
    }

    override fun scan(imageCapture: ImageCapture, context: Context) {
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

    override fun addScanListToUserIngredients(ingredients: List<Ingredient>) {
        val updatedList = state.value.userIngredients.toMutableList()
        updatedList.addAll(ingredients)
        _state.update {
            it.copy(
                userIngredients = updatedList,
                ingredientSearchText = ""
            )
        }
    }

    override fun onSearchIngredientsList(text: String) {
        val myIngredients = state.value.userIngredients
        val searchedList = myIngredients.filter { ingredient ->
            ingredient.type.contains(text, ignoreCase = true)
        }

        _state.update {
            it.copy(
                searchIngredientsListText = text,
                searchResults = searchedList.toList()
            )
        }
    }

    override fun onDeleteIngredient(ingredient: Ingredient) {
        val myIngredients = state.value.userIngredients.toMutableList()
        val matchingIngredient = myIngredients.find { it.type == ingredient.type }

        if (matchingIngredient != null) {
            myIngredients.remove(matchingIngredient)
            _state.update { it.copy(userIngredients = myIngredients) }
        }

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

            else -> {}
        }
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }
}



