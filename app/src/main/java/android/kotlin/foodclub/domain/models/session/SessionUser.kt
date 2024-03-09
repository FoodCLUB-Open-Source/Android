package android.kotlin.foodclub.domain.models.session

data class SessionUser(
    val username: String,
    val userId: Long,
    val expiryAt: Long
)