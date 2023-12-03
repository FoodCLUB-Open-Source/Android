package android.kotlin.foodclub.navigation

import android.kotlin.foodclub.utils.composables.sharedHiltViewModel
import android.kotlin.foodclub.viewModels.home.SettingsViewModel
import android.kotlin.foodclub.views.settings.ChangePasswordSettings
import android.kotlin.foodclub.views.settings.EditProfileSetting
import android.kotlin.foodclub.views.settings.PrivacySetting
import android.kotlin.foodclub.views.settings.SettingsView
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.settingsNavigationGraph(navController: NavHostController) {
    navigation(
        route = HomeOtherRoutes.SettingsView.route,
        startDestination = SettingsScreen.Main.route
    ) {
        composable(SettingsScreen.Main.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<SettingsViewModel>(navController)
            val state = viewModel.state.collectAsState()

            SettingsView(
                navController = navController,
                viewModel = viewModel,
                state = state.value
            )
        }
        composable(SettingsScreen.EditProfile.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<SettingsViewModel>(navController)
            val state = viewModel.state.collectAsState()

            EditProfileSetting(
                navController = navController,
                user = state.value.user,
                viewModel = viewModel
            )
        }
        composable(SettingsScreen.Privacy.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<SettingsViewModel>(navController)

            PrivacySetting(navController = navController)
        }
        composable(SettingsScreen.ChangePassword.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<SettingsViewModel>(navController)
            val state = viewModel.state.collectAsState()

            ChangePasswordSettings(
                error = state.value.error,
                onBackAction = { navController.popBackStack() }) { oldPassword, newPassword ->
                viewModel.changePassword(oldPassword, newPassword)
            }
        }
    }
}

sealed class SettingsScreen(val route: String) {
    object Main : SettingsScreen(route = "SETTINGS_MENU")
    object Privacy : SettingsScreen(route = "SETTINGS_PRIVACY")
    object EditProfile : SettingsScreen(route = "SETTINGS_EDIT_PROFILE")
    object ChangePassword : SettingsScreen(route = "SETTINGS_CHANGE_PASS")
}