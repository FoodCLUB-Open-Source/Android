package live.foodclub.navigation.auth

import live.foodclub.navigation.Graph
import live.foodclub.viewModels.authentication.loginWithEmail.LogInWithEmailViewModel
import live.foodclub.viewModels.authentication.mainLogin.MainLogInAndSignUpViewModel
import live.foodclub.viewModels.authentication.signupVerification.SignupVerificationViewModel
import live.foodclub.views.authentication.loginWithEmail.LogInWithEmail
import live.foodclub.views.authentication.MainLogInAndSignUp
import live.foodclub.views.authentication.signupVerification.SignupVerification
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import live.foodclub.views.authentication.TermsAndConditions
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
                viewModel = viewModel,
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
            TermsAndConditions(
                navController = navController
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
            val viewModel: SignupVerificationViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState()

            SignupVerification(
                navController = navController,
                email = backStackEntry.arguments?.getString(Auth.EMAIL.title),
                username = backStackEntry.arguments?.getString(Auth.USERNAME.title),
                password = backStackEntry.arguments?.getString(Auth.PASSWORD.title),
                state = state.value,
                events = viewModel
            )

        }
    }
}

sealed class AuthScreen(val route: String) {
    data object MainLogInAndSignUp : AuthScreen(route = "MENU")
    data object Login : AuthScreen(route = "LOGIN")
    data object SignUp : AuthScreen(route = "SIGN_UP")
    data object Forgot : AuthScreen(route = "FORGOT")

    data object VerifySignup : AuthScreen(route = "VERIFY_SIGN_UP")

    data object TermsAndConditions : AuthScreen(route = "TERMS")
}

enum class Auth(val title: String) {
    USERNAME("username"),
    PASSWORD("password"),
    EMAIL("email")
}