package live.foodclub.presentation.ui.home.home.foodSNAPS

import live.foodclub.R
import live.foodclub.domain.enums.Reactions
import live.foodclub.domain.models.home.VideoModel
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import okio.ByteString.Companion.encodeUtf8

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun FoodSNAPSPager(
    storyListData: List<VideoModel>,
    navController: NavHostController,
    //showMemoriesReel: Boolean,
    //changeMemoriesReelVisibility: (Boolean) -> Unit,
    selectedReaction: Reactions,
    clearSelectedReactions: () -> Unit,
    selectReaction: (Reactions) -> Unit,
    snapPagerState: PagerState,
) {
    var isReactionsClickable by remember { mutableStateOf(selectedReaction != Reactions.ALL) }
    val swipeableState = rememberSwipeableState(initialValue = SwipeDirection.NEUTRAL)

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        //changeMemoriesReelVisibility(true)
        refreshing = false
    }

    val inSnapView = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = ::refresh
    )

    val snapPagerFling = PagerDefaults.flingBehavior(
        state = snapPagerState,
        lowVelocityAnimationSpec = tween(
            easing = LinearEasing,
            durationMillis = ANIMATION_DURATION_SHORT
        )
    )

    LaunchedEffect(key1 = swipeableState.currentValue) {
        when (swipeableState.currentValue) {
            SwipeDirection.UP -> {
//                if (showMemoriesReel) {
//                    changeMemoriesReelVisibility(false)
//                }
                swipeableState.snapTo(SwipeDirection.NEUTRAL)
            }

            SwipeDirection.DOWN -> {}

            SwipeDirection.NEUTRAL -> {}
        }
    }

    if (storyListData.isEmpty()) {
        TapToSnapDialog(
            modifier = Modifier
                .clickable {
                    navController.navigate("CAMERA_VIEW/${"story".encodeUtf8()}")
                }
                .padding(horizontal = dimensionResource(id = R.dimen.dim_15))
                .aspectRatio(0.9f, true)
        )
    } else {
        VerticalPager(
            state = snapPagerState,
            modifier = Modifier
                .pullRefresh(inSnapView)
                .fillMaxSize(),
            flingBehavior = snapPagerFling
        ) {pagerIdx ->
            ReactionsOverlay(
                modifier = Modifier,
//                    .then(
//                        if (pagerIdx == 0 /* && showMemoriesReel */) {
//                            Modifier.swipeable(
//                                state = swipeableState,
//                                anchors = mapOf(
//                                    0f to SwipeDirection.NEUTRAL,
//                                    -1f to SwipeDirection.UP
//                                ),
//                                thresholds = { _, _ -> FractionalThreshold(0.3f) },
//                                orientation = Orientation.Vertical,
//                            )
//                        } else {
//                            Modifier
//                        }
//                    )
                selectedReaction = selectedReaction,
                clearSelectedReaction = clearSelectedReactions,
                isReactionsClickable = { isClickable ->
                    isReactionsClickable = isClickable
                }
            ) {
                FoodSNAPSPage(
                    index = pagerIdx,
                    storyListData = storyListData,
                    //showMemoriesReel = showMemoriesReel,
                    selectReaction = selectReaction,
                    reactionsClickable = isReactionsClickable
                )
            }
        }
    }
}

enum class SwipeDirection {
    UP, DOWN, NEUTRAL
}
private const val ANIMATION_DURATION_SHORT = 300