package live.foodclub.presentation.navigation.auth

import live.foodclub.utils.composables.sharedHiltViewModel
import live.foodclub.presentation.ui.authentication.forgotPassword.ForgotPasswordEvents
import live.foodclub.presentation.ui.authentication.forgotPassword.ForgotPasswordViewModel
import live.foodclub.presentation.ui.authentication.forgotPassword.ChangePasswordView
import live.foodclub.presentation.ui.authentication.forgotPassword.EmailSentView
import live.foodclub.presentation.ui.authentication.forgotPassword.forgotPasswordScreen.ForgotPasswordView
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument

fun NavGraphBuilder.forgotPasswordNavigationGraph(navController: NavHostController) {
    navigation(
        route = "${AuthScreen.Forgot.route}?email={email}",
        startDestination = ForgotPasswordScreen.ForgotPasswordPage1.route,
        arguments = listOf(navArgument("email") {
            nullable = true
            type = NavType.StringType
        } )
    ) {
        composable(route = ForgotPasswordScreen.ForgotPasswordPage1.route) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry("${AuthScreen.Forgot.route}?email={email}")
            }
            val email = parentEntry.arguments?.getString("email") ?: ""
            val viewModel = entry.sharedHiltViewModel<ForgotPasswordViewModel>(navController)

            viewModel.setEmail(email)
            val state = viewModel.state.collectAsState()
            val events : ForgotPasswordEvents = viewModel

            ForgotPasswordView(
                onValuesUpdate = {
                    events.sendCode(it) {
                        navController.navigate(route = ForgotPasswordScreen.ForgotPasswordPage2.route)
                    }
                },
                onBackButtonClick = {
                    navController.navigate(AuthScreen.Login.route) {
                        popUpTo(AuthScreen.Login.route) { inclusive = true }
                    }
                },
                onEmailChange = { events.onEmailChange(it) },
                state = state.value
            )
        }
        composable(ForgotPasswordScreen.ForgotPasswordPage2.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<ForgotPasswordViewModel>(navController)
            val state = viewModel.state.collectAsState()
            val events : ForgotPasswordEvents = viewModel

            ChangePasswordView(
                onValuesUpdate = { code, password ->
                    events.changePassword(code, password) {
                        navController.navigate(route = ForgotPasswordScreen.ForgotPasswordPage3.route)
                    }
                },
                onBackButtonClick = { navController.popBackStack() },
                state = state.value,
            )
        }
        composable(route = ForgotPasswordScreen.ForgotPasswordPage3.route) {
            EmailSentView(
                onClick = {
                    navController.navigate(AuthScreen.Login.route) {
                        popUpTo(AuthScreen.Login.route) { inclusive = true }
                    }
                },
                onBackButtonClick = {
                    navController.navigate(AuthScreen.Login.route) {
                        popUpTo(AuthScreen.Login.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

sealed class ForgotPasswordScreen(val route: String) {
    data object ForgotPasswordPage1 : ForgotPasswordScreen(route = "forgot_password_page_1")
    data object ForgotPasswordPage2 : ForgotPasswordScreen(route = "forgot_password_page_2")
    data object ForgotPasswordPage3 : ForgotPasswordScreen(route = "forgot_password_page_3")
}