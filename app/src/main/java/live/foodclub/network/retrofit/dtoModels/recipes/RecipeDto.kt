package live.foodclub.network.retrofit.dtoModels.recipes

import live.foodclub.domain.models.recipes.Recipe
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RecipeDto(
    val id: Long,

    @SerializedName("post_id")
    val postId: Long,

    @SerializedName("recipe_description")
    val description: String?,

    @SerializedName("recipe_ingredients")
    val ingredients: List<RecipeIngredientDto>,
    @SerializedName("serving_size")
    val servingSize: Int,

    val categories: List<RecipeCategoryDto>
)

fun RecipeDto.toRecipeModel(): Recipe {
    return Recipe(
        id = id,
        postId = postId,
        description = description,
        ingredients = ingredients.map { it.toIngredientModel() },
        servingSize = servingSize,
        categories = categories.mapNotNull { it.toCategory() }
    )
}
