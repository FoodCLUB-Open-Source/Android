package android.kotlin.foodclub.network.retrofit.dtoModels.profile

import com.google.gson.annotations.SerializedName

data class UserInfoDto(
    val id: Long,
    val username: String,
    @SerializedName("profile_picture")
    val profilePictureUrl: String? = null,
    @SerializedName("full_name")
    val fullName: String? = null
)
