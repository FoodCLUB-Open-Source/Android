package android.kotlin.foodclub.network.retrofit.dtoModels.recipes

import android.kotlin.foodclub.domain.enums.QuantityUnit
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.Product
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RecipeIngredientDto(
    @SerializedName("recipe_id")
    val recipeId: Long,
    @SerializedName("product_id")
    val productId: String,
    val quantity: Int,
    val unit: String,
    val label: String,
    @SerializedName("imageurl")
    val imageUrl: String? = null
)

fun RecipeIngredientDto.toIngredientModel(): Ingredient {
    return Ingredient(
        product = Product(
            foodId = productId,
            label = label,
            image = imageUrl,
            units = listOf(QuantityUnit.parseUnit(unit))
        ),
        quantity = quantity,
        unit = QuantityUnit.parseUnit(unit)
    )
}
