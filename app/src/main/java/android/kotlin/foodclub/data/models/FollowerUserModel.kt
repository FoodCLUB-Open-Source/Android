package android.kotlin.foodclub.data.models

import com.google.gson.annotations.SerializedName

data class FollowerUserModel(
    @SerializedName("user_id")
    val userId: Int,

    val username: String,

    @SerializedName("profile_picture")
    val profilePictureUrl: String?
)
