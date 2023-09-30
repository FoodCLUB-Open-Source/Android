package android.kotlin.foodclub.viewmodels.home

import android.kotlin.foodclub.data.models.Ingredient
import android.kotlin.foodclub.utils.helpers.MyBasketCache
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBasketViewModel @Inject constructor(
    private val basketCache: MyBasketCache
): ViewModel() {
    private val _basket = MutableStateFlow(basketCache.getBasket())

    private val _productsList = MutableStateFlow<List<Ingredient>> (_basket.value.ingredients)
    val productsList: StateFlow<List<Ingredient>> get() = _productsList

    private val _selectedProductsList = MutableStateFlow<List<Int>>(listOf())
    val selectedProductsList: StateFlow<List<Int>> get() = _selectedProductsList

    init {
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

    fun removeIngredient(id: Int) {
        _basket.value.removeIngredient(id)
        _productsList.update { _basket.value.ingredients }
        saveBasket()
    }

    fun selectIngredient(id: Int) {
        _basket.value.selectIngredient(id)
        _selectedProductsList.update { _basket.value.selectedIngredients }
    }

    fun unselectIngredient(id: Int) {
        _basket.value.unselectIngredient(id)
        _selectedProductsList.update { _basket.value.selectedIngredients }
    }

    fun deleteSelectedIngredients() {
        _basket.value.deleteIngredients()
        _productsList.update { _basket.value.ingredients }
        _selectedProductsList.update { _basket.value.selectedIngredients }
        saveBasket()
    }
}