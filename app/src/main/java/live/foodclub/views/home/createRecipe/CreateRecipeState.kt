package live.foodclub.views.home.createRecipe

import live.foodclub.domain.enums.Category
import live.foodclub.utils.composables.products.ProductState

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
