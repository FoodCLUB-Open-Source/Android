package com.example.foodclub.navigation.graphs

import android.kotlin.foodclub.views.home.PlayView
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodclub.ui.theme.BottomBarScreenObject
import com.example.foodclub.views.home.CreateView
import com.example.foodclub.views.home.DiscoverView
import com.example.foodclub.views.home.HomeView
import com.example.foodclub.views.home.ProfileView

@Composable
fun HomeNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreenObject.Home.route
    ) {
        composable(route = BottomBarScreenObject.Home.route) {
            HomeView()
            // navController.navigate(Graph.DETAILS)
        }
        composable(route = BottomBarScreenObject.Profile.route) {
            ProfileView()
        }
        composable(route = BottomBarScreenObject.Discover.route) {
            DiscoverView()
        }

        composable(route = BottomBarScreenObject.Create.route) {
            CreateView()
        }
        composable(route = BottomBarScreenObject.Play.route) {
            PlayView()
        }

    }
}