package android.kotlin.foodclub.views.home.home.foodSNAPS

import android.kotlin.foodclub.domain.models.snaps.MemoriesModel
import android.kotlin.foodclub.views.home.home.HomeState
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodSNAPSView(
    state: HomeState,
    modifier: Modifier = Modifier,
    onShowMemoriesChanged: (Boolean) -> Unit,
    showMemories: Boolean,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController
) {
    var currentMemoriesModel by remember {
        mutableStateOf(MemoriesModel(listOf(), ""))
    }
    // TODO can showMemories live inside FoodSNAPS rather than HomeView?
    val storyListData = state.storyList // use state.videoList to test

    var showMemoriesReel by remember { mutableStateOf(true) }

    BackHandler {
        if (showMemories) {
            onShowMemoriesChanged(!showMemories)
        } else {
            coroutineScope.launch {
                pagerState.animateScrollToPage(
                    page = 0,
                    animationSpec = tween(1, easing = LinearEasing)
                )
            }
        }
    }

    if (showMemories) {
        SnapsView(memoriesModel = currentMemoriesModel)
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            AnimatedVisibility(visible = showMemoriesReel) {
                MemoriesView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    storyListEmpty = storyListData.isEmpty(),
                    state = state,
                    onShowMemoriesChanged = { showMemories ->
                        onShowMemoriesChanged(showMemories)
                    },
                    updateCurrentMemoriesModel = { currentMemoriesModel = it }
                )
            }

            FoodSNAPSPager(
                storyListData = storyListData,
                navController = navController,
                showMemoriesReel = showMemoriesReel,
                changeMemoriesReelVisibility = { showMemoriesReel = it },
            )
        }
    }
}

