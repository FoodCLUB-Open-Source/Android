package android.kotlin.foodclub.api.responses

import com.google.gson.annotations.SerializedName

data class DeletePostResponse(
    @SerializedName("Status")
    val status: String
)
