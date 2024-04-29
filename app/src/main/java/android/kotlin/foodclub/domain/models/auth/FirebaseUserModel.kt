package android.kotlin.foodclub.domain.models.auth

data class FirebaseUserModel(
    val userID: String,
    val username: String,
    val isOnline: Boolean,
    val fcmToken: String,
    val fullName: String,
    val profileImageUrl: String,
)
