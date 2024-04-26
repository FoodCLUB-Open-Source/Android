package live.foodclub.domain.models.auth

data class SignInUser(
    val id: Int,
    val username: String,
    val profileImageUrl: String?,
    val fullName: String?,
    val accessToken: String,
    val idToken: String,
    val refreshToken: String
)
