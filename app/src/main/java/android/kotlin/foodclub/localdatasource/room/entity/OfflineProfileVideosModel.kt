package android.kotlin.foodclub.localdatasource.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("profile_videos_data")
data class OfflineProfileVideosModel(
    @PrimaryKey
    val videoId: Long,
    val isVideoPost: Boolean,
    val authorDetails: String? = null,
    val videoLink: String? = null,
    val thumbnailLink: String? = null,
    val isLiked: Boolean? = null,
    val isBookmarked: Boolean? = null,
    val description: String? = null,
    val createdAt: String? = null,
    val like: Long = 0L,
    val comment: Long = 0L,
    val share: Long = 0L,
    val favourite: Long = 0L,
    val views: Long = 0L
)