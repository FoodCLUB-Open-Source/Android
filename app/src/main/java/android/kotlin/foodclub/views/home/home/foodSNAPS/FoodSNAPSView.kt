package android.kotlin.foodclub.views.home.home.foodSNAPS

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.models.snaps.MemoriesModel
import android.kotlin.foodclub.views.home.home.HomeState
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMotionApi::class,
    ExperimentalMaterialApi::class
)
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

    val SCREEN_HEIGHT_PERCENTAGE_EXCLUDING_BOTTOM_NAV = 0.94f
    var screenHeightMinusBottomNavItem =
        LocalConfiguration.current.screenHeightDp.dp * SCREEN_HEIGHT_PERCENTAGE_EXCLUDING_BOTTOM_NAV

    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.snapmotion_layout)
            .readBytes()
            .decodeToString()
    }
    val storyListData = state.storyList // use state.videoList to test

    val scrollState = rememberScrollState(initial = 0)
    val snapPagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { 8 }
    )
    val isDragged = snapPagerState.interactionSource.collectIsDraggedAsState()
    val extra = snapPagerState.interactionSource.interactions.collectAsState(initial = null)
    val swipableState = rememberSwipeableState(initialValue = SwipeDirection.NEUTRAL)



    val ANIMATION_DURATION_SHORT = 300
    val snapPagerFling = PagerDefaults.flingBehavior(
        state = snapPagerState,
        lowVelocityAnimationSpec = tween(
            easing = LinearEasing,
            durationMillis = ANIMATION_DURATION_SHORT
        ),
    )

    var visible by remember { mutableStateOf(true) }


    LaunchedEffect(key1 = swipableState.currentValue) {
        when(swipableState.currentValue) {
            SwipeDirection.UP -> {
                if (visible) {
                    visible = false
                } else {
                    snapPagerState.scrollToPage(1)
                }

                swipableState.snapTo(SwipeDirection.NEUTRAL)
            }

            SwipeDirection.DOWN -> {
                if (!visible) {
                    visible = true
                } else {
                    snapPagerState.scrollToPage(0)
                }

                swipableState.snapTo(SwipeDirection.NEUTRAL)
            }

            SwipeDirection.NEUTRAL -> {}
        }
    }


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
            AnimatedVisibility(visible = visible) {
                MemoriesView(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    state = state,
                    onShowMemoriesChanged = { showMemories ->
                        onShowMemoriesChanged(showMemories)},
                    updateCurrentMemoriesModel = { currentMemoriesModel = it }
                    )
            }

                VerticalPager(
                    state = snapPagerState,
                    flingBehavior = snapPagerFling,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Blue)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(color = Color.Green)
                            .then(
                                if (it == 0) {
                                    Modifier.swipeable(
                                        state = swipableState,
                                        anchors = mapOf(
                                            0f to SwipeDirection.NEUTRAL,
                                            -1f to SwipeDirection.UP,
                                            1f to SwipeDirection.DOWN
                                        ),
                                        thresholds = { _, _ -> FractionalThreshold(0.3f) },
                                        orientation = Orientation.Vertical,
                                    )
                                } else {
                                    Modifier
                                }
                            )
                    ) {
                        Text(text = "Page $it", modifier = Modifier.align(Alignment.Center))
                    }
                }
        }
    }
}

enum class SwipeDirection {
    UP, DOWN, NEUTRAL
}

