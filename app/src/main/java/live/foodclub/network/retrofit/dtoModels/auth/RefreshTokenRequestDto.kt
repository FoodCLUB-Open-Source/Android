package live.foodclub.network.retrofit.dtoModels.auth

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RefreshTokenRequestDto(
    val username: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
