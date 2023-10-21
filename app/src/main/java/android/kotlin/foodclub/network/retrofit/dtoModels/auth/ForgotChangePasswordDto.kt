package android.kotlin.foodclub.network.retrofit.dtoModels.auth

import com.google.gson.annotations.SerializedName

data class ForgotChangePasswordDto(
    val username: String,

    @SerializedName("verification_code")
    val code: String,

    @SerializedName("new_password")
    val newPassword: String,
)
