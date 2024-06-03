package live.foodclub.presentation.navigation

import live.foodclub.utils.composables.sharedHiltViewModel
import live.foodclub.presentation.ui.settings.SettingsEvents
import live.foodclub.presentation.ui.settings.SettingsViewModel
import live.foodclub.presentation.ui.home.profile.composables.HelpAndSupportView
import live.foodclub.presentation.ui.settings.composables.ChangePasswordSettings
import live.foodclub.presentation.ui.settings.composables.EditProfileSetting
import live.foodclub.presentation.ui.settings.composables.PrivacyPolicyView
import live.foodclub.presentation.ui.settings.composables.PrivacySetting
import live.foodclub.presentation.ui.settings.SettingsView
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
                events = viewModel,
                state = state.value
            )
        }
        composable(SettingsScreen.EditProfile.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<SettingsViewModel>(navController)
            val state = viewModel.state.collectAsState()

            EditProfileSetting(
                navController = navController,
                user = state.value.user,
                events = viewModel
            )
        }
        composable(SettingsScreen.Privacy.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<SettingsViewModel>(navController)

            PrivacySetting(navController = navController)
        }
        composable(SettingsScreen.ChangePassword.route) { entry ->
            val viewModel = entry.sharedHiltViewModel<SettingsViewModel>(navController)
            val state = viewModel.state.collectAsState()
            val events: SettingsEvents = viewModel

            ChangePasswordSettings(
                error = state.value.error,
                onBackAction = { navController.popBackStack() }) { oldPassword, newPassword ->
                events.changePassword(oldPassword, newPassword)
            }
        }
        composable(SettingsScreen.PrivacyPolicy.route) { entry ->

            PrivacyPolicyView(navController = navController)
        }
        composable(SettingsScreen.HelpAndSupport.route) { entry ->
            HelpAndSupportView(navController = navController)
        }
    }
}

sealed class SettingsScreen(val route: String) {
    data object Main : SettingsScreen(route = "SETTINGS_MENU")
    data object Privacy : SettingsScreen(route = "SETTINGS_PRIVACY")
    data object PrivacyPolicy : SettingsScreen(route = "SETTINGS_PRIVACY_POLICY")
    data object EditProfile : SettingsScreen(route = "SETTINGS_EDIT_PROFILE")
    data object ChangePassword : SettingsScreen(route = "SETTINGS_CHANGE_PASS")
    data object HelpAndSupport: SettingsScreen(route = "SETTINGS_HELP_AND_SUPPORT")
}