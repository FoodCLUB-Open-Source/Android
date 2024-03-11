package android.kotlin.foodclub.network.retrofit.responses.auth

import android.kotlin.foodclub.network.retrofit.dtoModels.auth.SignInUserDto
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val user: SignInUserDto,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("id_token")
    val idToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
