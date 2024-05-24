package live.foodclub.views.home.discover

import live.foodclub.R
import live.foodclub.domain.enums.QuantityUnit
import live.foodclub.domain.models.home.VideoModel
import live.foodclub.domain.models.products.Ingredient
import live.foodclub.domain.models.products.MyBasketCache
import live.foodclub.domain.models.products.Product
import androidx.compose.ui.graphics.ImageBitmap
import androidx.media3.exoplayer.ExoPlayer
import live.foodclub.utils.composables.videoPager.VideoPagerState

data class DiscoverState(
    var username: String,
    var ingredientSearchText : String,
    val postList: List<VideoModel>,
    val error : String,
    val capturedImage : ImageBitmap?,
    val ingredientToEdit : Ingredient?,
    val videoPagerState: VideoPagerState,
    val scanResultItemList : List<Ingredient>,
    val myBasketCache: MyBasketCache?,
    val exoPlayer: ExoPlayer,
) {
    companion object {
        fun default(exoPlayer: ExoPlayer) = DiscoverState(
            username = "",
            ingredientSearchText = "",
            postList = emptyList(),
            error = "",
            capturedImage = null,
            ingredientToEdit = null,
            videoPagerState = VideoPagerState.default(),
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
            exoPlayer = exoPlayer
        )
    }
}
