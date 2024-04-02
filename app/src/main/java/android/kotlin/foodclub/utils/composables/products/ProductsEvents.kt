package android.kotlin.foodclub.utils.composables.products

import android.kotlin.foodclub.domain.models.products.Ingredient

interface ProductsEvents {
    fun selectAction(ingredient: Ingredient, productAction: ProductAction)
    fun addIngredient(ingredient: Ingredient)
    fun deleteIngredient(ingredient: Ingredient)
    fun search(searchText: String)
}