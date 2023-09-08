package android.kotlin.foodclub.navigation.graphs

import android.kotlin.foodclub.views.home.CameraPreviewView
import android.kotlin.foodclub.views.home.CameraView
import android.kotlin.foodclub.views.home.ChangePasswordView
import android.kotlin.foodclub.views.home.CreateRecipeView
import android.kotlin.foodclub.views.home.GalleryView
//import android.kotlin.foodclub.views.home.PlayView
//import android.kotlin.foodclub.views.home.StoryView
import android.kotlin.foodclub.views.home.DeleteRecipeView
import android.kotlin.foodclub.views.home.EditProfileSetting
import android.kotlin.foodclub.views.home.MyBasketView
import android.kotlin.foodclub.views.home.PrivacySetting
import android.kotlin.foodclub.views.home.SettingsView
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodclub.navigation.graphs.Graph
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
            ProfileView(navController)
        }
        composable(route = BottomBarScreenObject.Discover.route) {
            MyBasketView()
        }
        composable(route = BottomBarScreenObject.Create.route) {
            CreateView()
        }
        composable(route = BottomBarScreenObject.Play.route) {
            DiscoverView()
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
        composable(route = HomeOtherRoutes.GalleryView.route){
            GalleryView(navController = navController, it.arguments?.getString("firstImage") ?: "")
        }
    }
}

sealed class HomeOtherRoutes(val route: String) {
    object EditProfileSetting : HomeOtherRoutes(route = "EDIT_PROFILE")
    object PrivacySetting : HomeOtherRoutes(route = "PRIVACY")
    object ChangePasswordView : HomeOtherRoutes(route = "CHANGE_PASSWORD")
    object SettingsView : HomeOtherRoutes(route = "SETTINGS")
    object CameraView : HomeOtherRoutes(route = "CAMERA_VIEW")
    object CreateRecipeView : HomeOtherRoutes(route = "CREATE_RECIPE_VIEW")
    object CameraPreviewView : HomeOtherRoutes(route = "CAMERA_PREVIEW_VIEW/{uri}")
    object GalleryView: HomeOtherRoutes(route = "GALLERY_VIEW/{firstImage}")
}
