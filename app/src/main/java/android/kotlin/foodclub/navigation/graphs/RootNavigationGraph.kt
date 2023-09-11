  package android.kotlin.foodclub.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodclub.navigation.graphs.authNavigationGraph
import com.example.foodclub.views.home.MainView


@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        onBoardingNavigationGraph(navController = navController)
        authNavigationGraph(navController = navController)
        composable(route = Graph.HOME) {
            MainView()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val ON_BOARDING = "onBoarding_graph"


}