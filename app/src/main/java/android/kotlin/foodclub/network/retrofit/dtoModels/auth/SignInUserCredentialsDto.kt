package android.kotlin.foodclub.network.retrofit.dtoModels.auth

import androidx.annotation.Keep

@Keep
data class SignInUserCredentialsDto(
    val username: String,
    val password: String
)
