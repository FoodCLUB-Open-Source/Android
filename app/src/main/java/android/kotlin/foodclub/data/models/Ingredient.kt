package android.kotlin.foodclub.data.models

import android.kotlin.foodclub.utils.enums.QuantityUnit

class Ingredient constructor(
    var id: String, type: String, quantity: Int, unit: QuantityUnit, imageUrl: Any = ""
) {
    var type: String = type
        private set

    var quantity: Int = quantity
        private set

    var unit: QuantityUnit = unit
        private set

    var imageUrl: Any = imageUrl
        private set

    fun decrementQuantity(decrementValue: Int) {
        if(quantity > decrementValue) quantity -= decrementValue
    }

    fun incrementQuantity(incrementValue: Int) {
        quantity += incrementValue
    }
}