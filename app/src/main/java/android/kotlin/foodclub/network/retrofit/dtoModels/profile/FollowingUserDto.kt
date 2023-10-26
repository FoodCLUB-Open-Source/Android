package android.kotlin.foodclub.network.retrofit.dtoModels.profile

import com.google.gson.annotations.SerializedName

data class FollowingUserDto(
    @SerializedName("user_following_id")
    val userId: Int,

    val username: String,

    @SerializedName("profile_picture")
    val profilePictureUrl: String?
)