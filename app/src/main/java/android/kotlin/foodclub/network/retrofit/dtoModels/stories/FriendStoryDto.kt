package android.kotlin.foodclub.network.retrofit.dtoModels.stories

import com.google.gson.annotations.SerializedName

data class FriendStoryDto(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("profile_picture")
    val profilePicture: String,
    val username: String,
    val stories: List<StoryDetailDto>
)
