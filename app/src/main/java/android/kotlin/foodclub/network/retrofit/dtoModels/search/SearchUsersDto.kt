package android.kotlin.foodclub.network.retrofit.dtoModels.search

import com.google.gson.annotations.SerializedName

data class SearchUsersDto (
    @SerializedName("id")
    val userId: Int,

    val username: String,

    @SerializedName("full_name")
    val userFullname: String?,

    @SerializedName("profile_picture")
    val profilePictureUrl: String?
)