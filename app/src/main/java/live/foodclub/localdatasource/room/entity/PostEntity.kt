package live.foodclub.localdatasource.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import live.foodclub.domain.models.home.VideoModel
import live.foodclub.domain.models.home.VideoStats
import live.foodclub.domain.models.home.VideoUserInteraction
import live.foodclub.domain.models.profile.SimpleUserModel

@Entity(
    tableName = "posts",
    foreignKeys = [
        ForeignKey(
            entity = ProfileEntity::class,
            parentColumns = ["userId"],
            childColumns = ["authorId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PostEntity(
    @PrimaryKey(autoGenerate = false)
    val postId: Long,
    val authorId: Long,
    val recipeId: Long,
    val title: String? = null,
    val description: String?,
    val createdAt: String? = null,
    val videoLink: String? = null,
    val thumbnailLink: String? = null,
    val totalLikes: Long? = null,
    val totalViews: Long? = null,
    val isLiked: Boolean = false,
    val isBookmarked: Boolean = false
)

fun PostEntity.toHomePosts(): HomePostEntity {
    return HomePostEntity(id = 0, postId = postId)
}

fun PostEntity.toVideoModel(): VideoModel {
    return VideoModel(
        videoId = postId,
        authorDetails = SimpleUserModel(userId = 0,username = "", profilePictureUrl = null),
        title = title,
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
        currentViewerInteraction = VideoUserInteraction(
            isBookmarked = isBookmarked ?: false,
            isLiked = isLiked ?: false
        )
    )
}