package android.kotlin.foodclub.network.retrofit.dtoModels.profile

import com.google.gson.annotations.SerializedName

data class TopCreatorsDto(
    val id: Int,
    val username: String,

    @SerializedName("profile_picture")
    val profilePictureUrl: String?
)
