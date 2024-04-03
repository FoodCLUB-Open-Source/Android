package android.kotlin.foodclub.viewModels.home.createRecipe

import android.kotlin.foodclub.domain.enums.Category
import android.kotlin.foodclub.domain.enums.QuantityUnit
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.Product
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.repositories.RecipeRepository
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.views.home.createRecipe.CreateRecipeState
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateRecipeViewModel @Inject constructor(
    private val productsRepository: ProductRepository,
    private val recipeRepository: RecipeRepository,
) : ViewModel(), CreateRecipeEvents {

    companion object {
        private val TAG = CreateRecipeViewModel::class.java.simpleName
    }

    private var videoPath: String = ""

    private val _state = MutableStateFlow(CreateRecipeState.default())
    val state: StateFlow<CreateRecipeState>
        get() = _state

    init {
        _state.update { it.copy(title = TAG) }
        getTestData()
        fetchProductsDatabase()
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

     override fun fetchProductsDatabase(searchText: String) {
        viewModelScope.launch {
            when (val resource = productsRepository.getProductsList(searchText)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            error = "",
                            products = resource.data!!
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update { it.copy(error = resource.message!!) }
                }
            }
        }

    }

    override fun addIngredient(ingredient: Ingredient) {
        val newIngredients = state.value.ingredients.toMutableList()
        newIngredients.add(ingredient)
        _state.update { it.copy(ingredients = newIngredients) }
    }

    override fun onIngredientExpanded(ingredientId: String) {
        if (state.value.revealedIngredientId == ingredientId) return
        _state.update { it.copy(revealedIngredientId = ingredientId) }
    }

    override fun onIngredientCollapsed(ingredientId: String) {
        if (state.value.revealedIngredientId != ingredientId) return
        _state.update { it.copy(revealedIngredientId = "") }
    }

    override fun onIngredientDeleted(ingredient: Ingredient) {
        if(!state.value.ingredients.contains(ingredient)) return
        val newIngredients =state.value.ingredients.toMutableList()
        newIngredients.remove(ingredient)
        _state.update { it.copy(ingredients = newIngredients) }
    }

    override fun clearIngredients() {
        _state.update { it.copy(ingredients = listOf()) }
    }

    override fun unselectCategory(category: Category) {
        val newCategories = state.value.categories.toMutableList()
        newCategories.remove(category)
        _state.update { it.copy(categories = newCategories)}
    }

    override fun selectCategory(category: Category) {
        val newCategories = state.value.categories.toMutableList()
        newCategories.add(category)
        _state.update { it.copy(categories = newCategories)}
    }

    override fun clearCategories() {
        _state.update { it.copy(categories = listOf())}
    }

    override fun fetchMoreProducts(searchText: String, onLoadCompleted: () -> Unit) {
        val job = viewModelScope.launch {
            when (
                val resource = productsRepository.getProductsList(
                   searchText =  searchText,
                    session = state.value.products.getSessionIdFromUrl()
                )
            ) {
                is Resource.Success -> {
                    val response = resource.data!!

                    _state.update { it.copy(
                        error = "",
                        products = ProductsData(
                            searchText = it.products.searchText,
                            productsList = it.products.productsList + response.productsList,
                            nextUrl = response.nextUrl
                        )
                    ) }
                }

                is Resource.Error -> {
                    _state.update { it.copy(error = resource.message!!) }
                    Log.d(TAG, "error: ${_state.value.error}")
                }
            }
        }
        job.invokeOnCompletion { onLoadCompleted() }
    }

    // CREATE RECIPE FUNCTION
    override  suspend fun createRecipe(recipe: Recipe): Boolean {
            return recipeRepository.createRecipe(recipe)
    }

}
