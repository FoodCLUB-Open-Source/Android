package com.example.foodclub.navigation.graphs

import android.kotlin.foodclub.views.home.CameraPreviewView
import android.kotlin.foodclub.views.home.CameraView
import android.kotlin.foodclub.views.home.CreateRecipeView
import android.kotlin.foodclub.views.home.DeleteRecipeView
import android.kotlin.foodclub.views.home.PlayView
import androidx.compose.runtime.Composable
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
    fun HomeNavigationGraph(navController: NavHostController, showSheet: Boolean, triggerBottomSheetModal: () -> Unit) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreenObject.Home.route
    ) {
        composable(route = BottomBarScreenObject.Home.route) {
            HomeView(navController = navController)
        }
        composable(route = BottomBarScreenObject.Profile.route) {
            ProfileView()
        }
        composable(route = BottomBarScreenObject.Discover.route) {
            DiscoverView()
        }
        composable(route = BottomBarScreenObject.Create.route) {
            CreateView()
        }
        composable(route = BottomBarScreenObject.Play.route) {
            PlayView()
        }
        composable(route = HomeOtherRoutes.CameraView.route) {
            CameraView(navController = navController)
        }
        composable(route = HomeOtherRoutes.CameraPreviewView.route) {
            CameraPreviewView(it.arguments?.getString("uri") ?: "")
        }
        composable(route = HomeOtherRoutes.CreateRecipeView.route) {
            CreateRecipeView()
        }

    }
}

sealed class HomeOtherRoutes(val route: String) {
    object CameraView : HomeOtherRoutes(route = "CAMERA_VIEW")
    object CreateRecipeView : HomeOtherRoutes(route = "CREATE_RECIPE_VIEW")
    object CameraPreviewView : HomeOtherRoutes(route = "CAMERA_PREVIEW_VIEW/{uri}")
}
