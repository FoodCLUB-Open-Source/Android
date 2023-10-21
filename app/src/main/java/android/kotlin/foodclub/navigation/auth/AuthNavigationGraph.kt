package android.kotlin.foodclub.navigation.auth

import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.views.authentication.LogInWithEmail
import android.kotlin.foodclub.views.authentication.MainLogInAndSignUp
import android.kotlin.foodclub.views.authentication.SignupVerification
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import android.kotlin.foodclub.views.authentication.TermsAndConditions

fun NavGraphBuilder.authNavigationGraph(navController: NavHostController, setBottomBarVisibility: (Boolean) -> Unit) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.MainLogInAndSignUp.route
    ) {
        signupNavigationGraph(navController)
        forgotPasswordNavigationGraph(navController)

        composable(route = AuthScreen.MainLogInAndSignUp.route) {

            setBottomBarVisibility(false)
            MainLogInAndSignUp(navController)

        }
        composable(route = AuthScreen.Login.route) {
            LogInWithEmail(navController)
        }

        composable(route = AuthScreen.TermsAndConditions.route) {
            TermsAndConditions(navController)
        }

        composable(route = AuthScreen.VerifySignup.route + "/{username}?password={password}&email={email}",

            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
                navArgument("password") { nullable = true
                    type = NavType.StringType }
            )
        ) {backStackEntry ->
            SignupVerification(navController,backStackEntry.arguments?.getString("email"),
                backStackEntry.arguments?.getString("username"),
                backStackEntry.arguments?.getString("password"))

        }
    }
}

sealed class AuthScreen(val route: String) {
    object MainLogInAndSignUp : AuthScreen(route = "MENU")
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
    object Forgot : AuthScreen(route = "FORGOT")

    object VerifySignup : AuthScreen(route = "VERIFY_SIGN_UP")

    object TermsAndConditions : AuthScreen(route = "TERMS")
}