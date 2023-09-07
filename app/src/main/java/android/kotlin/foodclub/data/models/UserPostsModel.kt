package android.kotlin.foodclub.data.models

import com.google.gson.annotations.SerializedName

data class UserPostsModel(
    val id: Int,
    val title: String,
    val description: String,

    @SerializedName("created_at")
    val dateCreated: String,

    @SerializedName("video_url")
    val videoUrl: String,

    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,

    @SerializedName("total_likes")
    val totalLikes: Int,

    @SerializedName("total_views")
    val totalViews: Int
)
