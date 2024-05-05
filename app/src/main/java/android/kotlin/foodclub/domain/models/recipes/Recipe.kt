package android.kotlin.foodclub.domain.models.recipes

import android.kotlin.foodclub.domain.enums.Category
import android.kotlin.foodclub.domain.enums.toRecipeCategoryDto
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.toRecipeIngredientDto
import android.kotlin.foodclub.network.retrofit.dtoModels.recipes.RecipeDto

data class Recipe(
    val id: Long,
    val postId: Long,
    val description: String?,
    val ingredients: List<Ingredient>,
    val servingSize: Int,
    val categories: List<Category>
)

fun Recipe.toRecipeDto(): RecipeDto {
    return RecipeDto(
        id = id,
        postId = postId,
        description = description,
        ingredients = ingredients.map { it.toRecipeIngredientDto(id) },
        servingSize = servingSize,
        categories = categories.map { it.toRecipeCategoryDto() }
    )
}
