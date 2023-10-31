package android.kotlin.foodclub.viewModels.home

import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.repositories.RecipeRepository
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.domain.enums.QuantityUnit
import android.kotlin.foodclub.domain.models.recipes.Category
import android.kotlin.foodclub.utils.helpers.Resource
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
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    private val _title = MutableLiveData("CreateRecipeViewModel View")
    val title: LiveData<String> get() = _title

    private val _ingredients = MutableStateFlow(listOf<Ingredient>())
    val ingredients: StateFlow<List<Ingredient>> get() = _ingredients
    private val _revealedIngredientId = MutableStateFlow("")
    val revealedIngredientId: StateFlow<String> get() = _revealedIngredientId

    private val _productsDatabase = MutableStateFlow(ProductsData("", "", listOf()))
    val productsDatabase: StateFlow<ProductsData> get() = _productsDatabase

    private val _categories = MutableStateFlow(listOf<Category>())
    val categories: StateFlow<List<Category>> get() = _categories

    private val _chosenCategories = MutableStateFlow(listOf<Category>())
    val chosenCategories: StateFlow<List<Category>> get() = _chosenCategories

    private val _error = MutableStateFlow("")

    init {
        getTestData()
        fetchProductsDatabase()
    }

    private fun getTestData() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val testIngredientsList = arrayListOf<Ingredient>()
                testIngredientsList.add(Ingredient("1", "Tomato paste", 200, QuantityUnit.GRAMS, "https://kretu.sts3.pl/foodclub_drawable/salad_ingredient.png"))
                testIngredientsList.add(Ingredient("2", "Potato wedges", 200, QuantityUnit.GRAMS, "https://kretu.sts3.pl/foodclub_drawable/salad_ingredient.png"))
                testIngredientsList.add(Ingredient("3", "Pasta", 200, QuantityUnit.GRAMS, "https://kretu.sts3.pl/foodclub_drawable/salad_ingredient.png"))
                _ingredients.emit(testIngredientsList)
            }
        }
        _chosenCategories.value = listOf(
            Category(1, "Meat"), Category(2, "Keto"), Category(3, "High-protein"),
            Category(4, "Vegan"), Category(5, "Low-fat"), Category(6,"Fat-reduction"),
            Category(7, "Italian"), Category(8, "Chinese")
        )
    }
    private fun fetchProductsDatabase(searchText: String = "") {
        viewModelScope.launch() {
            when(val resource = productsRepository.getProductsList(searchText)) {
                is Resource.Success -> {
                    _error.value = ""
                    _productsDatabase.value = resource.data!!
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                }
            }
        }

    }

    fun onIngredientExpanded(ingredientId: String) {
        if(_revealedIngredientId.value == ingredientId) return
        _revealedIngredientId.value = ingredientId
    }

    fun onIngredientCollapsed(ingredientId: String) {
        if(_revealedIngredientId.value != ingredientId) return
        _revealedIngredientId.value = ""
    }

    fun onIngredientDeleted(ingredient: Ingredient) {
        if(!_ingredients.value.contains(ingredient)) return
        _ingredients.update {
            val mutableList = it.toMutableList()
            mutableList.remove(ingredient)
            mutableList
        }
    }

    fun unselectCategory(category: Category) {
        val newCategories = ArrayList(_chosenCategories.value)
        newCategories.remove(category)
        _chosenCategories.update { newCategories }
    }

    // CREATE RECIPE FUNCTION
    suspend fun createRecipe(recipe: Recipe, userId: String): Boolean {
        return recipeRepository.createRecipe(recipe, userId)
    }

}
