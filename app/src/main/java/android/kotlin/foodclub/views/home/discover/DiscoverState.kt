package android.kotlin.foodclub.views.home.discover

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.enums.QuantityUnit
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.MyBasketCache
import android.kotlin.foodclub.domain.models.products.Product
import androidx.compose.ui.graphics.ImageBitmap

data class DiscoverState(
    var username: String,
    var ingredientSearchText : String,
    val postList: List<VideoModel>,
    val error : String,
    val capturedImage : ImageBitmap?,
    val ingredientToEdit : Ingredient?,
    val scanResultItemList : List<Ingredient>,
    val myBasketCache: MyBasketCache?,
) {
    companion object {
        fun default() = DiscoverState(
            username = "",
            ingredientSearchText = "",
            postList = emptyList(),
            error = "",
            capturedImage = null,
            ingredientToEdit = null,
            scanResultItemList = listOf( // TODO Dummy Data, needs removing
                Ingredient(
                    product = Product(
                        foodId = "1",
                        label = "Capsicum",
                        image = R.drawable.capsicum.toString(),
                        units = QuantityUnit.entries
                    ),
                    quantity = 100,
                    unit = QuantityUnit.GRAM,
                    expirationDate = "Edit"
                ),
                Ingredient(
                    product = Product(
                        foodId = "2",
                        label = "Tomato Soup",
                        image = R.drawable.tomato_ingredient.toString(),
                        units = QuantityUnit.entries
                    ),
                    quantity = 10,
                    unit = QuantityUnit.GRAM,
                    expirationDate = "Edit"
                ),
                Ingredient(
                    product = Product(
                        foodId = "3",
                        label = "Lemon",
                        image = R.drawable.lemon.toString(),
                        units = QuantityUnit.entries
                    ),
                    quantity = 1,
                    unit = QuantityUnit.GRAM,
                    expirationDate = "Edit",
                ),
                Ingredient(
                    product = Product(
                        foodId = "4",
                        label = "Egg",
                        image = R.drawable.egg.toString(),
                        units = QuantityUnit.entries
                    ),
                    quantity = 1000,
                    unit = QuantityUnit.GRAM,
                    expirationDate = "Edit",
                ),


                // Add more items as needed
            ),
            myBasketCache = null,

        )
    }
}
