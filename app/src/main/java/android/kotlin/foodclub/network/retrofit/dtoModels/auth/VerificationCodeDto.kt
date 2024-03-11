package android.kotlin.foodclub.network.retrofit.dtoModels.auth

import com.google.gson.annotations.SerializedName

data class VerificationCodeDto(
    val username: String,
    val password: String,
    @SerializedName("verification_code")
    val code: String
)
