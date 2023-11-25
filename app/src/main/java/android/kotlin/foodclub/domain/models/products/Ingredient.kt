package android.kotlin.foodclub.domain.models.products

import android.kotlin.foodclub.domain.enums.QuantityUnit

class Ingredient constructor(
    var id: String, type: String, quantity: Int, unit: QuantityUnit, imageUrl: Any = "",
    var isSelected: Boolean = false
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

    fun copy(
        id: String = this.id,
        type: String = this.type,
        quantity: Int = this.quantity,
        unit: QuantityUnit = this.unit,
        imageUrl: Any = this.imageUrl,
        isSelected: Boolean = this.isSelected
    ): Ingredient {
        return Ingredient(id, type, quantity, unit, imageUrl, isSelected)
    }
}