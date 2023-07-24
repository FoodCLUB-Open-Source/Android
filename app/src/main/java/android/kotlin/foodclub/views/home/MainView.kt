package com.example.foodclub.views.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(navController: NavHostController = rememberNavController()) {
    val viewModel: HomeViewModel = viewModel()

    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        HomeNavigationGraph(navController = navController)
    }
}


@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreenObject.Home,
        BottomBarScreenObject.Play,
        BottomBarScreenObject.Create,
        BottomBarScreenObject.Profile,
        BottomBarScreenObject.Discover,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    //val screenHeight = LocalConfiguration.current.screenHeightDp.dp * 0.1f

    if (bottomBarDestination) {
        NavigationBar (containerColor = Color.White, modifier = Modifier.height(140.dp)) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreenObject,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val icon = painterResource(screen.icon)

    NavigationBarItem(
        icon = {
            Icon(
                painter = icon,
                contentDescription = "Navigation Icon",
                tint = when {
                    screen is BottomBarScreenObject.Create -> Color.Unspecified // No tint for Create icon
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true -> Color(0xFF7EC60B) // green color for selected item
                    else -> Color(0xFFB9B9B9) // dark grey color for unselected item
                }

            )
        },
        selected = false,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }

    )
}