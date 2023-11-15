package android.kotlin.foodclub.viewModels.home

import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.profile.UserPosts
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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
        viewModelScope.launch {
            fetchProductsDatabase(text)
        }
    }

    fun addToUserIngredients(ingredient: Ingredient) {
        val updatedList = _userIngredientsList.value.toMutableList()
        updatedList.add(ingredient)
        _userIngredientsList.value = updatedList
        _ingredientsSearchText.value = ""
    }

    fun deleteIngredientFromList(ingredient: Ingredient){
        val list = _userIngredientsList.value.toMutableList()
        list.remove(ingredient)
        _userIngredientsList.value = list
    }

    fun updateIngredient(ingredient: Ingredient) {
        Log.i("MYTAG","INGR ${ingredient.quantity}")
        Log.i("MYTAG","INGR ${ingredient.unit}")
        Log.i("MYTAG","LIST BEFORE ${_userIngredientsList.value[0].quantity}")

        val updateList = _userIngredientsList.value.toMutableList()
        updateList.forEach { item->
            if (item.id == ingredient.id){
                item.quantity = ingredient.quantity
                item.unit = ingredient.unit
            }
        }
        ingredientToEdit.value = ingredient
        _userIngredientsList.value = updateList
        Log.i("MYTAG","LIST AFTER ${_userIngredientsList.value[0].quantity}")
    }

    private suspend fun fetchProductsDatabase(searchText: String) {
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

}



