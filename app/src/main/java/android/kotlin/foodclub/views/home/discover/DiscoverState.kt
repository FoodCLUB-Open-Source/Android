package android.kotlin.foodclub.views.home.discover

import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.kotlin.foodclub.domain.models.products.ProductsData
import android.kotlin.foodclub.domain.models.profile.UserPosts

data class DiscoverState(
    val mainSearchText : String,
    val ingredientSearchText : String,
    val userIngredients : List<Ingredient>,
    val searchResults : List<Ingredient>,
    val postList: List<VideoModel>,
    val myFridgePosts: List<UserPosts>,
    val sessionUserId: String,
    val sessionUserUsername: String,
    val productsData: ProductsData,
    val error : String
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
            error = ""
        )
    }
}
