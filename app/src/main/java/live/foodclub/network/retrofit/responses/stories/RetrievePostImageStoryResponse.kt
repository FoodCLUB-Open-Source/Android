package live.foodclub.network.retrofit.responses.stories

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RetrievePostImageStoryResponse(
    @SerializedName("Status")
    val status: String
)