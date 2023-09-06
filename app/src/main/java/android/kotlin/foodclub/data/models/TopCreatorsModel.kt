package android.kotlin.foodclub.data.models

import com.google.gson.annotations.SerializedName

data class TopCreatorsModel(
    val id: Int,
    val username: String,

    @SerializedName("profile_picture")
    val profilePictureUrl: String?
)
