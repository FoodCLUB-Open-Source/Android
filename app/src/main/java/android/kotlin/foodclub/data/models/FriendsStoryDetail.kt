package android.kotlin.foodclub.data.models

import com.google.gson.annotations.SerializedName

data class FriendsStoryDetail(
    @SerializedName("story_id")
    val storyId: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("video_url")
    val videoUrl: String
)
