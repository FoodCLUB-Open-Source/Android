package live.foodclub.network.retrofit.dtoModels.auth

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VerificationCodeDto(
    val username: String,
    val password: String,
    @SerializedName("verification_code")
    val code: String
)
