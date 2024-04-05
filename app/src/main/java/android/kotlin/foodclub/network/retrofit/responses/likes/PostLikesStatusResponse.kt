package android.kotlin.foodclub.network.retrofit.responses.likes

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PostLikesStatusResponse(
    @SerializedName("Status")
    val status: String
)