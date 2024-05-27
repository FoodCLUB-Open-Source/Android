package live.foodclub.domain.models.auth


data class FirebaseUserModel(
    val userID: Int = -1,
    val username: String = "",
    val isOnline: Boolean = false,
    var fcmToken: String = "",
    val fullName: String = "",
    val profileImageUrl: String = "",
    val accessToken: String = "",
    val idToken: String = "",
    val refreshToken: String = "",
    val email: String = "",
    val password: String = ""
) {
    companion object {
        fun default(): FirebaseUserModel = FirebaseUserModel()
    }
}