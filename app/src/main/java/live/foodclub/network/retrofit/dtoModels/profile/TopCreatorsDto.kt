package live.foodclub.network.retrofit.dtoModels.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TopCreatorsDto(
    val id: Int,
    val username: String,

    @SerializedName("profile_picture")
    val profilePictureUrl: String?
)
