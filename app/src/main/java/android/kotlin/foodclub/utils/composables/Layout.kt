package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Black
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.PlusJakartaSans
import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.viewModels.BaseViewModel
import android.kotlin.foodclub.views.home.BottomBar
import android.kotlin.foodclub.views.home.BottomSheet
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController

@Composable
fun MainLayout(
    navController: NavHostController,
    rootNavigationGraph: @Composable (Boolean, () -> Unit, () -> Unit, (Boolean) -> Unit) -> Unit
) {
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

    var showBottomBar by remember {
        mutableStateOf(navController.currentDestination?.hierarchy?.any {
            it.route?.startsWith(Graph.HOME) ?: false
        } == true)
    }

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
    ) { padding->
        if (showBottomBar && !showStory && showSheet) {
            BottomSheet(triggerBottomSheetModal, navController)
        }
        Box(
            modifier = Modifier
                .padding(bottom = padding.calculateBottomPadding())
        ){
            rootNavigationGraph(
                showSheet,
                triggerBottomSheetModal,
                triggerStory
            ) { showBottomBar = it }
        }
    }

}

/**
 * Auth Layout
 *
 * Layout repeated in all auth screens. You only need to make a code for middle contents of
 * the screen. The rest is generated based on the params you provide (including screen title,
 * subheading, back button and footer)
 *
 * @param header Main screen title text. Can put multiple line text by using "\n" as a divisor
 * @param subHeading Grey subheading displayed under the title. When the text length is bigger than
 * 50, the text will be divided into two lines
 * @param message Message like acceptance or error which would be displayed under the subheading.
 * If no message provided, nothing would be displayed
 * @param errorOccurred Boolean if the provided message is an error or not. This boolean changes
 * message colour and adds "Error" prefix if true
 * @param onBackButtonClick Executes when back button is clicked
 * @param content All composables which should be displayed in the middle of the screen
 */
@Composable
fun AuthLayout(
    header: String,
    subHeading: String? = null,
    message: String = "",
    errorOccurred: Boolean = false,
    onBackButtonClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(
                start = dimensionResource(id = R.dimen.dim_32),
                end = dimensionResource(id = R.dimen.dim_32),
                top = dimensionResource(id = R.dimen.dim_100),
                bottom = dimensionResource(id = R.dimen.dim_32)
            )
    ) {
        Column(Modifier.weight(1F)) {
            BackButton(onBackButtonClick)

            Column(Modifier.padding(top = dimensionResource(id = R.dimen.dim_32))) {
                header.split("\n").forEach {
                    Text(
                        text = it,
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = TextUnit(-1.28f, TextUnitType.Sp),
                        fontSize = dimensionResource(id = R.dimen.fon_32).value.sp
                    )
                }

                if (!subHeading.isNullOrEmpty()) {
                    Text(
                        text = subHeading,
                        fontSize = if (subHeading.length > 50) dimensionResource(id = R.dimen.fon_16).value.sp else dimensionResource(
                            id = R.dimen.fon_18
                        ).value.sp,
                        fontFamily = Montserrat,
                        color = Black.copy(alpha = 0.4f)
                    )
                }
            }

            Text(
                text = if (errorOccurred && message.isNotEmpty()) stringResource(
                    id = R.string.error_message,
                    message
                ) else message,
                fontFamily = Montserrat,
                color = if (errorOccurred) Color.Red else Color.Green,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_4))
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_16)),
            modifier = Modifier
                .weight(2F)
                .fillMaxSize()
        ) {
            content()
        }

        Box(Modifier.weight(1F)) { TermsAndConditionsInfoFooter() }
    }
}