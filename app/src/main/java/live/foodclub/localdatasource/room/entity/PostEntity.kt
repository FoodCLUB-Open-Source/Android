package live.foodclub.localdatasource.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
    return HomePostEntity(postId = postId)
}

fun PostEntity.toProfilePosts(): ProfilePostEntity {
    return ProfilePostEntity(postId = postId)
}

fun PostEntity.toBookmarkEntity(): BookmarkPostEntity {
    return BookmarkPostEntity(postId = postId)
}

fun PostEntity.toDiscoverPostEntity(): DiscoverPostEntity {
    return DiscoverPostEntity(postId = postId)
}