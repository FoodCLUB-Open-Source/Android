package android.kotlin.foodclub.network.retrofit.responses.posts

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DeletePostResponse(
    @SerializedName("Status")
    val status: String
)
