package android.kotlin.foodclub.network.retrofit.dtoModels.recipes

import android.kotlin.foodclub.domain.enums.Category
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RecipeCategoryDto(
    @SerializedName("category_name")
    val category: String
)

fun RecipeCategoryDto.toCategory(): Category? {
    return Category.deriveFromName(category)
}