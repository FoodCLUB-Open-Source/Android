package android.kotlin.foodclub.domain.models.products

import android.kotlin.foodclub.domain.enums.QuantityUnit

class Ingredient(
    val product: Product,
    var quantity: Int,
    var unit: QuantityUnit,
    var expirationDate: String = "",

    var isSelected: Boolean = false
) {
    fun decrementQuantity(decrementValue: Int) {
        if(quantity > decrementValue) quantity -= decrementValue
    }

    fun incrementQuantity(incrementValue: Int) {
        quantity += incrementValue
    }

    fun copy(
        product: Product = this.product,
        quantity: Int = this.quantity,
        unit: QuantityUnit = this.unit,
    ): Ingredient {
        return Ingredient(product, quantity, unit)
    }
}