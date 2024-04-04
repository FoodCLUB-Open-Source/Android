package android.kotlin.foodclub.viewModels.home.createRecipe

import android.kotlin.foodclub.domain.enums.Category
import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.utils.composables.products.ProductsEvents

interface CreateRecipeEvents: ProductsEvents {
    fun unselectCategory(category: Category)
    fun clearCategories()
    fun selectCategory(category: Category)
    fun clearIngredients()
    suspend fun createRecipe(recipe: Recipe): Boolean
}