package android.kotlin.foodclub.network.retrofit.dtoModels.auth

import com.google.gson.annotations.SerializedName

data class SignInUserDto(
    val id: Int,
    val username: String,

    @SerializedName("profile_picture")
    val profileImageUrl: String
)
