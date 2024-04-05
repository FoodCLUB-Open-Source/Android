package android.kotlin.foodclub.viewModels.home.discover

import android.content.Context
import android.kotlin.foodclub.domain.models.products.Ingredient
import androidx.camera.core.ImageCapture

interface DiscoverEvents {
    fun getPostsByWorld(worldCategory: Long)
    fun getPostsByUserId()
    fun scan(imageCapture: ImageCapture, context: Context)
    fun addScanListToUserIngredients(ingredients: List<Ingredient>)
    fun onResetSearchData()
}