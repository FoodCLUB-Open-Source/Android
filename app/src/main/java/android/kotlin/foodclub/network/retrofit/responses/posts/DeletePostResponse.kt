package android.kotlin.foodclub.network.retrofit.responses.posts

import com.google.gson.annotations.SerializedName

data class DeletePostResponse(
    @SerializedName("Status")
    val status: String
)
