package android.kotlin.foodclub.network.retrofit.responses.likes

import com.google.gson.annotations.SerializedName

data class PostLikesStatusResponse(
    @SerializedName("Status")
    val status: String
)