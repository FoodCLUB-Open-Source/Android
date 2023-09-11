  package android.kotlin.foodclub.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.foodclub.navigation.graphs.authNavigationGraph
import com.example.foodclub.navigation.graphs.homeNavigationGraph


@Composable
fun RootNavigationGraph(navController: NavHostController, showSheet: Boolean,
                        triggerBottomSheetModal:  () -> Unit, triggerStory: () -> Unit,
                        setBottomBarVisibility: (Boolean) -> Unit) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        onBoardingNavigationGraph(navController = navController, setBottomBarVisibility)
        authNavigationGraph(navController = navController, setBottomBarVisibility)
        homeNavigationGraph(navController, showSheet, triggerBottomSheetModal, triggerStory, setBottomBarVisibility)

//        composable(route = Graph.HOME) {
//            MainView()
//        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val ON_BOARDING = "onBoarding_graph"


}