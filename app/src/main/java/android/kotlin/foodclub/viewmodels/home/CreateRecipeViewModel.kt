package android.kotlin.foodclub.viewmodels.home

import android.kotlin.foodclub.data.models.IngredientModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateRecipeViewModel : ViewModel() {
    private val _title = MutableLiveData("CreateRecipeViewModel View")
    val title: LiveData<String> get() = _title

    private val _ingredients = MutableStateFlow(listOf<IngredientModel>())
    val ingredients: StateFlow<List<IngredientModel>> get() = _ingredients
    private val _revealedIngredientId = MutableStateFlow(0)
    val revealedIngredientId: StateFlow<Int> get() = _revealedIngredientId

    init {
        getTestData()
    }

    private fun getTestData() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val testIngredientsList = arrayListOf<IngredientModel>()
                testIngredientsList.add(IngredientModel(1, "Tomato paste", 200))
                testIngredientsList.add(IngredientModel(2, "Potato wedges", 200))
                testIngredientsList.add(IngredientModel(3, "Pasta", 200))
                _ingredients.emit(testIngredientsList)
            }
        }
    }
    fun onIngredientExpanded(ingredientId: Int) {
        if(_revealedIngredientId.value == ingredientId) return
        _revealedIngredientId.value = ingredientId
    }

    fun onIngredientCollapsed(ingredientId: Int) {
        if(_revealedIngredientId.value != ingredientId) return
        _revealedIngredientId.value = 0
    }

    fun onIngredientDeleted(ingredient: IngredientModel) {
        if(!_ingredients.value.contains(ingredient)) return
        _ingredients.update {
            val mutableList = it.toMutableList()
            mutableList.remove(ingredient)
            mutableList
        }
    }
}
