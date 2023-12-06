package android.kotlin.foodclub.navigation.auth

import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.viewModels.authentication.loginWithEmail.LogInWithEmailViewModel
import android.kotlin.foodclub.viewModels.authentication.mainLogin.MainLogInAndSignUpViewModel
import android.kotlin.foodclub.viewModels.authentication.TermsAndConditionsViewModel
import android.kotlin.foodclub.views.authentication.loginWithEmail.LogInWithEmail
import android.kotlin.foodclub.views.authentication.MainLogInAndSignUp
import android.kotlin.foodclub.views.authentication.SignupVerification
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import android.kotlin.foodclub.views.authentication.TermsAndConditions
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

fun NavGraphBuilder.authNavigationGraph(
    navController: NavHostController,
    setBottomBarVisibility: (Boolean) -> Unit
) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.MainLogInAndSignUp.route
    ) {
        signupNavigationGraph(navController)
        forgotPasswordNavigationGraph(navController)

        composable(route = AuthScreen.MainLogInAndSignUp.route) {
            val viewModel: MainLogInAndSignUpViewModel = viewModel()
            setBottomBarVisibility(false)

            MainLogInAndSignUp(
                navController = navController,
                events = viewModel
            )

        }
        composable(route = AuthScreen.Login.route) {
            val viewModel: LogInWithEmailViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState()

            LogInWithEmail(
                navController = navController,
                events = viewModel,
                state = state.value
            )
        }

        composable(route = AuthScreen.TermsAndConditions.route) {
            val viewModel: TermsAndConditionsViewModel = viewModel()

            TermsAndConditions(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(route = AuthScreen.VerifySignup.route + "/{username}?password={password}&email={email}",

            arguments = listOf(
                navArgument(Auth.USERNAME.title) { type = NavType.StringType },
                navArgument(Auth.PASSWORD.title) {
                    nullable = true
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            SignupVerification(
                navController = navController,
                email = backStackEntry.arguments?.getString(Auth.EMAIL.title),
                username = backStackEntry.arguments?.getString(Auth.USERNAME.title),
                password = backStackEntry.arguments?.getString(Auth.PASSWORD.title)
            )

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

enum class Auth(val title: String) {
    USERNAME("username"),
    PASSWORD("password"),
    EMAIL("email")
}