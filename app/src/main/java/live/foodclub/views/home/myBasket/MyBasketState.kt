package live.foodclub.views.home.myBasket

import live.foodclub.domain.models.products.Ingredient
import live.foodclub.domain.models.products.MyBasket
import live.foodclub.utils.composables.products.ProductState

data class MyBasketState(
    val basket: MyBasket?,
    val error: String,
    val productsList: List<Ingredient>,
    val selectedProductsList: List<String>,
    val productState: ProductState
) {
    companion object {
        fun default() = MyBasketState(
            basket = null,
            error = "",
            productsList = emptyList(),
            selectedProductsList = emptyList(),
            productState = ProductState.default()
        )
    }
}
