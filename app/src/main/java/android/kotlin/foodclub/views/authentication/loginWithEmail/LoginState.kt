package android.kotlin.foodclub.views.authentication.loginWithEmail

data class LoginState(
    val loginStatus: String? = null
) {
    companion object {
         fun default() = LoginState(
            loginStatus = null
         )
    }
}
