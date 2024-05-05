package android.kotlin.foodclub.utils.composables.products

import android.kotlin.foodclub.domain.models.products.Ingredient

interface ProductsEvents {
    fun selectAction(ingredient: Ingredient, productAction: ProductAction)
    fun updateIngredient(ingredient: Ingredient)
    fun deleteIngredient(ingredient: Ingredient)
    fun search(searchText: String)
    fun dismissAction()
    fun searchWithinAddedIngredients(searchText: String)
}