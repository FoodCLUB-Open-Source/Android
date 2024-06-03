package live.foodclub.presentation.ui.authentication.signUpWithEmail

import androidx.navigation.NavHostController

interface SignUpEmailEvents {
    fun signUpUser(navController: NavHostController)
    fun saveFullName(name: String)
    fun saveUsername(username: String)
    fun saveRepeatedEmail(repeatedEmail: String)
    fun saveEmailPasswordData(email: String, password: String)
}