package live.foodclub.utils.composables.products

import live.foodclub.domain.enums.QuantityUnit
import live.foodclub.domain.models.products.Ingredient
import live.foodclub.domain.models.products.Product

data class ProductState(
    val addedProducts: List<Ingredient>,
    val filteredAddedProducts: List<Ingredient>,
    val allowExpiryDate: Boolean,
    val currentAction: ProductAction,
    val editedIngredient: Ingredient,
    val searchText: String
) {
    companion object {
        fun default(): ProductState {
            return ProductState(
                addedProducts = listOf(),
                filteredAddedProducts = listOf(),
                allowExpiryDate = false,
                currentAction = ProductAction.DEFAULT,
                editedIngredient = Ingredient(
                    product = Product(
                        foodId = "1",
                        label = "Undefined",
                        image = null,
                        units = QuantityUnit.entries
                    ),
                    quantity = 0,
                    unit = QuantityUnit.GRAM
                ),
                searchText = ""
            )
        }
    }
}
