package live.foodclub.network.retrofit.dtoModels.auth

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ForgotChangePasswordDto(
    val username: String,

    @SerializedName("verification_code")
    val code: String,

    @SerializedName("new_password")
    val newPassword: String,
)
