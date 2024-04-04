package android.kotlin.foodclub.network.retrofit.dtoModels.search

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SearchPostsDto (
    @SerializedName("id")
    val postId: Int,

    @SerializedName("user_id")
    val userId: Int,

    val title: String,

    val description: String,

    @SerializedName("video_name")
    val videoUrl: String,

    @SerializedName("thumbnail_name")
    val thumbnailUrl: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    val contentCreator: SearchUsersDto
)