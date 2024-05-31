package live.foodclub.domain.models.home

import live.foodclub.domain.models.profile.SimpleUserModel

data class VideoModel(
    val videoId: Long,
    val authorDetails: SimpleUserModel,
    val videoStats: VideoStats,
    val videoLink: String,
    val recipeId: Long = 502,
    val title: String?,
    val currentViewerInteraction: VideoUserInteraction = VideoUserInteraction(),
    val description: String,
    val createdAt: String = "${(1..24).random()}h",
    val thumbnailLink: String = ""
)