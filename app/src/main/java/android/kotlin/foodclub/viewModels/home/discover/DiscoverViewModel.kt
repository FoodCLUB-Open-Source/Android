package android.kotlin.foodclub.viewModels.home.discover

import android.content.Context
import android.graphics.BitmapFactory
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.MyBasketCache
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.domain.models.products.toEmptyIngredient
import android.kotlin.foodclub.localdatasource.room.relationships.toProductModel
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.utils.composables.products.ProductAction
import android.kotlin.foodclub.utils.composables.products.ProductState
import android.kotlin.foodclub.utils.composables.products.ProductsEvents
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
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val productsRepo: ProductRepository,
    private val sessionCache: SessionCache,
    private val myBasketCache: MyBasketCache,
    val exoPlayer: ExoPlayer
) : ViewModel(), DiscoverEvents, ProductsEvents {

    companion object {
        private val TAG = DiscoverViewModel::class.java.simpleName
    }

    private val searchText = MutableStateFlow("")
    val searchProducts = searchText
        .flatMapLatest { text -> productsRepo.getProducts(text).flow }
        .map { pagingData -> pagingData.map { it.toProductModel().toEmptyIngredient() } }
        .cachedIn(viewModelScope)

    private val _state = MutableStateFlow(DiscoverState.default())
    val state: StateFlow<DiscoverState>
        get() = _state

    private val _productState =
        MutableStateFlow(ProductState.default().copy(allowExpiryDate = true))
    val productState = combine(_productState, searchText) { productState, searchText ->
        productState.copy(searchText = searchText)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductState.default())

    // TODO add real data for scanResultItemList

    init {
        exoPlayer.prepare()
        _state.update { state ->
            state.copy(
                username = sessionCache.getActiveSession()?.sessionUser?.username ?: "",
                myBasketCache = myBasketCache
            )
        }
        getPostsByWorld(197)
        getPostsByUserId()
        observeAndFetchSearchedIngredients()
    }

    private fun observeAndFetchSearchedIngredients() {
        viewModelScope.launch {
            _state
                .map { it.ingredientSearchText }
                .debounce(1000)
                .filter { it.isNotEmpty() }
                .collect { searchText ->
                    fetchProductsDatabase(searchText)
                }
        }
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

    override fun onResetSearchData() {
        _state.update {
            it.copy(
                ingredientSearchText = "",
                searchIngredientsListText = "",
                searchResults = emptyList(),
                productsData = ProductsData(
                    searchText = "",
                    nextUrl = "",
                    productsList = emptyList(),
                )
            )
        }
    }

    override fun onDeleteIngredient(ingredient: Ingredient) {
        val myIngredients = state.value.userIngredients.toMutableList()
        val matchingIngredient =
            myIngredients.find { it.product.foodId == ingredient.product.foodId }

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
                Log.e(TAG, "${resource.data!!.productsList}")
                _state.update {
                    it.copy(
                        error = "",
                        productsData = resource.data
                    )
                }
            }

            is Resource.Error -> {
                _state.update { it.copy(error = resource.message!!) }
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }

    override fun selectAction(ingredient: Ingredient, productAction: ProductAction) {
        _productState.update {
            it.copy(
                editedIngredient = ingredient,
                currentAction = productAction
            )
        }
    }

    override fun updateIngredient(ingredient: Ingredient) {
        val addedIngredients = _productState.value.addedProducts.toMutableList()
        var editedIngredient = addedIngredients.filter {
            it.product.foodId == ingredient.product.foodId
        }.getOrNull(0)

        if (editedIngredient == null) {
            editedIngredient = ingredient
            addedIngredients.add(editedIngredient)
            _productState.update { it.copy(filteredAddedProducts = addedIngredients) }
        } else {
            editedIngredient.quantity = ingredient.quantity
            editedIngredient.expirationDate = ingredient.expirationDate
            editedIngredient.unit = ingredient.unit
        }

        _productState.update { it.copy(addedProducts = addedIngredients) }
    }

    override fun deleteIngredient(ingredient: Ingredient) {
        val addedIngredients = _productState.value.addedProducts.toMutableList()
        val filteredAddedIngredient = _productState.value.filteredAddedProducts.toMutableList()

        addedIngredients.removeIf { it.product.foodId == ingredient.product.foodId }
        filteredAddedIngredient.removeIf { it.product.foodId == ingredient.product.foodId }
        _productState.update { it.copy(addedProducts = addedIngredients) }
    }

    override fun search(searchText: String) {
        this.searchText.update { searchText }
    }

    override fun dismissAction() {
        _productState.update { it.copy(currentAction = ProductAction.DEFAULT) }
    }

    override fun searchWithinAddedIngredients(searchText: String) {
        val addedIngredients = _productState.value.addedProducts
        val searchedList = addedIngredients.filter { ingredient ->
            ingredient.product.label.contains(searchText, ignoreCase = true)
        }

        _productState.update {
            it.copy(
                searchText = searchText,
                filteredAddedProducts = searchedList
            )
        }
    }
}



