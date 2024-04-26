package live.foodclub.domain.models.auth

data class ForgotChangePassword(
    val username: String,
    val code: String,
    val newPassword: String,
)
