package android.kotlin.foodclub.network.retrofit.dtoModels.stories

import com.google.gson.annotations.SerializedName

data class StoryDetailDto(
    @SerializedName("story_id")
    val storyId: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("video_url")
    val videoUrl: String
)
