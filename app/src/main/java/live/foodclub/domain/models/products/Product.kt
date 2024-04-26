package live.foodclub.domain.models.products

import live.foodclub.domain.enums.QuantityUnit

data class Product(
    val foodId: String,
    val label: String,
    val image: String?,
    val units: List<QuantityUnit>
)

fun Product.toEmptyIngredient(): Ingredient {
    return Ingredient(
        product = this,
        quantity = 0,
        unit = if (units.isEmpty()) QuantityUnit.GRAM else units[0]
    )
}
