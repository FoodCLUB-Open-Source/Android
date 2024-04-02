package android.kotlin.foodclub.utils.composables.products

import android.kotlin.foodclub.config.ui.containerColor
import android.kotlin.foodclub.config.ui.defaultSearchBarColors
import android.kotlin.foodclub.domain.enums.QuantityUnit
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.Product
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color

data class ProductState(
    val addedProducts: List<Ingredient>,
    val allowExpiryDate: Boolean,
    val currentAction: ProductAction,
    val editedIngredient: Ingredient
) {
    companion object {
        fun ProductState.default(): ProductState {
            return ProductState(
                addedProducts = listOf(),
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
                )
            )
        }
    }
}
