package android.kotlin.foodclub.domain.models.profile

data class UserDetailsModel(
    val id: Long,
    val userName: String,
    val email: String,
    var profilePicture: String,
    val createdAt: String
)