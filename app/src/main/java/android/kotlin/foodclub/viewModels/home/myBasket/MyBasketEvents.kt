package android.kotlin.foodclub.viewModels.home.myBasket

import android.kotlin.foodclub.domain.models.products.Ingredient

interface MyBasketEvents {
    fun fetchMoreProducts(searchText: String, onLoadComplete : () -> Unit)
    fun fetchProductsDatabase(searchText: String)
    fun addIngredient(ingredient: Ingredient)
    fun selectIngredient(ingredientId: String)
    fun unselectIngredient(ingredientId: String)
    fun saveBasket()
    fun deleteSelectedIngredients()
}