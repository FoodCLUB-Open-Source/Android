package android.kotlin.foodclub.network.retrofit.dtoModels.auth

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SignInUserDto(
    val id: Int,
    val username: String,

    @SerializedName("profile_picture")
    val profileImageUrl: String? = null,

    @SerializedName("full_name")
    val fullName: String? = null
)
