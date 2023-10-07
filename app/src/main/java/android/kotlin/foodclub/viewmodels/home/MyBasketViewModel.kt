package android.kotlin.foodclub.viewmodels.home

import android.kotlin.foodclub.data.models.Ingredient
import android.kotlin.foodclub.data.models.ProductsData
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.utils.helpers.MyBasketCache
import android.kotlin.foodclub.utils.helpers.Resource
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBasketViewModel @Inject constructor(
    private val basketCache: MyBasketCache,
    private val productRepository: ProductRepository
): ViewModel() {
    private val _basket = MutableStateFlow(basketCache.getBasket())

    private val _productsList = MutableStateFlow<List<Ingredient>> (_basket.value.ingredients)
    val productsList: StateFlow<List<Ingredient>> get() = _productsList

    private val _selectedProductsList = MutableStateFlow<List<String>>(listOf())
    val selectedProductsList: StateFlow<List<String>> get() = _selectedProductsList

    private val _productsDatabase = MutableStateFlow(ProductsData("", "", listOf()))
    val productsDatabase: StateFlow<ProductsData> get() = _productsDatabase

    private val _error = MutableStateFlow("")

    init {
        fetchProductsDatabase("")
        _basket.value.clearSelectedIngredients()
        _selectedProductsList.update { _basket.value.selectedIngredients }
    }

    fun saveBasket() {
        basketCache.saveBasket(_basket.value)
    }

    fun addIngredient(ingredient: Ingredient) {
        _basket.value.addIngredient(ingredient)
        _productsList.update { _basket.value.ingredients }
        saveBasket()
    }

    fun removeIngredient(id: String) {
        _basket.value.removeIngredient(id)
        _productsList.update { _basket.value.ingredients }
        saveBasket()
    }

    fun selectIngredient(id: String) {
        _basket.value.selectIngredient(id)
        _selectedProductsList.update { _basket.value.selectedIngredients }
    }

    fun unselectIngredient(id: String) {
        _basket.value.unselectIngredient(id)
        _selectedProductsList.update { _basket.value.selectedIngredients }
    }

    fun deleteSelectedIngredients() {
        _basket.value.deleteIngredients()
        _productsList.update { _basket.value.ingredients }
        _selectedProductsList.update { _basket.value.selectedIngredients }
        saveBasket()
    }

    fun fetchProductsDatabase(searchText: String) {
        viewModelScope.launch() {
            when(val resource = productRepository.getProductsList(searchText)) {
                is Resource.Success -> {
                    _error.value = ""
                    _productsDatabase.value = resource.data!!
                    Log.d("MyBasketViewModel", "database: ${_productsDatabase.value.productsList}")
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                    Log.d("MyBasketViewModel", "error: ${_error.value}")
                }
            }
        }
    }

    fun fetchMoreProducts(searchText: String, onJobComplete: () -> Unit) {
        val job = viewModelScope.launch() {
            when(
                val resource = productRepository.getProductsList(
                    searchText,
                    _productsDatabase.value.getSessionIdFromUrl()
                )
            ) {
                is Resource.Success -> {
                    _error.value = ""
                    val response = resource.data!!
                    _productsDatabase.value = ProductsData(
                        searchText = _productsDatabase.value.searchText,
                        productsList = _productsDatabase.value.productsList + response.productsList,
                        nextUrl = response.nextUrl
                    )
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                    Log.d("MyBasketViewModel", "error: ${_error.value}")
                }
            }
        }
        job.invokeOnCompletion { onJobComplete() }
    }
}