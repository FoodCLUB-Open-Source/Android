package live.foodclub.presentation.ui.home.home

import androidx.media3.exoplayer.ExoPlayer
import live.foodclub.domain.enums.Reactions
import live.foodclub.domain.models.home.VideoModel
import live.foodclub.domain.models.snaps.MemoriesModel
import live.foodclub.utils.composables.videoPager.VideoPagerState

data class HomeState(
    val videoList : List<VideoModel>,
    val memories : List<MemoriesModel>,
    val storyList : List<VideoModel>,
    val error : String,
    val videoPagerState: VideoPagerState,
    var showMemories: Boolean,
    val showMemoriesReel: Boolean,
    val selectedReaction: Reactions,
    val exoPlayer: ExoPlayer
) {
    companion object {
        fun default(exoPlayer: ExoPlayer) = HomeState(
            videoList = emptyList(),
            memories = emptyList(),
            storyList = emptyList(),
            error = "",
            videoPagerState = VideoPagerState.default().copy(isHomeView = true),
            showMemories = false,
            showMemoriesReel = true,
            selectedReaction = Reactions.ALL,
            exoPlayer = exoPlayer
        )
    }
}
