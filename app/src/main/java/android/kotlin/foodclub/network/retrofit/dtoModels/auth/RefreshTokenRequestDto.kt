package android.kotlin.foodclub.network.retrofit.dtoModels.auth

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequestDto(
    val username: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
