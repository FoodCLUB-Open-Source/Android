package live.foodclub.localdatasource.room.relationships

import androidx.room.Embedded
import androidx.room.Relation
import live.foodclub.domain.models.home.VideoModel
import live.foodclub.domain.models.home.VideoStats
import live.foodclub.domain.models.home.VideoUserInteraction
import live.foodclub.localdatasource.room.entity.PostEntity
import live.foodclub.localdatasource.room.entity.ProfileEntity
import live.foodclub.localdatasource.room.entity.toSimpleUserModel

data class PostWithUser (
    @Embedded val postEntity: PostEntity,
    @Relation(
        parentColumn = "authorId",
        entityColumn = "userId"
    )
    val user: ProfileEntity
)

fun PostWithUser.toVideoModel(): VideoModel {
    return VideoModel(
        videoId = postEntity.postId,
        authorDetails = user.toSimpleUserModel(),
        title = postEntity.title,
        videoStats = VideoStats(
            postEntity.totalLikes ?: 0,
            0L,
            0L,
            0L,
            postEntity.totalViews ?: 0
        ),
        videoLink = postEntity.videoLink ?: "",
        description = postEntity.description ?: "",
        thumbnailLink = postEntity.thumbnailLink ?: "",
        currentViewerInteraction = VideoUserInteraction(
            isBookmarked = postEntity.isBookmarked,
            isLiked = postEntity.isLiked
        )
    )
}