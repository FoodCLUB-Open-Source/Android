package android.kotlin.foodclub.localdatasource.room.entity

import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.home.VideoStats
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user_posts")
data class OfflineUserPostsModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val videoId: Long,
    val title: String? = null,
    val description: String?,
    val createdAt: String? = null,
    val videoLink: String? = null,
    val thumbnailLink: String? = null,
    val totalLikes: Long? = null,
    val totalViews: Long? = null
)

fun OfflineUserPostsModel.toVideoModel(): VideoModel {
    return VideoModel(
        videoId = videoId,
        authorDetails = "Marc",
        videoStats = VideoStats(
            totalLikes ?: 0,
            0L,
            0L,
            0L,
            totalViews ?: 0
        ),
        videoLink = videoLink ?: "",
        description = description ?: "",
        thumbnailLink = thumbnailLink ?: ""
    )
}