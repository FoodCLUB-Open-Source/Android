package android.kotlin.foodclub.utils.composables

import android.annotation.SuppressLint
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.PlusJakartaSans
import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.viewmodels.BaseViewModel
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainLayout(navController: NavHostController,
               rootNavigationGraph: @Composable (Boolean, () -> Unit, () -> Unit, (Boolean) -> Unit) -> Unit) {
    //Check if user is logged in, otherwise - redirect to auth navigation graph
    val baseViewModel: BaseViewModel = hiltViewModel()
    val currentSessionState = baseViewModel.currentSession.collectAsState()
    LaunchedEffect(currentSessionState.value) {
        baseViewModel.checkSession(navController = navController)
    }

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

@Composable
fun AuthLayout(header: String, subHeading: String? = null,
               message: String = "", errorOccurred: Boolean = false,
               onBackButtonClick: () -> Unit, content: @Composable () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 32.dp, end = 32.dp, top = 100.dp, bottom = 32.dp)) {
        Column(Modifier.weight(1F)) {
            BackButton(onBackButtonClick)

            Column(Modifier.padding(top = 32.dp)) {
                header.split("\n").forEach {
                    Text(
                        text = it,
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )
                }

                if(!subHeading.isNullOrEmpty()) {
                    Text(
                        text = subHeading,
                        fontSize = if(subHeading.length > 50) 16.sp else 18.sp,
                        fontFamily = Montserrat,  // AS ITS A CLEAN CUT FONT
                        color = Color(0xFF000000).copy(alpha = 0.4f)
                    )
                }
            }

            Text(
                text = if(errorOccurred && message.isNotEmpty()) "Error: $message" else message,
                fontFamily = Montserrat,
                color = if(errorOccurred) Color.Red else Color.Green,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(2F).fillMaxSize()
        ) {
            content()
        }

        Box(Modifier.weight(1F)) { TermsAndConditionsInfoFooter() }
    }
}