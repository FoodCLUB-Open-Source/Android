package live.foodclub.network.retrofit.dtoModels.posts

import live.foodclub.network.retrofit.dtoModels.profile.UserInfoDto
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import live.foodclub.localdatasource.room.entity.PostEntity
import live.foodclub.localdatasource.room.relationships.PostWithUser
import live.foodclub.network.retrofit.dtoModels.profile.toProfileEntity

@Keep
data class PostModelDto(
    val id: Long,
    val title: String,
    val description: String?,

    @SerializedName("user_id")
    val userId: Long,
    val user: UserInfoDto,

    @SerializedName("recipe_id")
    val recipeID: Long,

    @SerializedName("created_at")
    val createdAt:String,
    @SerializedName("updated_at")
    val updatedAt:String,

    @SerializedName("post_id")
    val postId:String,

    @SerializedName("category_name")
    val categoryName: String?,

    @SerializedName("video_url")
    val videoUrl: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,

    @SerializedName("total_likes")
    val likes: Long?,
    @SerializedName("total_views")
    val views: Long?,

    val isLiked: Boolean,
    val isViewed: Boolean,
    val isBookmarked: Boolean = false
)

fun PostModelDto.toPostWithUser(): PostWithUser {
    return PostWithUser(
        postEntity = PostEntity(
            postId = id,
            authorId = user.id,
            recipeId = recipeID,
            title = title,
            description = description,
            createdAt = createdAt,
            videoLink = videoUrl,
            thumbnailLink = thumbnailUrl,
            totalLikes = likes,
            totalViews = views,
            isLiked = isLiked,
            isBookmarked = isBookmarked
        ),
        user = user.toProfileEntity()
    )
}