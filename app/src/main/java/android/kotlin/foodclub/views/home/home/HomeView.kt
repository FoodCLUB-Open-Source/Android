@file:JvmName("HomeViewKt")

package android.kotlin.foodclub.views.home.home

import android.annotation.SuppressLint
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.domain.models.snaps.MemoriesModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import android.kotlin.foodclub.viewModels.home.HomeViewModel
import android.kotlin.foodclub.views.home.SnapsView
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.draw.alpha
import androidx.media3.common.util.UnstableApi


@SuppressLint("StateFlowValueCalledInComposition")
@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    initialPage: Int? = 0,
    navController: NavHostController,
    triggerStoryView: () -> Unit,
    state: HomeState
) {
    var showIngredientSheet by remember { mutableStateOf(false) }
    val localDensity = LocalDensity.current

    val coroutineScope = rememberCoroutineScope()

    var screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.94f

    if (screenHeightMinusBottomNavItem <= 650.dp) {
        screenHeightMinusBottomNavItem = LocalConfiguration.current.screenHeightDp.dp * 0.96f
    }
    var showStories by remember {
        mutableStateOf(false)
    }
    var currentMemoriesModel by remember {
        mutableStateOf(MemoriesModel(listOf(), ""))
    }
    val systemUiController = rememberSystemUiController()

    val triggerIngredientBottomSheetModal: () -> Unit = {
        showIngredientSheet = !showIngredientSheet
    }

    var showFeedOnUI by remember { mutableStateOf(true) }
    var feedTransparency by remember { mutableFloatStateOf(1f) }
    var snapsTransparency by remember { mutableFloatStateOf(0.7f) }

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = Color.White
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(95.dp)
                .alpha(0.4f)
                .background(color = Color(0xFF424242))
        )
        if (showStories) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .alpha(feedTransparency)
                    .padding(start = 22.dp, bottom = 18.dp)
                    .clickable { showStories = !showStories }
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
        ) {
            Text(
                modifier = modifier
                    .alpha(feedTransparency)
                    .clickable {
                        showFeedOnUI = true
                        snapsTransparency = 0.7f
                        feedTransparency = 1f
                    },
                text = "Feed",
                fontFamily = Montserrat,
                fontSize = 18.sp,
                style = TextStyle(color = Color.White),
                lineHeight = 21.94.sp,
                fontWeight = if (showFeedOnUI) FontWeight.Bold else FontWeight.Medium
            )
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .alpha(0.7f),
                text = "|",
                fontFamily = Montserrat,
                fontSize = 18.sp,
                style = TextStyle(color = Color.LightGray),
                lineHeight = 21.94.sp
            )
            Text(
                modifier = modifier
                    .alpha(snapsTransparency)
                    .clickable {
                        feedTransparency = 0.7f
                        snapsTransparency = 1f
                        showFeedOnUI = false
                    },
                text = "Snaps",
                fontFamily = Montserrat,
                fontSize = 18.sp,
                style = TextStyle(color = Color.White),
                lineHeight = 21.94.sp,
                fontWeight = if (!showFeedOnUI) FontWeight.Bold else FontWeight.Medium
            )
        }
    }

    Column(
        modifier = Modifier.height(screenHeightMinusBottomNavItem)
    ) {
        if (showIngredientSheet) {
            HomeBottomSheetIngredients(triggerIngredientBottomSheetModal)
        }
        if (showFeedOnUI) {
            VideoPager(
                videoList = state.videoList,
                initialPage = initialPage,
                viewModel = viewModel,
                modifier = modifier,
                localDensity = localDensity,
                onInfoClick = triggerIngredientBottomSheetModal,
                coroutineScope = coroutineScope
            )
        } else {
            if (showStories) {
                SnapsView(
                    memoriesModel = currentMemoriesModel,
                    modifier = Modifier
                )
            } else {
                MemoriesView(
                    modifier = modifier,
                    memories = state.memories,
                    showStories = showStories,
                    currentMemoriesModel = currentMemoriesModel,
                    navController = navController
                )
            }
        }
    }
}


