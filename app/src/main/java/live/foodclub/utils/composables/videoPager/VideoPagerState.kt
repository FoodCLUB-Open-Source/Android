package live.foodclub.utils.composables.videoPager

import live.foodclub.domain.models.recipes.Recipe

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
