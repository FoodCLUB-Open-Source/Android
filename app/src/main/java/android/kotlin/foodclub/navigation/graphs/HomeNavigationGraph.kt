package com.example.foodclub.navigation.graphs

import android.kotlin.foodclub.views.home.CameraPreviewView
import android.kotlin.foodclub.views.home.CameraView
import android.kotlin.foodclub.views.home.ChangePasswordView
import android.kotlin.foodclub.views.home.CreateRecipeView
import android.kotlin.foodclub.views.home.DeleteRecipeView
import android.kotlin.foodclub.views.home.EditProfileSetting
import android.kotlin.foodclub.views.home.FollowerView
import android.kotlin.foodclub.views.home.FollowingView
import android.kotlin.foodclub.views.home.MyBasketView
import android.kotlin.foodclub.views.home.PrivacySetting
import android.kotlin.foodclub.views.home.SearchView
import android.kotlin.foodclub.views.home.SettingsView
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodclub.ui.theme.BottomBarScreenObject
import com.example.foodclub.views.home.CreateView
import com.example.foodclub.views.home.DiscoverView
import com.example.foodclub.views.home.HomeView
import com.example.foodclub.views.home.ProfileView

@Composable
    fun HomeNavigationGraph(navController: NavHostController, showSheet: Boolean, triggerBottomSheetModal: () -> Unit,
                            callbackEnableStoryView: (offset: IntOffset) -> Unit, storyViewMode: Boolean) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreenObject.Home.route
    ) {
        composable(route = BottomBarScreenObject.Home.route) {
            HomeView(navController = navController,
                callbackEnableStoryView = callbackEnableStoryView,
                storyViewMode = storyViewMode)
        }
        composable(route = BottomBarScreenObject.Profile.route) {
            ProfileView(navController)
        }
        composable(route = BottomBarScreenObject.Discover.route) {
            MyBasketView(navController = navController)
        }
        composable(route = BottomBarScreenObject.Create.route) {
            CreateView()
        }
        composable(route = BottomBarScreenObject.Play.route) {
            DiscoverView(navController = navController)
        }
        composable(route = HomeOtherRoutes.CameraView.route) {
            CameraView(navController = navController)
        }
        composable(route = HomeOtherRoutes.CameraPreviewView.route) {
            CameraPreviewView(it.arguments?.getString("uri") ?: "")
        }
        composable(route = HomeOtherRoutes.CreateRecipeView.route) {
            CreateRecipeView(navController = navController)
        }
        composable(route = HomeOtherRoutes.SettingsView.route) {
            SettingsView(navController = navController)
        }
        composable(route = HomeOtherRoutes.ChangePasswordView.route) {
            ChangePasswordView(navController = navController)
        }
        composable(route = HomeOtherRoutes.PrivacySetting.route) {
            PrivacySetting(navController = navController)
        }
        composable(route = HomeOtherRoutes.EditProfileSetting.route) {
            EditProfileSetting(navController = navController)
        }
        composable(route = HomeOtherRoutes.EditProfileSetting.route) {
            EditProfileSetting(navController = navController)
        }
        composable(route = HomeOtherRoutes.DeleteRecipeView.route) {
            DeleteRecipeView(navController = navController)
        }

        composable(route = HomeOtherRoutes.FollowerView.route) {
            FollowerView(navController = navController)
        }

        composable(route = HomeOtherRoutes.FollowingView.route) {
            FollowingView(navController = navController)
        }

        composable(route = HomeOtherRoutes.MyBasketView.route) {
            MyBasketView(navController = navController)
        }

        composable(route = HomeOtherRoutes.MySearchView.route) {
            SearchView(navController = navController)
        }

    }
}

sealed class HomeOtherRoutes(val route: String) {
    object DeleteRecipeView : HomeOtherRoutes(route = "DELETE_RECIPE")
    object EditProfileSetting : HomeOtherRoutes(route = "EDIT_PROFILE")
    object PrivacySetting : HomeOtherRoutes(route = "PRIVACY")
    object ChangePasswordView : HomeOtherRoutes(route = "CHANGE_PASSWORD")
    object SettingsView : HomeOtherRoutes(route = "SETTINGS")
    object CameraView : HomeOtherRoutes(route = "CAMERA_VIEW")
    object CreateRecipeView : HomeOtherRoutes(route = "CREATE_RECIPE_VIEW")
    object CameraPreviewView : HomeOtherRoutes(route = "CAMERA_PREVIEW_VIEW/{uri}")

    object FollowerView : HomeOtherRoutes(route = "FOLLOWER_VIEW")

    object FollowingView : HomeOtherRoutes(route = "FOLLOWING_VIEW")

    object MyBasketView : HomeOtherRoutes(route = "BASKET_VIEW")

    object MySearchView : HomeOtherRoutes(route = "SEARCH_VIEW")
}
