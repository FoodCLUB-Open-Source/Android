package live.foodclub.domain.models.recipes

import live.foodclub.domain.enums.Category
import live.foodclub.domain.enums.toRecipeCategoryDto
import live.foodclub.domain.models.products.Ingredient
import live.foodclub.domain.models.products.toRecipeIngredientDto
import live.foodclub.network.retrofit.dtoModels.recipes.RecipeDto

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
