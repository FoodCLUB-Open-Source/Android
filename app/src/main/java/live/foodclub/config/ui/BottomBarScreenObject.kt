package live.foodclub.config.ui

import live.foodclub.R
import androidx.annotation.DrawableRes

sealed class BottomBarScreenObject(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int
) {
    data object Home: BottomBarScreenObject(
        route= "HOME",
        title= "HOME",
        icon= R.drawable.nav_home_icon
    )
    data object Play: BottomBarScreenObject(
        route = "PLAY",
        title = "PLAY",
        icon= R.drawable.nav_play_icon
    )
    data object Create: BottomBarScreenObject(
        route = "CREATE",
        title = "CREATE",
        icon= R.drawable.nav_create_icon
    )
    data object Discover: BottomBarScreenObject(
        route = "DISCOVER",
        title = "DISCOVER",
        icon= R.drawable.nav_discover_icon
    )
    data object Profile: BottomBarScreenObject(
        route= "PROFILE",
        title = "PROFILE",
        icon= R.drawable.nav_profile_icon
    )
}