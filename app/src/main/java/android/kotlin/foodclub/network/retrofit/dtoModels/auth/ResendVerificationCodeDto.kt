package android.kotlin.foodclub.network.retrofit.dtoModels.auth

import androidx.annotation.Keep

@Keep
data class ResendVerificationCodeDto(
    val username: String
)
