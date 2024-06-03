package live.foodclub.presentation.ui.authentication.signupVerification

import androidx.navigation.NavHostController

interface SignupVerificationEvents {
    fun sendVerificationCode(navController: NavHostController)
    fun verifyCode(code: String, navController: NavHostController)
    fun setData(navController: NavHostController, username: String?, password: String?)
}