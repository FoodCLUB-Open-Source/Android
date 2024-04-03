package android.kotlin.foodclub.views.home.createRecipe

import android.kotlin.foodclub.domain.enums.Category
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.utils.composables.products.ProductState

data class CreateRecipeState(
    val title: String,
    val ingredients: List<Ingredient>,
    val revealedIngredientId: String,
    val categories: List<Category>,
    val products: ProductsData,
    val error: String,
    val productState: ProductState
) {
    companion object {
        fun default() = CreateRecipeState(
            title = "",
            ingredients = emptyList(),
            revealedIngredientId = "",
            categories = emptyList(),
            products = ProductsData(
                searchText = "",
                nextUrl = "",
                productsList = emptyList()
            ),
            error = "",
            productState = ProductState.default()
        )
    }
}
