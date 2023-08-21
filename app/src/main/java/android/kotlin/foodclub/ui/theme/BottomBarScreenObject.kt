package com.example.foodclub.ui.theme

import android.kotlin.foodclub.R
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource


sealed class BottomBarScreenObject(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int
) {
    object Home: BottomBarScreenObject(
        route= "HOME",
        title= "HOME",
        icon= R.drawable.nav_home_icon
    )
    object Play: BottomBarScreenObject(
        route = "PLAY",
        title = "PLAY",
        icon= R.drawable.nav_play_icon
    )
    object Create: BottomBarScreenObject(
        route = "CREATE",
        title = "CREATE",
        icon= R.drawable.nav_create_icon
    )
    object Discover: BottomBarScreenObject(
        route = "DISCOVER",
        title = "DISCOVER",
        icon= R.drawable.nav_discover_icon
    )
    object Profile: BottomBarScreenObject(
        route= "PROFILE",
        title = "PROFILE",
        icon= R.drawable.nav_profile_icon
    )
}