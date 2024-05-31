package live.foodclub.domain.models.auth

data class SignUpUser(
    val username: String,
    val email: String,
    val password: String,
    val name: String,
    val accessToken: String? = null,
    val idToken: String? = null,
    val refreshToken: String? = null,
    val isOnline: Boolean? = null,
    val fcmToken: String? = null,
    val conversationIds: List<String>? = null,
    val profileImageUrl: String? = null,
    val id: Int? = null,
)