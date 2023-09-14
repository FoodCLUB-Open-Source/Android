package android.kotlin.foodclub.api.responses

data class LoginResponse(
    val user: LoginUserData
)
data class LoginUserData(
    val id: Int,
    val username: String,
    val profile_picture: String
)
