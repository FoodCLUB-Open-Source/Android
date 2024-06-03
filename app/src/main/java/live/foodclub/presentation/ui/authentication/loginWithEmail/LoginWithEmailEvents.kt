package live.foodclub.presentation.ui.authentication.loginWithEmail

import androidx.navigation.NavController

interface LoginWithEmailEvents {
    fun logInUser(userEmail: String?, userPassword: String?, navController: NavController)
}