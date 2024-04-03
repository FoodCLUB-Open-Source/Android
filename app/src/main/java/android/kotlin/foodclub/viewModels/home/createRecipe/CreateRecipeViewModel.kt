package android.kotlin.foodclub.viewModels.home.createRecipe

import android.kotlin.foodclub.domain.enums.Category
import android.kotlin.foodclub.domain.enums.QuantityUnit
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.Product
import android.kotlin.foodclub.domain.models.products.toEmptyIngredient
import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.localdatasource.room.relationships.toProductModel
import android.kotlin.foodclub.repositories.RecipeRepository
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.utils.composables.products.ProductAction
import android.kotlin.foodclub.utils.composables.products.ProductState
import android.kotlin.foodclub.views.home.createRecipe.CreateRecipeState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        getTestData()
    }

    private fun getTestData() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val testIngredientsList = arrayListOf<Ingredient>()
                testIngredientsList.add(
                    Ingredient(
                        product = Product(
                            foodId = "1",
                            label = "Tomato paste",
                            image = "https://kretu.sts3.pl/foodclub_drawable/salad_ingredient.png",
                            units = QuantityUnit.entries
                        ),
                        quantity = 200,
                        unit = QuantityUnit.GRAM
                    )
                )
                testIngredientsList.add(
                    Ingredient(
                        product = Product(
                            foodId = "2",
                            label = "Potato wedges",
                            image = "https://kretu.sts3.pl/foodclub_drawable/salad_ingredient.png",
                            units = QuantityUnit.entries
                        ),
                        quantity = 200,
                        unit = QuantityUnit.GRAM
                    )
                )
                testIngredientsList.add(
                    Ingredient(
                        product = Product(
                            foodId = "3",
                            label = "Pasta",
                            image = "https://kretu.sts3.pl/foodclub_drawable/salad_ingredient.png",
                            units = QuantityUnit.entries
                        ),
                        quantity = 200,
                        unit = QuantityUnit.GRAM
                    )
                )
                _state.update { it.copy(ingredients = testIngredientsList) }
            }
        }
        _state.update {
            it.copy(
                categories = listOf(
                    Category.MEAT,
                    Category.FAT_REDUCTION,
                    Category.ITALIAN
                )
            )
        }
    }

    fun setVideoPath(path: String) {
        videoPath = path
    }

    override fun onIngredientExpanded(ingredientId: String) {
        if (_state.value.revealedIngredientId == ingredientId) return
        _state.update { it.copy(revealedIngredientId = ingredientId) }
    }

    override fun onIngredientCollapsed(ingredientId: String) {
        if (_state.value.revealedIngredientId != ingredientId) return
        _state.update { it.copy(revealedIngredientId = "") }
    }

    override fun onIngredientDeleted(ingredient: Ingredient) {
        if(!_state.value.ingredients.contains(ingredient)) return
        val newIngredients = _state.value.ingredients.toMutableList()
        newIngredients.remove(ingredient)
        _state.update { it.copy(ingredients = newIngredients) }
    }

    override fun clearIngredients() {
        _state.update { it.copy(ingredients = listOf()) }
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
