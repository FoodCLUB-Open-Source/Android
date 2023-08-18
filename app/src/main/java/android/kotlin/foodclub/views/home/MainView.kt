package com.example.foodclub.views.home

import android.annotation.SuppressLint
import android.kotlin.foodclub.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodclub.navigation.graphs.HomeNavigationGraph
import com.example.foodclub.ui.theme.BottomBarScreenObject
import com.example.foodclub.ui.theme.BottomBarScreenObject.Create.icon
import com.example.foodclub.viewmodels.home.HomeViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

@Composable
fun BottomSheetItem(icon: Int, text: String,
                    onDismiss: () -> Unit, navController: NavHostController, destination: String) {
    Row(
        modifier = Modifier
            .clickable { navController.navigate(destination); onDismiss() }
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = text,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(onDismiss: () -> Unit, navController: NavHostController) {
    ModalBottomSheet(
        containerColor = Color.White,
        onDismissRequest = { onDismiss() },
        //sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Create",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold,
            )
            Divider(
                color = Color.Gray,
                thickness = 0.8.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            BottomSheetItem(
                icon = R.drawable.story_bottom_sheet_icon,
                text = "Create a Story",
                onDismiss,
                navController = navController,
                "CAMERA_VIEW"
            )
            BottomSheetItem(
                icon = R.drawable.recipe_bottom_sheet_icon,
                text = "Create a Recipe",
                onDismiss,
                navController = navController,
                "CREATE_RECIPE_VIEW"
            )
            Spacer(modifier = Modifier.height(25.dp))
        }

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(navController: NavHostController = rememberNavController()) {
    val viewModel: HomeViewModel = viewModel()
    var showSheet by remember { mutableStateOf(false) }

    val triggerBottomSheetModal: () -> Unit = {
        showSheet = !showSheet
    }

    Scaffold(
        bottomBar = { BottomBar(navController = navController, triggerBottomSheetModal) }
    ) {
        if (showSheet) {
            BottomSheet(triggerBottomSheetModal, navController)
        }
        HomeNavigationGraph(navController = navController, showSheet = showSheet, triggerBottomSheetModal)
    }
}


@Composable
fun BottomBar(navController: NavHostController, triggerBottomSheetModal: () -> Unit) {
    val screens = listOf(
        BottomBarScreenObject.Home,
        BottomBarScreenObject.Play,
        BottomBarScreenObject.Create,
        BottomBarScreenObject.Discover,
        BottomBarScreenObject.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    //val screenHeight = LocalConfiguration.current.screenHeightDp.dp * 0.1f

    if (bottomBarDestination) {
        NavigationBar (containerColor = Color.White, modifier = Modifier.height(115.dp)) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController,
                    triggerBottomSheetModal = triggerBottomSheetModal
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreenObject,
    currentDestination: NavDestination?,
    navController: NavHostController,
    triggerBottomSheetModal: () -> Unit
) {
    val icon = painterResource(screen.icon)

    NavigationBarItem(
        icon = {
            Icon(
                painter = icon,
                contentDescription = "Navigation Icon",
                tint = when {
                    screen is BottomBarScreenObject.Create -> Color.Unspecified
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true -> Color(0xFF7EC60B)
                    else -> Color(0xFFB9B9B9)
                }

            )
        },
        selected = false,
        onClick = {
            if (screen.route == "CREATE") {
                triggerBottomSheetModal()
            } else {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        }

    )
}