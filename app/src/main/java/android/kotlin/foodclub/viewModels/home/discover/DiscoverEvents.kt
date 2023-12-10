package android.kotlin.foodclub.viewModels.home.discover

import android.kotlin.foodclub.domain.models.products.Ingredient

interface DiscoverEvents {
    fun getPostsByWorld(worldCategory: Long)
    fun getPostsByUserId()
    fun myFridgePosts()
    fun onSubSearchTextChange(text: String)
    fun updateIngredient(ingredient: Ingredient)
    fun deleteIngredientFromList(ingredient: Ingredient)
    fun addToUserIngredients(ingredient: Ingredient)
    fun getPostData(postId: Long)

}