package android.kotlin.foodclub.navigation.graphs

import android.kotlin.foodclub.views.home.CameraPreviewView
import android.kotlin.foodclub.views.home.CameraView
import android.kotlin.foodclub.views.home.CreateRecipeView
import android.kotlin.foodclub.views.home.DeleteRecipeView
import android.kotlin.foodclub.views.home.FollowerView
import android.kotlin.foodclub.views.home.HomeView
import android.kotlin.foodclub.views.home.MyBasketView
import android.kotlin.foodclub.views.home.SearchView
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.foodclub.ui.theme.BottomBarScreenObject
import com.example.foodclub.views.home.CreateView
import com.example.foodclub.views.home.DiscoverView
import android.kotlin.foodclub.views.home.ProfileView
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation

    fun NavGraphBuilder.homeNavigationGraph(navController: NavHostController, showSheet: Boolean,
                                            triggerBottomSheetModal: () -> Unit,
                                            triggerStory: () -> Unit,
                                            setBottomBarVisibility: (Boolean) -> Unit) {
    navigation(
        route = Graph.HOME,
        startDestination = BottomBarScreenObject.Home.route
    ) {
        settingsNavigationGraph(navController)

        composable(route = BottomBarScreenObject.Home.route) {
            setBottomBarVisibility(true)
            HomeView(navController = navController, triggerStoryView = triggerStory)
        }
        composable(route = BottomBarScreenObject.Profile.route + "?userId={userId}",
            arguments = listOf(navArgument("userId") { defaultValue = 0L
            type = NavType.LongType})
        ) {
            val userId = it.arguments?.getLong("userId")
            if(userId == null) {
                navController.popBackStack()
                return@composable
            }
            ProfileView(navController, userId)

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
        composable(route = HomeOtherRoutes.CameraPreviewView.route) { backStackEntry ->
            val uri = backStackEntry.arguments?.getString("uri") ?: ""
            CameraPreviewView(uri = uri, navController = navController) // **CHANGED THIS**
        }
        composable(route = HomeOtherRoutes.CreateRecipeView.route) {
            CreateRecipeView(navController = navController)
        }
        composable(route = HomeOtherRoutes.DeleteRecipeView.route  + "/{postId}",
            arguments = listOf(
                navArgument("postId") { defaultValue = 0L
                    type = NavType.LongType
                }
            )
        ) {
            val postId = it.arguments?.getLong("postId")
            if(postId == null || postId == 0L) {
                navController.popBackStack()
                return@composable
            }
            DeleteRecipeView(navController = navController, postId = postId)
        }

        composable(route = HomeOtherRoutes.FollowerView.route + "/{userId}",
            arguments = listOf(
                navArgument("userId") { nullable = true }
            )
        ) {
            it.arguments?.getString("userId")?.let { it1 ->
                FollowerView(navController = navController, viewType = "followers",
                    userId = it1.toLong())
            }
        }

        composable(route = HomeOtherRoutes.FollowingView.route + "/{userId}",
            arguments = listOf(
                navArgument("userId") { nullable = true }
            )
        ) {
            it.arguments?.getString("userId")?.let { it1 ->
                FollowerView(navController = navController, viewType = "following",
                    userId = it1.toLong())
            }
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
    object SettingsView : HomeOtherRoutes(route = "SETTINGS")
    object CameraView : HomeOtherRoutes(route = "CAMERA_VIEW")
    object CreateRecipeView : HomeOtherRoutes(route = "CREATE_RECIPE_VIEW")
    object CameraPreviewView : HomeOtherRoutes(route = "CAMERA_PREVIEW_VIEW/{uri}")

    object FollowerView : HomeOtherRoutes(route = "FOLLOWER_VIEW")

    object FollowingView : HomeOtherRoutes(route = "FOLLOWING_VIEW")

    object MyBasketView : HomeOtherRoutes(route = "BASKET_VIEW")

    object MySearchView : HomeOtherRoutes(route = "SEARCH_VIEW")
}
