package live.foodclub.presentation.ui.home.discover

import android.content.Context
import live.foodclub.domain.models.products.Ingredient
import androidx.camera.core.ImageCapture
import live.foodclub.domain.enums.Category
import live.foodclub.utils.composables.videoPager.VideoPagerEvents

interface DiscoverEvents: VideoPagerEvents {
    fun getPostsByUserId()
    fun scan(imageCapture: ImageCapture, context: Context)
    fun changeCategory(category: Category)
    fun addScanListToUserIngredients(ingredients: List<Ingredient>)
    fun onResetSearchData()
}