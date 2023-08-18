package android.kotlin.foodclub.navigation.graphs

import android.kotlin.foodclub.views.home.FollowerFollowingView
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.foodclub.navigation.graphs.AuthScreen
import com.example.foodclub.navigation.graphs.Graph
import com.example.foodclub.navigation.graphs.OnBoardingScreen
import com.example.foodclub.views.authentication.ForgotPasswordView
import com.example.foodclub.views.home.ProfileView

fun NavGraphBuilder.profileNavigationGraph(navController: NavHostController){
    navigation(
        route = Graph.PROFILE,
        startDestination = ProfileNavigationScreens.FOLLOWER_VIEW.route
    ){
        composable(route = ProfileNavigationScreens.PROFILE_VIEW.route) {
            ProfileView(navController)
        }

        composable(route = ProfileNavigationScreens.FOLLOWER_VIEW.route) {
            FollowerFollowingView(navController)
        }
    }
}

sealed class ProfileNavigationScreens(val route: String) {

    object PROFILE_VIEW : ProfileNavigationScreens(route = "PROFILE_VIEW")
    object FOLLOWER_VIEW : ProfileNavigationScreens(route = "FOLLOWER_VIEW")


}
