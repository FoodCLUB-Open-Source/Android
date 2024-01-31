package android.kotlin.foodclub.views.home.home

import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.domain.models.snaps.MemoriesModel

data class HomeState(
    val videoList : List<VideoModel>,
    val memories : List<MemoriesModel>,
    val storyList : List<VideoModel>,
    val recipe: Recipe?,
    val error : String,
    val showMemories: Boolean,
    val showMemoriesReel: Boolean,
) {
    companion object {
        fun default() = HomeState(
            videoList = emptyList(),
            memories = emptyList(),
            storyList = emptyList(),
            recipe = null,
            error = "",
            showMemories = false,
            showMemoriesReel = true
        )
    }
}
