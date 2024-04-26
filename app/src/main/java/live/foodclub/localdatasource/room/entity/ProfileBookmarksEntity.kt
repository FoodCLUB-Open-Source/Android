package live.foodclub.localdatasource.room.entity

import live.foodclub.domain.models.home.VideoModel
import live.foodclub.domain.models.home.VideoStats
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity("profile_bookmarked_posts", foreignKeys = [
    ForeignKey(
        entity = ProfileEntity::class,
        parentColumns = ["userId"],
        childColumns = ["bookmarkedBy"],
        onDelete = CASCADE
    )]
)
data class ProfileBookmarksEntity(
    @PrimaryKey(autoGenerate = false)
    val videoId: Long,
    val bookmarkedBy: Long,
    val title: String? = null,
    val description: String?,
    val createdAt: String? = null,
    val videoLink: String? = null,
    val thumbnailLink: String? = null,
    val totalLikes: Long? = null,
    val totalViews: Long? = null
)

fun ProfileBookmarksEntity.toVideoModel(): VideoModel {
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
