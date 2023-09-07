package android.kotlin.foodclub.data.models

import com.google.gson.annotations.SerializedName

data class FollowingUserModel(
    @SerializedName("user_following_id")
    val userId: Int,

    val username: String,

    @SerializedName("profile_picture")
    val profilePictureUrl: String
)
