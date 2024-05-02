package android.kotlin.foodclub.localdatasource.room.entity

import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.home.VideoStats
import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity("profile_posts", foreignKeys = [
    ForeignKey(
        entity = ProfileEntity::class,
        parentColumns = ["userId"],
        childColumns = ["authorId"],
        onDelete = CASCADE
    )]
)
data class ProfilePostsEntity(
    @PrimaryKey(autoGenerate = false)
    val videoId: Long,
    val authorId: Long,
    val title: String? = null,
    val description: String?,
    val createdAt: String? = null,
    val videoLink: String? = null,
    val thumbnailLink: String? = null,
    val totalLikes: Long? = null,
    val totalViews: Long? = null,
    val isLiked: Boolean? = null,
    val isBookmarked: Boolean? = null
)

fun ProfilePostsEntity.toVideoModel(): VideoModel {
    return VideoModel(
        videoId = videoId,
        authorDetails = SimpleUserModel(userId = 0,username = "", profilePictureUrl = null),
        videoStats = VideoStats(
            totalLikes ?: 0,
            0L,
            0L,
            0L,
            totalViews ?: 0
        ),
        videoLink = videoLink ?: "",
        description = description ?: "",
        thumbnailLink = thumbnailLink ?: "",
        isLiked = isLiked ?: false,
        isBookmarked = isBookmarked ?: false
    )
}