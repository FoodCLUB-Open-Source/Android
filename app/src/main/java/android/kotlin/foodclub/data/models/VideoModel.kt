package android.kotlin.foodclub.data.models

import java.text.DecimalFormat

val decimalFormat = DecimalFormat("#.#")
fun Long.formattedCount(): String {
    return if (this < 10000) {
        this.toString()
    } else if (this < 1000000) {
        "${decimalFormat.format(this.div(1000))}K"
    } else if (this < 1000000000) {
        "${decimalFormat.format(this.div(1000000))}M"
    } else {
        "${decimalFormat.format(this.div(1000000000))}B"
    }
}

fun randomUploadDate(): String = "${(1..24).random()}h"

data class VideoModel(
    val videoId: String,
    val authorDetails: String,
    val videoStats: VideoStats,
    val videoLink: String,
    val currentViewerInteraction: ViewerInteraction = ViewerInteraction(),
    val description: String,
    val createdAt: String = randomUploadDate(),
) {
    data class VideoStats(
        var like: Long,
        var comment: Long,
        var share: Long,
        var favourite: Long,
        var views: Long = (like.plus(500)..like.plus(100000)).random()
    ) {
        var formattedLikeCount: String = ""
        var formattedCommentCount: String = ""
        var formattedShareCount: String = ""
        var formattedFavouriteCount: String = ""
        var formattedViewsCount: String = ""

        init {
            formattedLikeCount = like.formattedCount()
            formattedCommentCount = comment.formattedCount()
            formattedShareCount = share.formattedCount()
            formattedFavouriteCount = favourite.formattedCount()
            formattedViewsCount = views.formattedCount()
        }
    }

    data class ViewerInteraction(
        var isLikedByYou: Boolean = false,
        var isAddedToFavourite: Boolean = false
    )
}