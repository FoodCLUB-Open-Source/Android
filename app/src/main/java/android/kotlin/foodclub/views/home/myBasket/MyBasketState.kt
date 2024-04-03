package android.kotlin.foodclub.views.home.myBasket

import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.MyBasket
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.utils.composables.products.ProductState

data class MyBasketState(
    val basket: MyBasket?,
    val error: String,
    val productsList: List<Ingredient>,
    val selectedProductsList: List<String>,
    val productsDatabase: ProductsData,
    val productState: ProductState
) {
    companion object {
        fun default() = MyBasketState(
            basket = null,
            error = "",
            productsList = emptyList(),
            selectedProductsList = emptyList(),
            productsDatabase = ProductsData("", "", emptyList()),
            productState = ProductState.default()
        )
    }
}
