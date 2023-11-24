package android.kotlin.foodclub.viewModels.home

import android.content.Context
import android.graphics.BitmapFactory
import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.enums.QuantityUnit
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.others.BottomSheetItem
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.profile.UserPosts
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val profileRepo: ProfileRepository,
    private val productsRepo: ProductRepository,
    private val sessionCache: SessionCache
):ViewModel() {

    private val _postList = MutableStateFlow<List<VideoModel>>(listOf())
    val postList: StateFlow<List<VideoModel>> get() = _postList

    private val _postListPerCategory = MutableStateFlow<List<VideoModel>>(listOf())
    val postListPerCategory: StateFlow<List<VideoModel>> get() = _postListPerCategory

    private val _myFridgePosts = MutableStateFlow<List<UserPosts>>(listOf())
    val myFridgePosts: StateFlow<List<UserPosts>> get() = _myFridgePosts

    private val _sessionUserId = MutableStateFlow("")
    val sessionUserId: MutableStateFlow<String> get() = _sessionUserId

    private val _sessionUserName = MutableStateFlow("")
    val sessionUserName: MutableStateFlow<String> get() = _sessionUserName

    private val _productsDatabase = MutableStateFlow(ProductsData("", "", listOf()))
    val productsDatabase: StateFlow<ProductsData> get() = _productsDatabase

    private val _userIngredientsList = MutableStateFlow<List<Ingredient>>(listOf())
    val userIngredientsList: StateFlow<List<Ingredient>> get() = _userIngredientsList

    private val _mainSearchText = MutableStateFlow("")
    var mainSearchText = _mainSearchText.asStateFlow()

    private var searchJob: Job? = null

    private val _ingredientsSearchText = MutableStateFlow("")
    var ingredientsSearchText = _ingredientsSearchText.asStateFlow()

    var ingredientToEdit: MutableState<Ingredient?> = mutableStateOf(null)

    // filter products db list with search text
    var displayedProducts: StateFlow<List<Ingredient>> = combine(
        ingredientsSearchText,
        _productsDatabase
    ) { query, productsData ->
        if (query.isBlank()) {
            productsData.productsList
        } else {
            val matchingProducts = productsData.productsList.filter { product ->
                product.type.contains(query, ignoreCase = true)
            }
            matchingProducts
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _error = MutableStateFlow("")

    init {
        viewModelScope.launch {
            fetchProductsDatabase(mainSearchText.value)
        }
    }

    fun onMainSearchTextChange(text: String) {
        _mainSearchText.value = text
    }

    fun onSubSearchTextChange(text: String) {
        _ingredientsSearchText.value = text
        searchJob?.cancel() // Cancel the previous job if it exists

        searchJob = viewModelScope.launch {
            if (text != ""){
                delay(1000) // Delay for 1000 milliseconds
                fetchProductsDatabase(_ingredientsSearchText.value)
            }
        }
    }

    fun addToUserIngredients(ingredient: Ingredient) {
        val updatedList = _userIngredientsList.value.toMutableList()
        updatedList.add(ingredient)
        _userIngredientsList.value = updatedList
        _ingredientsSearchText.value = ""
    }
    fun addScanListToUserIngredients(ingredient: List<Ingredient>) {
        val updatedList: MutableList<Ingredient> = _userIngredientsList.value.toMutableList()
        updatedList.addAll(ingredient)

        _userIngredientsList.value = updatedList
        _ingredientsSearchText.value = ""
    }

    fun deleteIngredientFromList(ingredient: Ingredient){
        val list = _userIngredientsList.value.toMutableList()
        list.remove(ingredient)
        _userIngredientsList.value = list
    }

    fun updateIngredient(ingredient: Ingredient) {
        _userIngredientsList.update { currentList ->
            currentList.map { item ->
                if (item.id == ingredient.id) {
                    Ingredient(
                        id = item.id,  // Make sure to copy other properties if needed
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
        }

        // Now update the ingredientToEdit
        ingredientToEdit.value = ingredient

        // Now update the ingredientToEdit
        ingredientToEdit.value = ingredient
        Log.i("MYTAG","LIST AFTER ${_userIngredientsList.value[0].quantity}")
    }
    private suspend fun fetchProductsDatabase(searchText: String) {
        Log.e("MYTAG","made call $searchText")

        when(val resource = productsRepo.getProductsList(searchText)) {
            is Resource.Success -> {
                Log.e("MYTAG","SUCCESS")

                _error.value = ""
                _productsDatabase.value = resource.data!!
            }
            is Resource.Error -> {
                _error.value = resource.message!!
            }
        }
    }

    fun getPostsByWorld(worldCategory: Long) {
        _sessionUserId.value = sessionCache.getActiveSession()!!.sessionUser.userId.toString()

        viewModelScope.launch(Dispatchers.Main) {
            when(val resource = postRepository.getWorldCategoryPosts(worldCategory, 10, 1)) {
                is Resource.Success -> {
                    _postListPerCategory.value = resource.data!!
                }
                is Resource.Error -> {

                }
            }
        }
    }


    fun getPostData(postId:Long){
        val userId = sessionCache.getActiveSession()?.sessionUser?.userId ?: return
        viewModelScope.launch {
            when (val resource = postRepository.getPost(postId, userId)) {
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
            when(val resource = postRepository.getHomepagePosts(sessionUserId.value.toLong(), 10, 1)) {
                is Resource.Success -> {
                    _postList.value = resource.data!!
                }
                is Resource.Error -> {

                }
            }
        }

    }

    fun myFridgePosts() {
        viewModelScope.launch {
            when(val resource = profileRepo.retrieveProfileData(sessionUserId.value.toLong(), 10, 1)) {
                is Resource.Success -> {
                    _myFridgePosts.value = resource.data!!.userPosts
                }
                is Resource.Error -> {

                }
            }
        }

    }

    private val _capturedImage = mutableStateOf<ImageBitmap?>(null)
    var capturedImage: State<ImageBitmap?> = _capturedImage



    val ScanResultItemList: List<Ingredient> = listOf(
        Ingredient(
            id = "1",  // Make sure to copy other properties if needed
            quantity = 100,
            unit = QuantityUnit.GRAMS,
            type = "Capsicum",
            expirationDate = "Edit",
            imageUrl = R.drawable.capsicum
        ),
        Ingredient(
            id = "2",  // Make sure to copy other properties if needed
            quantity = 10,
            unit = QuantityUnit.GRAMS,
            type = "Tomato Soup",
            expirationDate = "Edit",
            imageUrl = R.drawable.tomato_ingredient
        ),
        Ingredient(
            id = "3",  // Make sure to copy other properties if needed
            quantity =1 ,
            unit = QuantityUnit.GRAMS,
            type = "Lemon",
            expirationDate = "Edit",
            imageUrl = R.drawable.lemon
        ),
        Ingredient(
            id = "4",  // Make sure to copy other properties if needed
            quantity = 1000,
            unit = QuantityUnit.GRAMS,
            type = "Egg",
            expirationDate = "Edit",
            imageUrl = R.drawable.egg
        ),


        // Add more items as needed
    )
    fun Scan(imageCapture: ImageCapture, context: Context) {
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    // Convert the ImageProxy to ImageBitmap
                    val buffer = image.planes[0].buffer
                    val bytes = ByteArray(buffer.remaining())
                    buffer.get(bytes)
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    _capturedImage.value = bitmap.asImageBitmap()

                    // Close the ImageProxy
                    image.close()
                }

                override fun onError(exception: ImageCaptureException) {
                    // Handle capture error
                }
            }
        )
    }


}



