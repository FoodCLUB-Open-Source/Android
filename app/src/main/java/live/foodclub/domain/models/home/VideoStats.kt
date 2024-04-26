package live.foodclub.domain.models.home

import java.text.DecimalFormat

data class VideoStats(
    val like: Long = 0L,
    val comment: Long = 0L,
    val share: Long = 0L,
    val favourite: Long = 0L,
    val views: Long = (like.plus(500)..like.plus(100000)).random()
) {
    private val decimalFormat = DecimalFormat("#.#")
    private fun Long.formatCount(): String {
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

    val displayLike = like.formatCount()
    val displayComment = comment.formatCount()
    val displayShare = share.formatCount()
    val displayFavourite = favourite.formatCount()
    val displayViews = views.formatCount()
}
