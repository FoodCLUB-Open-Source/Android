package android.kotlin.foodclub.utils.composables

import android.annotation.SuppressLint
import android.kotlin.foodclub.navigation.graphs.Graph
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import android.kotlin.foodclub.views.home.BottomBar
import android.kotlin.foodclub.views.home.BottomSheet

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainLayout(navController: NavHostController,
               rootNavigationGraph: @Composable (Boolean, () -> Unit, () -> Unit, (Boolean) -> Unit) -> Unit) {
    var showSheet by remember { mutableStateOf(false) }
    val triggerBottomSheetModal: () -> Unit = {
        showSheet = !showSheet
    }

    var showStory by remember { mutableStateOf(false) }
    val triggerStory: () -> Unit = {
        showStory = !showStory
    }

    var showBottomBar by remember { mutableStateOf(navController.currentDestination?.hierarchy?.any {
        it.route?.startsWith(Graph.HOME) ?: false } == true) }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar && !showStory,
                exit = slideOutVertically(targetOffsetY = { it }),
                enter = slideInVertically(initialOffsetY = { it })
            ) {
                BottomBar(navController = navController, triggerBottomSheetModal)
            }
             }
    ) {
        if (showBottomBar && !showStory && showSheet) {
            BottomSheet(triggerBottomSheetModal, navController)
        }
        rootNavigationGraph(showSheet, triggerBottomSheetModal, triggerStory) { showBottomBar = it }
    }

}