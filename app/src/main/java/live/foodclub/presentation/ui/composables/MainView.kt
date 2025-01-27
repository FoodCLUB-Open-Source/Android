package live.foodclub.presentation.ui.composables

import live.foodclub.R
import live.foodclub.config.ui.BottomBarScreenObject
import live.foodclub.config.ui.Montserrat
import live.foodclub.config.ui.foodClubGreen
import live.foodclub.presentation.navigation.CreateRecipeScreen
import live.foodclub.presentation.navigation.HomeOtherRoutes
import live.foodclub.utils.composables.customComponents.BottomSheetItem
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismiss: () -> Unit,
    navController: NavHostController
) {
    ModalBottomSheet(
        containerColor = Color.White,
        onDismissRequest = { onDismiss() },
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(id = R.string.create),
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
            )
            Divider(
                color = Color.Gray,
                thickness = dimensionResource(id = R.dimen.dim_0pt8),
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.dim_16))
            )
            BottomSheetItem(
                icon = R.drawable.story_bottom_sheet_icon,
                text = stringResource(id = R.string.create_a_story),
                onDismiss = onDismiss,
                onClick = {
                    navController.navigate(route = HomeOtherRoutes.TakeSnapPhotoView.route)
                    //onDismiss()
                }

            )
            BottomSheetItem(
                icon = R.drawable.recipe_bottom_sheet_icon,
                text = stringResource(id = R.string.create_a_recipe),
                onDismiss = onDismiss,
                onClick = {
                    navController.navigate(route = CreateRecipeScreen.CameraView.route)
                }
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_25)))
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
    if (bottomBarDestination) {
        NavigationBar (containerColor = Color.White) {
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
                modifier = Modifier.size(if (screen.route == "CREATE") dimensionResource(id = R.dimen.dim_40) else dimensionResource(id = R.dimen.dim_20)),
                contentDescription = stringResource(id = R.string.navigation_icon),
                tint = when {
                    screen is BottomBarScreenObject.Create -> Color.Unspecified
                    currentDestination?.hierarchy?.any { it.route?.startsWith(screen.route) == true } == true -> foodClubGreen
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