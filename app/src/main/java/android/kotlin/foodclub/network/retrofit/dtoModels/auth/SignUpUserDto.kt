package android.kotlin.foodclub.network.retrofit.dtoModels.auth

import com.google.gson.annotations.SerializedName

data class SignUpUserDto(
    val username: String,
    val email: String,
    val password: String,

    @SerializedName("fullname")
    val name: String
)