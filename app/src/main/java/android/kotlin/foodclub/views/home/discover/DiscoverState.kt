package android.kotlin.foodclub.views.home.discover

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.enums.QuantityUnit
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.MyBasketCache
import android.kotlin.foodclub.domain.models.products.ProductsData
import androidx.compose.ui.graphics.ImageBitmap

data class DiscoverState(
    val mainSearchText : String,
    var ingredientSearchText : String,
    val userIngredients : List<Ingredient>,
    val searchResults : List<Ingredient>,
    val postList: List<VideoModel>,
    val myFridgePosts: List<VideoModel>,
    val sessionUserId: String,
    val sessionUserUsername: String,
    val productsData: ProductsData,
    val error : String,
    val capturedImage : ImageBitmap?,
    val ingredientToEdit : Ingredient?,
    val scanResultItemList : List<Ingredient>,
    val myBasketCache: MyBasketCache?,
) {
    companion object {
        fun default() = DiscoverState(
            mainSearchText = "",
            ingredientSearchText = "",
            userIngredients = emptyList(),
            searchResults = emptyList(),
            postList = emptyList(),
            myFridgePosts = emptyList(),
            sessionUserId = "",
            sessionUserUsername = "",
            productsData = ProductsData(
                searchText = "",
                nextUrl = "",
                productsList = emptyList(),
            ),
            error = "",
            capturedImage = null,
            ingredientToEdit = null,
            scanResultItemList = listOf( // TODO Dummy Data, needs removing
                Ingredient(
                    id = "1",  // Make sure to copy other properties if needed
                    quantity = 100,
                    unit = QuantityUnit.GRAMS,
                    type = "Capsicum",
                    expirationDate = "Edit",
                    imageUrl = R.drawable.capsicum
                ),
                Ingredient(
                    id = "2",  // Make sure to copy other properties if needed
                    quantity = 10,
                    unit = QuantityUnit.GRAMS,
                    type = "Tomato Soup",
                    expirationDate = "Edit",
                    imageUrl = R.drawable.tomato_ingredient
                ),
                Ingredient(
                    id = "3",  // Make sure to copy other properties if needed
                    quantity = 1,
                    unit = QuantityUnit.GRAMS,
                    type = "Lemon",
                    expirationDate = "Edit",
                    imageUrl = R.drawable.lemon
                ),
                Ingredient(
                    id = "4",  // Make sure to copy other properties if needed
                    quantity = 1000,
                    unit = QuantityUnit.GRAMS,
                    type = "Egg",
                    expirationDate = "Edit",
                    imageUrl = R.drawable.egg
                ),


                // Add more items as needed
            ),
            myBasketCache = null,

        )
    }
}
