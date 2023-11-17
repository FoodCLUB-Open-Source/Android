package android.kotlin.foodclub.domain.models.products

import android.kotlin.foodclub.domain.enums.QuantityUnit

class Ingredient(
    var id: String,
    val type: String,
    var quantity: Int,
    var unit: QuantityUnit,
    imageUrl: Any = "",
    var expirationDate: String = ""
) {

    var imageUrl: Any = imageUrl
        private set

    fun decrementQuantity(decrementValue: Int) {
        if(quantity > decrementValue) quantity -= decrementValue
    }

    fun incrementQuantity(incrementValue: Int) {
        quantity += incrementValue
    }
}