package android.kotlin.foodclub.network.retrofit.responses.stories

import com.google.gson.annotations.SerializedName

data class RetrievePostImageStoryResponse(
    @SerializedName("Status")
    val status: String
)