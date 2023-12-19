package android.kotlin.foodclub.views.home.myBasket

import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.MyBasket
import android.kotlin.foodclub.domain.models.products.ProductsData

data class MyBasketState(
    val basket: MyBasket?,
    val error: String,
    val productsList: List<Ingredient>,
    val selectedProductsList: List<String>,
    val productsDatabase: ProductsData
) {
    companion object {
        fun default() = MyBasketState(
            basket = null,
            error = "",
            productsList = emptyList(),
            selectedProductsList = emptyList(),
            productsDatabase = ProductsData("", "", emptyList())
        )
    }
}
