package live.foodclub.network.retrofit.dtoModels.auth

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SignUpUserDto(
    val username: String,
    val email: String,
    val password: String,

    @SerializedName("full_name")
    val name: String
)