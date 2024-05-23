package live.foodclub.domain.models.auth

import live.foodclub.domain.models.profile.SimpleUserModel


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
    fun mapToSimpleUserModel(): SimpleUserModel {
        return SimpleUserModel(
            userId = userID,
            userFullname = username,
            profilePictureUrl = profileImageUrl,
            username = username
        )
    }

}