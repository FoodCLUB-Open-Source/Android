package android.kotlin.foodclub.views.authentication.forgotPassword.forgotPasswordScreen

data class ForgotPasswordState(
    val email: String = "",
    val errorOccurred: Boolean = false,
    val message: String = ""
) {
    companion object {
         fun default() = ForgotPasswordState(
            email = "",
            errorOccurred = false,
            message = ""
         )
    }
}
