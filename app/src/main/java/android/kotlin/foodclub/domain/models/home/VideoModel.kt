package android.kotlin.foodclub.domain.models.home

data class VideoModel(
    val videoId: Long,
    val authorDetails: String,
    val videoStats: VideoStats,
    val videoLink: String,
    val currentViewerInteraction: VideoUserInteraction = VideoUserInteraction(),
    val description: String,
    val createdAt: String = "${(1..24).random()}h",
    val thumbnailLink: String = ""
)