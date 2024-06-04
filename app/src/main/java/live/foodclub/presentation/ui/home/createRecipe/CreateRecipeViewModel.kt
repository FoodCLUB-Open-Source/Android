package live.foodclub.presentation.ui.home.createRecipe

import live.foodclub.domain.enums.Category
import live.foodclub.domain.models.products.Ingredient
import live.foodclub.domain.models.products.toEmptyIngredient
import live.foodclub.domain.models.recipes.Recipe
import live.foodclub.localdatasource.room.relationships.toProductModel
import live.foodclub.repositories.RecipeRepository
import live.foodclub.repositories.ProductRepository
import live.foodclub.utils.composables.products.ProductAction
import live.foodclub.utils.composables.products.ProductState
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
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CreateRecipeViewModel @Inject constructor(
    private val productsRepository: ProductRepository,
    private val recipeRepository: RecipeRepository,
) : ViewModel(), CreateRecipeEvents {

    companion object {
        private val TAG = CreateRecipeViewModel::class.java.simpleName
    }

    private var videoPath: String = ""

    private val searchText = MutableStateFlow("")
    val searchProducts = searchText
        .flatMapLatest { text -> productsRepository.getProducts(text).flow }
        .map { pagingData -> pagingData.map { it.toProductModel().toEmptyIngredient() } }
        .cachedIn(viewModelScope)

    private val _productState = MutableStateFlow(ProductState.default())

    private val _state = MutableStateFlow(CreateRecipeState.default())
    val state = combine(_state, _productState) { state, productState ->
        state.copy(
            productState = productState
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),
        CreateRecipeState.default())

    init {
        _state.update { it.copy(title = TAG) }
    }

    fun setVideoPath(path: String) {
        videoPath = path
    }

    override fun clearIngredients() {
        _productState.update { it.copy(addedProducts = listOf()) }
    }

    override fun unselectCategory(category: Category) {
        val newCategories = _state.value.categories.toMutableList()
        newCategories.remove(category)
        _state.update { it.copy(categories = newCategories)}
    }

    override fun selectCategory(category: Category) {
        val newCategories = _state.value.categories.toMutableList()
        newCategories.add(category)
        _state.update { it.copy(categories = newCategories)}
    }

    override fun clearCategories() {
        _state.update { it.copy(categories = listOf())}
    }

    // CREATE RECIPE FUNCTION
    override  suspend fun createRecipe(recipe: Recipe): Boolean {
            return recipeRepository.createRecipe(recipe)
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

        if(editedIngredient == null) {
            editedIngredient = ingredient
            addedIngredients.add(editedIngredient)
        } else {
            editedIngredient.quantity = ingredient.quantity
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

    override fun searchWithinAddedIngredients(searchText: String) { }
}
