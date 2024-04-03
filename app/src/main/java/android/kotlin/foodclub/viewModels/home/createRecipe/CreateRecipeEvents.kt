package android.kotlin.foodclub.viewModels.home.createRecipe

import android.kotlin.foodclub.domain.enums.Category
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.utils.composables.products.ProductsEvents

interface CreateRecipeEvents: ProductsEvents {
    fun unselectCategory(category: Category)

    fun clearCategories()
    fun selectCategory(category: Category)
    fun onIngredientExpanded(ingredientId: String)
    fun onIngredientCollapsed(ingredientId: String)
    fun onIngredientDeleted(ingredient: Ingredient)

    fun clearIngredients()
    suspend fun createRecipe(recipe: Recipe): Boolean
}