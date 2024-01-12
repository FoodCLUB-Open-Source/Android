package android.kotlin.foodclub.viewModels.home.discover

import android.content.Context
import android.kotlin.foodclub.domain.models.products.Ingredient
import androidx.camera.core.ImageCapture

interface DiscoverEvents {
    fun getPostsByWorld(worldCategory: Long)
    fun getPostsByUserId()
    fun myFridgePosts()
    fun onAddIngredientsSearchTextChange(text: String)
    fun updateIngredient(ingredient: Ingredient)
    fun deleteIngredientFromList(ingredient: Ingredient)
    fun addToUserIngredients(ingredient: Ingredient)
    fun getPostData(postId: Long)
    fun scan(imageCapture: ImageCapture, context: Context)
    fun addScanListToUserIngredients(ingredients: List<Ingredient>)
    fun onSearchIngredientsList(text: String)
    fun onDeleteIngredient(ingredient: Ingredient)
}