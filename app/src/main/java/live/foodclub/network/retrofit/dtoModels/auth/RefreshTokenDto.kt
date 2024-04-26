package live.foodclub.network.retrofit.dtoModels.auth

import com.google.gson.annotations.SerializedName

data class RefreshTokenDto(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("id_token")
    val idToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
