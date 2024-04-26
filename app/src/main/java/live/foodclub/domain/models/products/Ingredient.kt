package live.foodclub.domain.models.products

import live.foodclub.domain.enums.QuantityUnit
import live.foodclub.network.retrofit.dtoModels.recipes.RecipeIngredientDto

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

fun Ingredient.toRecipeIngredientDto(recipeId: Long): RecipeIngredientDto {
    return RecipeIngredientDto(
        recipeId = recipeId,
        productId = product.foodId,
        quantity = quantity,
        unit = unit.longName,
        label = product.label,
        imageUrl = product.image
    )
}