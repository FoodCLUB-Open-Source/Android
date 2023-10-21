package android.kotlin.foodclub.viewmodels.home

import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.data.models.Recipe
import android.kotlin.foodclub.data.models.RecipeRepository
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.utils.enums.QuantityUnit
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
    private val productsRepository: ProductRepository
) : ViewModel() {
    private val _title = MutableLiveData("CreateRecipeViewModel View")
    val title: LiveData<String> get() = _title

    private val _ingredients = MutableStateFlow(listOf<Ingredient>())
    val ingredients: StateFlow<List<Ingredient>> get() = _ingredients
    private val _revealedIngredientId = MutableStateFlow("")
    val revealedIngredientId: StateFlow<String> get() = _revealedIngredientId

    private val _productsDatabase = MutableStateFlow(ProductsData("", "", listOf()))
    val productsDatabase: StateFlow<ProductsData> get() = _productsDatabase

    private val _error = MutableStateFlow("")

    // FOR CREATE RECIPE
    private val repository: RecipeRepository = RecipeRepository(RetrofitInstance.recipeAPI)

    init {
//        getTestData()
        fetchProductsDatabase("")
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
    }
    private fun fetchProductsDatabase(searchText: String) {
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

    // CREATE RECIPE FUNCTION
    suspend fun createRecipe(recipe: Recipe, userId: String): Boolean {
        try {
            // CREATES RECIPE + USER ID
            return repository.createRecipe(recipe, userId)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

}
