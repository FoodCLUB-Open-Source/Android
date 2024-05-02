package android.kotlin.foodclub.domain.models.home

import android.kotlin.foodclub.domain.models.profile.SimpleUserModel

data class VideoModel(
    val videoId: Long,
    val authorDetails: SimpleUserModel,
    val videoStats: VideoStats,
    val videoLink: String,
    val currentViewerInteraction: VideoUserInteraction = VideoUserInteraction(),
    val description: String,
    val createdAt: String = "${(1..24).random()}h",
    val thumbnailLink: String = "",
    val isLiked: Boolean,
    val isBookmarked: Boolean
)