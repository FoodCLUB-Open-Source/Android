package android.kotlin.foodclub.domain.models.profile

data class UserDetailsModel(
    val id: Long,
    val userName: String,
    val email: String,
    val phoneNumber: String? = null,
    var profilePicture: String,
    val userBio: String? = null,
    val gender: String? = null,
    val createdAt: String,
    val dateOfBirth: String? = null,
    val dietaryPrefs: List<String>? = null,
    val country: String? = null,
    val shippingAddress: String? = null,
    val fullName: String? = null
)