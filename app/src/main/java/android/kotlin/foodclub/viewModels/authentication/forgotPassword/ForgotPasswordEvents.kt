package android.kotlin.foodclub.viewModels.authentication.forgotPassword

interface ForgotPasswordEvents {
    fun sendCode(email: String, onSuccess: () -> Unit)
    fun changePassword(verificationCode: String, password: String, onSuccess: () -> Unit)
    fun onEmailChange(email: String)
}