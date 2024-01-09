package android.kotlin.foodclub.viewModels.home.myBasket

import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.domain.models.products.MyBasketCache
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.views.home.myBasket.MyBasketState
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
) : ViewModel(), MyBasketEvents {

    companion object {
        private val TAG = MyBasketViewModel::class.java.simpleName
    }

    private val _state = MutableStateFlow(MyBasketState.default())
    val state: StateFlow<MyBasketState>
        get() = _state

    init {
        fetchProductsDatabase("")
        viewModelScope.launch {
            val basket = basketCache.getBasket()
            basket.clearSelectedIngredients()
            _state.update {
                it.copy(
                    basket = basket,
                    productsList = basket.ingredients,
                    selectedProductsList = basketCache.getBasket().selectedIngredients
                )
            }
        }
    }

    override fun saveBasket() {
        basketCache.saveBasket(state.value.basket!!)
    }

    override fun selectIngredient(ingredientId: String) {
        val basket = state.value.basket!!
        basket.selectIngredient(ingredientId)
        _state.update {
            it.copy(
                basket = basket,
                selectedProductsList = basket.selectedIngredients
            )
        }
    }

    override fun unselectIngredient(ingredientId: String) {
        val basket = state.value.basket!!
        basket.unselectIngredient(ingredientId)
        _state.update {
            it.copy(
                basket = basket,
                selectedProductsList = basket.selectedIngredients
            )
        }
    }

    override fun deleteSelectedIngredients() {
        val basket = state.value.basket!!
        basket.deleteIngredients()
        _state.update {
            it.copy(
                basket = basket,
                productsList = basket.ingredients,
                selectedProductsList = basket.selectedIngredients
            )
        }
        saveBasket()

    }

    override fun fetchProductsDatabase(searchText: String) {
        viewModelScope.launch {
            when (val resource = productRepository.getProductsList(searchText)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            error = "",
                            productsDatabase = resource.data!!
                        )
                    }
                    Log.d(TAG, "database: ${state.value.productsDatabase.productsList}")
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = resource.message!!
                        )
                    }
                    Log.d(TAG, "error: ${state.value.error}")
                }
            }
        }
    }

    override fun fetchMoreProducts(searchText: String, onLoadComplete: () -> Unit) {
        val job = viewModelScope.launch {
            when (
                val resource = productRepository.getProductsList(
                    searchText = searchText,
                    session = state.value.productsDatabase.getSessionIdFromUrl()
                )
            ) {
                is Resource.Success -> {
                    val response = resource.data!!
                    _state.update {
                        it.copy(
                            error = "",
                            productsDatabase = ProductsData(
                                searchText = it.productsDatabase.searchText,
                                productsList = it.productsDatabase.productsList + response.productsList,
                                nextUrl = response.nextUrl
                            )
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = resource.message!!
                        )
                    }
                    Log.d(TAG, "error: ${state.value.error}")
                }
            }
        }
        job.invokeOnCompletion { onLoadComplete() }
    }

    override fun addIngredient(ingredient: Ingredient) {
        val basket = state.value.basket!!
        basket.addIngredient(ingredient)
        _state.update {
            it.copy(
                basket = basket,
                productsList = basket.ingredients
            )
        }
        saveBasket()

    }

    fun refreshBasket() {
        _state.update { it.copy(productsList = basketCache.getBasket().ingredients) }
    }

    fun updateSelectedIngredients(selectedIngredients: List<Ingredient>) {
        // TOOD Update state, selectedIngredients string or ingredient list
    }

    fun addIngredientsToBasket(ingredients: List<Ingredient>) {
        ingredients.forEach { ingredient ->
            addIngredient(ingredient = ingredient)
        }

        _state.update { it.copy(selectedProductsList = emptyList()) }
    }

    fun removeIngredient(id: String) {
        val basket = state.value.basket!!
        basket.removeIngredient(id)
        _state.update {
            it.copy(
                basket = basket,
                productsList = basket.ingredients
            )
        }
        saveBasket()
    }
}