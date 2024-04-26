package live.foodclub.views.home.home.feed

import live.foodclub.domain.enums.Reactions
import live.foodclub.domain.models.home.VideoModel
import live.foodclub.domain.models.recipes.Recipe
import live.foodclub.domain.models.snaps.MemoriesModel

data class HomeState(
    val videoList : List<VideoModel>,
    val memories : List<MemoriesModel>,
    val storyList : List<VideoModel>,
    val recipe: Recipe?,
    val error : String,
    var showMemories: Boolean,
    val showMemoriesReel: Boolean,
    val selectedReaction: Reactions
) {
    companion object {
        fun default() = HomeState(
            videoList = emptyList(),
            memories = emptyList(),
            storyList = emptyList(),
            recipe = null,
            error = "",
            showMemories = false,
            showMemoriesReel = true,
            selectedReaction = Reactions.ALL
        )
    }
}
