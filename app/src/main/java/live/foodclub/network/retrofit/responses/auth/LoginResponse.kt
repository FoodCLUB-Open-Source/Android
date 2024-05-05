package live.foodclub.network.retrofit.responses.auth

import live.foodclub.network.retrofit.dtoModels.auth.SignInUserDto
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginResponse(
    val user: SignInUserDto,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("id_token")
    val idToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
