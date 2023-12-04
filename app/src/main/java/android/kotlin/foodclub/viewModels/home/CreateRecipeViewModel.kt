package android.kotlin.foodclub.viewModels.home

import android.kotlin.foodclub.domain.enums.QuantityUnit
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.domain.models.recipes.Category
import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.repositories.RecipeRepository
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.views.home.createRecipe.CreateRecipeState
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
) : ViewModel() {

    companion object {
        private val TAG = CreateRecipeViewModel::class.java.simpleName
    }

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
                        "1",
                        "Tomato paste",
                        200,
                        QuantityUnit.GRAMS,
                        "https://kretu.sts3.pl/foodclub_drawable/salad_ingredient.png"
                    )
                )
                testIngredientsList.add(
                    Ingredient(
                        "2",
                        "Potato wedges",
                        200,
                        QuantityUnit.GRAMS,
                        "https://kretu.sts3.pl/foodclub_drawable/salad_ingredient.png"
                    )
                )
                testIngredientsList.add(
                    Ingredient(
                        "3",
                        "Pasta",
                        200,
                        QuantityUnit.GRAMS,
                        "https://kretu.sts3.pl/foodclub_drawable/salad_ingredient.png"
                    )
                )
                _state.update { it.copy(ingredients = testIngredientsList) }
            }
        }
        _state.update {
            it.copy(
                categories = listOf(
                    Category(1, "Meat"),
                    Category(2, "Keto"),
                    Category(3, "High-protein"),
                    Category(4, "Vegan"),
                    Category(5, "Low-fat"),
                    Category(6, "Fat-reduction"),
                    Category(7, "Italian"),
                    Category(8, "Chinese"),
                    Category(9, "Vegetarian")
                ),
                chosenCategories = listOf(
                    Category(1, "Meat"),
                    Category(6, "Fat-reduction"),
                    Category(7, "Italian")
                )
            )
        }
    }

    fun fetchProductsDatabase(searchText: String = "") {
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

    fun addIngredient(ingredient: Ingredient) {
        val newIngredients = state.value.ingredients.toMutableList()
        newIngredients.add(ingredient)
        _state.update { it.copy(ingredients = newIngredients) }
    }

    fun onIngredientExpanded(ingredientId: String) {
        if (state.value.revealedIngredientId == ingredientId) return
        _state.update { it.copy(revealedIngredientId = ingredientId) }
    }

    fun onIngredientCollapsed(ingredientId: String) {
        if (state.value.revealedIngredientId != ingredientId) return
        _state.update { it.copy(revealedIngredientId = "") }
    }

    fun onIngredientDeleted(ingredient: Ingredient) {
        if(!state.value.ingredients.contains(ingredient)) return
        val newIngredients =state.value.ingredients.toMutableList()
        newIngredients.remove(ingredient)
        _state.update { it.copy(ingredients = newIngredients) }
    }

    fun unselectCategory(category: Category) {
        val newCategories = state.value.chosenCategories.toMutableList()
        newCategories.remove(category)
        _state.update { it.copy(chosenCategories = newCategories)}
    }

    fun selectCategory(category: Category) {
        val newCategories = state.value.chosenCategories.toMutableList()
        newCategories.add(category)
        _state.update { it.copy(chosenCategories = newCategories)}
    }

    fun fetchMoreProducts(searchText: String, onJobComplete: () -> Unit) {
        val job = viewModelScope.launch() {
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
        job.invokeOnCompletion { onJobComplete() }
    }

    // CREATE RECIPE FUNCTION
    suspend fun createRecipe(recipe: Recipe, userId: String): Boolean {
        return recipeRepository.createRecipe(recipe, userId)
    }

}
