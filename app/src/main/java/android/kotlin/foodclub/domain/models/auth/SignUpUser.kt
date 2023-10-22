package android.kotlin.foodclub.domain.models.auth

data class SignUpUser(
    val username: String,
    val email: String,
    val password: String,
    val name: String
)
