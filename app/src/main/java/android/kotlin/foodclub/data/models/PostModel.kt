package android.kotlin.foodclub.data.models

import com.google.gson.annotations.SerializedName

data class PostModel(
    val id: Long,
    val title: String,
    val description: String,
    val username: String?,

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
