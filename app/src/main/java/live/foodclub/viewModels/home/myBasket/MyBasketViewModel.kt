package live.foodclub.viewModels.home.myBasket

import live.foodclub.domain.models.products.Ingredient
import live.foodclub.repositories.ProductRepository
import live.foodclub.domain.models.products.MyBasketCache
import live.foodclub.domain.models.products.toEmptyIngredient
import live.foodclub.localdatasource.room.relationships.toProductModel
import live.foodclub.utils.composables.products.ProductAction
import live.foodclub.utils.composables.products.ProductState
import live.foodclub.views.home.myBasket.MyBasketState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MyBasketViewModel @Inject constructor(
    private val basketCache: MyBasketCache,
    private val productRepository: ProductRepository
) : ViewModel(), MyBasketEvents {

    companion object {
        private val TAG = MyBasketViewModel::class.java.simpleName
    }

    private val searchText = MutableStateFlow("")
    val searchProducts = searchText
        .flatMapLatest { text -> productRepository.getProducts(text).flow }
        .map { pagingData -> pagingData.map { it.toProductModel().toEmptyIngredient() } }
        .cachedIn(viewModelScope)

    private val _productState = MutableStateFlow(ProductState.default())

    private val _state = MutableStateFlow(MyBasketState.default())
    val state = combine(_state, _productState) { state, productState ->
        state.copy(
            productState = productState
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),
        MyBasketState.default())

    init {
        viewModelScope.launch {
            val basket = basketCache.getBasket()
            basket.clearSelectedIngredients()
            _productState.update {
                it.copy(addedProducts = basket.ingredients)
            }
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
        _productState.update {
            it.copy(addedProducts = basket.ingredients)
        }
        _state.update {
            it.copy(
                basket = basket,
                productsList = basket.ingredients,
                selectedProductsList = basket.selectedIngredients
            )
        }
        saveBasket()

    }

    override fun selectAction(ingredient: Ingredient, productAction: ProductAction) {
        _productState.update {
            it.copy(
                currentAction = productAction,
                editedIngredient = ingredient
            )
        }
    }

    override fun updateIngredient(ingredient: Ingredient) {
        val basket = state.value.basket!!
        var editedIngredient = basket.ingredients.filter {
            it.product.foodId == ingredient.product.foodId
        }.getOrNull(0)

        if(editedIngredient == null) {
            editedIngredient = ingredient
            basket.addIngredient(editedIngredient)
        } else {
            editedIngredient.quantity = ingredient.quantity
            editedIngredient.unit = ingredient.unit
        }

        _productState.update {
            it.copy(addedProducts = basket.ingredients)
        }
        _state.update {
            it.copy(
                basket = basket,
                productsList = basket.ingredients
            )
        }
        saveBasket()
    }

    override fun deleteIngredient(ingredient: Ingredient) {
        ingredient.quantity = 0
        ingredient.expirationDate = ""
        val basket = state.value.basket!!
        basket.removeIngredient(ingredient.product.foodId)
        _productState.update {
            it.copy(addedProducts = basket.ingredients)
        }
        _state.update {
            it.copy(
                basket = basket,
                productsList = basket.ingredients
            )
        }
        saveBasket()
    }

    override fun search(searchText: String) {
        this.searchText.update { searchText }
    }

    override fun dismissAction() {
        _productState.update { it.copy(currentAction = ProductAction.DEFAULT) }
    }

    override fun searchWithinAddedIngredients(searchText: String) { }
}