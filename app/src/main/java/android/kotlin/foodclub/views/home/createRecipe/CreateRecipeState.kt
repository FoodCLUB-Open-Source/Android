package android.kotlin.foodclub.views.home.createRecipe

import android.kotlin.foodclub.domain.enums.Category
import android.kotlin.foodclub.utils.composables.products.ProductState

data class CreateRecipeState(
    val title: String,
    val categories: List<Category>,
    val error: String,
    val productState: ProductState
) {
    companion object {
        fun default() = CreateRecipeState(
            title = "",
            categories = emptyList(),
            error = "",
            productState = ProductState.default()
        )
    }
}
