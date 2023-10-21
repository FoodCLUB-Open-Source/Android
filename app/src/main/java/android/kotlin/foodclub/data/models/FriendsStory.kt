package android.kotlin.foodclub.data.models

import com.google.gson.annotations.SerializedName

data class FriendsStory(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("profile_picture")
    val profilePicture: String,
    val username: String,
    val stories: List<FriendsStoryDetail>
)
