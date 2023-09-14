package android.kotlin.foodclub.navigation.graphs

import android.kotlin.foodclub.views.authentication.LogInWithEmail
import android.kotlin.foodclub.views.authentication.MainLogInAndSignUp
import android.kotlin.foodclub.views.authentication.SignupVerification
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import android.kotlin.foodclub.views.authentication.ChangePasswordView
import android.kotlin.foodclub.views.authentication.EmailSentView
import com.example.foodclub.views.authentication.ConfirmIdentityView
import android.kotlin.foodclub.views.authentication.ForgotPasswordView

fun NavGraphBuilder.authNavigationGraph(navController: NavHostController, setBottomBarVisibility: (Boolean) -> Unit) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.MainLogInAndSignUp.route
    ) {
        signupNavigationGraph(navController)

        composable(route = AuthScreen.MainLogInAndSignUp.route) {
            setBottomBarVisibility(false)
            MainLogInAndSignUp(navController)
        }
        composable(route = AuthScreen.Login.route) {
            LogInWithEmail(navController)
        }
        composable(route = AuthScreen.Forgot.route) {
            ForgotPasswordView(navController)
        }
        composable(route = AuthScreen.ConfirmId.route) {
            ConfirmIdentityView()
        }
        composable(route = AuthScreen.VerifySignup.route + "/{username}?resendCode={resendCode}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
                navArgument("resendCode") { defaultValue = false }
            )
        ) {backStackEntry ->
            SignupVerification(navController, backStackEntry.arguments?.getString("username"),
                backStackEntry.arguments?.getBoolean("resendCode"))
        }

        composable(route = AuthScreen.ForgotEmailSent.route) {
            EmailSentView()
        }

        composable(route = AuthScreen.ChangePassword.route + "/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) {backStackEntry ->
            ChangePasswordView(navController,backStackEntry.arguments?.getString("username"))
        }

    }
}

sealed class AuthScreen(val route: String) {
    object MainLogInAndSignUp : AuthScreen(route = "MENU")
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
    object Forgot : AuthScreen(route = "FORGOT")

    object ForgotEmailSent : AuthScreen(route = "FORGOT_EMAIL_SENT")

    object ChangePassword : AuthScreen(route = "CHANGE_PASSWORD")

    object ConfirmId : AuthScreen(route = "CONFIRM_ID")
    object VerifySignup : AuthScreen(route = "VERIFY_SIGN_UP")
}
