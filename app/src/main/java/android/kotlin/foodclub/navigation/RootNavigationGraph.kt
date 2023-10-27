package android.kotlin.foodclub.navigation

import android.kotlin.foodclub.navigation.auth.authNavigationGraph
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost


  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  @Composable
fun RootNavigationGraph(navController: NavHostController, showSheet: Boolean,
                        triggerBottomSheetModal:  () -> Unit, triggerStory: () -> Unit,
                        setBottomBarVisibility: (Boolean) -> Unit) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.HOME
    ) {
        onBoardingNavigationGraph()
        authNavigationGraph(navController = navController, setBottomBarVisibility)
        homeNavigationGraph(navController, showSheet, triggerBottomSheetModal, triggerStory, setBottomBarVisibility)
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val ON_BOARDING = "onBoarding_graph"
}