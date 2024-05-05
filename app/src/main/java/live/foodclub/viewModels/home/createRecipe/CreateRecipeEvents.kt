package live.foodclub.viewModels.home.createRecipe

import live.foodclub.domain.enums.Category
import live.foodclub.domain.models.recipes.Recipe
import live.foodclub.utils.composables.products.ProductsEvents

interface CreateRecipeEvents: ProductsEvents {
    fun unselectCategory(category: Category)
    fun clearCategories()
    fun selectCategory(category: Category)
    fun clearIngredients()
    suspend fun createRecipe(recipe: Recipe): Boolean
}