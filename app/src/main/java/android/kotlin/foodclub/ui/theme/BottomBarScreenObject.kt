package com.example.foodclub.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomBarScreenObject(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home: BottomBarScreenObject(
        route= "HOME",
        title= "HOME",
        icon= Icons.Default.Home
    )

    object Profile: BottomBarScreenObject(
        route= "PROFILE",
        title = "PROFILE",
        icon= Icons.Default.Person
    )

    object Discover: BottomBarScreenObject(
        route = "DISCOVER",
        title = "DISCOVER",
        icon= Icons.Default.Settings
    )
}