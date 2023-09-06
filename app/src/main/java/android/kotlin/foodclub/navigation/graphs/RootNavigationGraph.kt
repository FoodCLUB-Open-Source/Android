  package com.example.foodclub.navigation.graphs

import android.kotlin.foodclub.utils.helpers.SessionCache
import android.kotlin.foodclub.views.authentication.EmailSentView
import android.kotlin.foodclub.views.authentication.LogInWithEmail
import android.kotlin.foodclub.views.authentication.MainLogInAndSignUp
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodclub.views.authentication.ForgotPasswordView
import com.example.foodclub.views.home.HomeView
import com.example.foodclub.views.home.MainView


@Composable
fun RootNavigationGraph(navController: NavHostController, sessionCache: SessionCache) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION) {
        onBoardingNavigationGraph(navController = navController)
        authNavigationGraph(navController = navController, sessionCache = sessionCache)
        composable(route = Graph.HOME) {
            MainView(sessionCache = sessionCache)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val ON_BOARDING = "onBoarding_graph"


}