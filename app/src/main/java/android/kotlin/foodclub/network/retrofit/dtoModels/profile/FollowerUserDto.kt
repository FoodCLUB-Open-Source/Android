package android.kotlin.foodclub.network.retrofit.dtoModels.profile

import com.google.gson.annotations.SerializedName

data class FollowerUserDto(
    @SerializedName("user_id")
    val userId: Int,

    val username: String,

    @SerializedName("profile_picture")
    val profilePictureUrl: String?
)