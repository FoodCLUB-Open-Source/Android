package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.utils.composables.BottomSheetItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import android.kotlin.foodclub.config.ui.BottomBarScreenObject
import okio.ByteString.Companion.encodeUtf8

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
                fontFamily = Montserrat,
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
                onClick = {
                    navController.navigate("CAMERA_VIEW/${"story".encodeUtf8()}")
                    onDismiss()
                }

            )
            BottomSheetItem(
                icon = R.drawable.recipe_bottom_sheet_icon,
                text = "Create a Recipe",
//                onClick = { navController.navigate("CAMERA_VIEW/${"recipe".encodeUtf8()}")}//"CREATE_RECIPE_VIEW") }
                onClick = {
//                    navController.navigate("VIDEOTRIMMER")
                    navController.navigate("CREATE_RECIPE_VIEW")
                    onDismiss()
                }
            )
            Spacer(modifier = Modifier.height(25.dp))
        }

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

    val bottomBarDestination = screens.any { currentDestination?.route?.startsWith(it.route) == true }
    var screenHeight = LocalConfiguration.current.screenHeightDp.dp * 0.13f

    if (screenHeight < 90.dp) {
        screenHeight = 110.dp
    }
    if (bottomBarDestination) {
        NavigationBar (containerColor = Color.White, modifier = Modifier.height(screenHeight)) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController,
                    triggerBottomSheetModal = triggerBottomSheetModal,
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
    triggerBottomSheetModal: () -> Unit,
) {
    val icon = painterResource(screen.icon)

    NavigationBarItem(
        icon = {
            Icon(
                painter = icon,
                modifier = Modifier.size(if (screen.route == "CREATE") 40.dp else 20.dp),
                contentDescription = "Navigation Icon",
                tint = when {
                    screen is BottomBarScreenObject.Create -> Color.Unspecified
                    currentDestination?.hierarchy?.any { it.route?.startsWith(screen.route) == true } == true -> Color(0xFF7EC60B)
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