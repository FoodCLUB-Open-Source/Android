package android.kotlin.foodclub.network.retrofit.responses.auth

import android.kotlin.foodclub.network.retrofit.dtoModels.auth.SignInUserDto

data class LoginResponse(
    val user: SignInUserDto
)
