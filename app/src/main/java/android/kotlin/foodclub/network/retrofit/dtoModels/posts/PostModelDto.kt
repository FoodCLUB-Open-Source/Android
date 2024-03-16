package android.kotlin.foodclub.network.retrofit.dtoModels.posts

import android.kotlin.foodclub.localdatasource.room.entity.ProfileBookmarksEntity
import android.kotlin.foodclub.localdatasource.room.entity.ProfilePostsEntity
import com.google.gson.annotations.SerializedName

data class PostModelDto(
    val id: Long,
    val title: String,
    val description: String,
    val username: String?,

    @SerializedName("created_at")
    val createdAt:String,
    @SerializedName("updated_at")
    val updatedAt:String,

    @SerializedName("post_id")
    val postId:String,

    @SerializedName("category_name")
    val categoryName: String?,

    @SerializedName("profile_picture")
    val profilePictureUrl: String?,
    @SerializedName("video_url")
    val videoUrl: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,

    @SerializedName("total_likes")
    val likes: Long?,
    @SerializedName("total_views")
    val views: Long?
)

fun PostModelDto.toProfilePostsEntity(authorId: Long): ProfilePostsEntity {
    return ProfilePostsEntity(
        authorId = authorId,
        videoId = id,
        title = title,
        description = description,
        createdAt = createdAt,
        videoLink = videoUrl,
        thumbnailLink = thumbnailUrl,
        totalLikes = likes,
        totalViews = views
    )
}

fun PostModelDto.toProfileBookmarksEntity(bookmarkedBy: Long): ProfileBookmarksEntity {
    return ProfileBookmarksEntity(
        bookmarkedBy = bookmarkedBy,
        videoId = id,
        title = title,
        description = description,
        createdAt = createdAt,
        videoLink = videoUrl,
        thumbnailLink = thumbnailUrl,
        totalLikes = likes,
        totalViews = views
    )
}
