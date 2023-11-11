package android.kotlin.foodclub.navigation

//import android.kotlin.foodclub.views.home.PlayView
//import android.kotlin.foodclub.views.home.StoryView
//import com.example.foodclub.navigation.graphs.Graph
import android.kotlin.foodclub.views.home.CameraPreviewView
import android.kotlin.foodclub.views.home.CameraView
import android.kotlin.foodclub.views.home.FollowerView
import android.kotlin.foodclub.views.home.GalleryView
import android.kotlin.foodclub.views.home.HomeView
import android.kotlin.foodclub.views.home.MyBasketView
import android.kotlin.foodclub.views.home.ProfileView
import android.kotlin.foodclub.views.home.SearchView
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import android.kotlin.foodclub.config.ui.BottomBarScreenObject
import android.kotlin.foodclub.views.home.CreateView
import android.kotlin.foodclub.views.home.DiscoverView
import android.kotlin.foodclub.views.home.MyDigitalPantryView
import android.kotlin.foodclub.views.home.TakeProfilePhotoView


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.homeNavigationGraph(
    navController: NavHostController, showSheet: Boolean,
    triggerBottomSheetModal: () -> Unit,
    triggerStory: () -> Unit,
    setBottomBarVisibility: (Boolean) -> Unit
) {
    navigation(
        route = Graph.HOME,
        startDestination = BottomBarScreenObject.Home.route
    ) {
        settingsNavigationGraph(navController)
        createRecipeNavigationGraph(navController, setBottomBarVisibility)

        composable(route = BottomBarScreenObject.Home.route) {
            setBottomBarVisibility(true)
            HomeView(navController = navController, triggerStoryView = triggerStory)
        }
        composable(
            route = BottomBarScreenObject.Profile.route + "?userId={userId}",
            arguments = listOf(navArgument("userId") {
                defaultValue = 0L
                type = NavType.LongType
            })
        ) {
            val userId = it.arguments?.getLong("userId")
            if (userId == null) {
                navController.popBackStack()
                return@composable
            }
            ProfileView(navController, userId)

        }
        composable(route = BottomBarScreenObject.Discover.route) {
            MyBasketView()
        }
        composable(route = BottomBarScreenObject.Create.route) {
            CreateView()
        }
        composable(route = BottomBarScreenObject.Play.route) {
            DiscoverView(navController = navController)
        }
        composable(route = HomeOtherRoutes.CameraView.route) {
            val state = it.arguments?.getString("state") ?: ""
            CameraView(navController = navController, stateEncoded = state)
        }
        composable(route = HomeOtherRoutes.VideoTrimmerView.route) {
            val state = it.arguments?.getString("state") ?: ""
//            CameraView(navController = navController, stateEncoded = state)

            CreateView()
        }
        composable(route = HomeOtherRoutes.CameraPreviewView.route) { backStackEntry ->
            val uri = backStackEntry.arguments?.getString("uri") ?: ""
            val state = backStackEntry.arguments?.getString("state") ?: ""
            CameraPreviewView(
                uri = uri,
                navController = navController,
                state = state
            ) // **CHANGED THIS**
        }
//        composable(route = HomeOtherRoutes.CreateRecipeView.route) {
//            setBottomBarVisibility(false)
//            CreateRecipeView(navController = navController)
//
////            CreateView()
//        }

        composable(route = HomeOtherRoutes.GalleryView.route) {
            val state = it.arguments?.getString("state") ?: ""
            GalleryView(navController = navController, stateEncoded = state)
        }

        composable(route = HomeOtherRoutes.FollowerView.route + "/{userId}",
            arguments = listOf(
                navArgument("userId") { nullable = true }
            )
        ) {
            it.arguments?.getString("userId")?.let { it1 ->
                FollowerView(
                    navController = navController, viewType = "followers",
                    userId = it1.toLong()
                )
            }
        }

        composable(route = HomeOtherRoutes.FollowingView.route + "/{userId}",
            arguments = listOf(
                navArgument("userId") { nullable = true }
            )
        ) {
            it.arguments?.getString("userId")?.let { it1 ->
                FollowerView(
                    navController = navController, viewType = "following",
                    userId = it1.toLong()
                )
            }
        }

        composable(route = HomeOtherRoutes.MyBasketView.route) {
            MyBasketView()
        }

        composable(route = HomeOtherRoutes.MySearchView.route) {
            SearchView(navController = navController)
        }
        composable(route = HomeOtherRoutes.MyDigitalPantryView.route) {
            MyDigitalPantryView(navController = navController)
        }
        composable(route = HomeOtherRoutes.TakeProfilePhotoView.route) {
            TakeProfilePhotoView(navController = navController)
        }

    }
}


sealed class HomeOtherRoutes(val route: String) {
    object DeleteRecipeView : HomeOtherRoutes(route = "DELETE_RECIPE")
    object SettingsView : HomeOtherRoutes(route = "SETTINGS")
    object CameraView : HomeOtherRoutes(route = "CAMERA_VIEW/{state}")
    object CreateRecipeView : HomeOtherRoutes(route = "CREATE_RECIPE_VIEW")
    object CameraPreviewView : HomeOtherRoutes(route = "CAMERA_PREVIEW_VIEW/{uri}/{state}")
    object GalleryView : HomeOtherRoutes(route = "GALLERY_VIEW/{state}")
    object FollowerView : HomeOtherRoutes(route = "FOLLOWER_VIEW")

    object FollowingView : HomeOtherRoutes(route = "FOLLOWING_VIEW")

    object MyBasketView : HomeOtherRoutes(route = "BASKET_VIEW")
    object MyDigitalPantryView : HomeOtherRoutes(route = "MY_DIGITAL_PANTRY_VIEW")
    object MySearchView : HomeOtherRoutes(route = "SEARCH_VIEW")

    object VideoTrimmerView : HomeOtherRoutes(route = "VIDEOTRIMMER")
    object TakeProfilePhotoView: HomeOtherRoutes(route = "TAKE_PROFILE_PHOTO_VIEW")
}