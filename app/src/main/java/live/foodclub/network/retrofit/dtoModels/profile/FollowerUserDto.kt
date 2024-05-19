package live.foodclub.network.retrofit.dtoModels.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FollowerUserDto(
    @SerializedName("user_id")
    val userId: Long,

    val username: String,

    @SerializedName("profile_picture")
    val profilePictureUrl: String?
)
