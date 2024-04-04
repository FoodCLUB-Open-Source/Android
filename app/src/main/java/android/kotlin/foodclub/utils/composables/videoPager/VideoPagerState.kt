package android.kotlin.foodclub.utils.composables.videoPager

import android.kotlin.foodclub.domain.models.recipes.Recipe

data class VideoPagerState(
    val browsingUserId: Long,
    val isHomeView: Boolean = false,
    val recipe: Recipe?
) {
    companion object {
        fun default() = VideoPagerState(
            browsingUserId = 0,
            recipe = null
        )
    }
}
