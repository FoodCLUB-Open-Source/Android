package android.kotlin.foodclub.network.retrofit.dtoModels.recipes

import com.google.gson.annotations.SerializedName

data class RecipeDto(
    val id: Long,

    @SerializedName("post_id")
    val postId: Long,

    @SerializedName("recipe_description")
    val description: String,

    @SerializedName("recipe_ingredients")
    val ingredients: List<String>,
    @SerializedName("serving_size")
    val servingSize: Int
)
