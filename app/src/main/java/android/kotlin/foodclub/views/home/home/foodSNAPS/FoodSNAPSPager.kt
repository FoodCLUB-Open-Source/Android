package android.kotlin.foodclub.views.home.home.foodSNAPS

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.enums.Reactions
import android.kotlin.foodclub.domain.models.home.VideoModel
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import okio.ByteString.Companion.encodeUtf8

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun FoodSNAPSPager(
    storyListData: List<VideoModel>,
    navController: NavHostController,
    showMemoriesReel: Boolean,
    changeMemoriesReelVisibility: (Boolean) -> Unit,
    selectedReaction: Reactions,
    clearSelectedReactions: () -> Unit,
    selectReaction: (Reactions) -> Unit,
    snapPagerState: PagerState,
    swipeableState: SwipeableState<SwipeDirection>
) {
    var isReactionsClickable by remember { mutableStateOf(selectedReaction != Reactions.ALL) }

    val ANIMATION_DURATION_SHORT = 300
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
                if (showMemoriesReel) {
                    changeMemoriesReelVisibility(false)
                } else {
                    snapPagerState.scrollToPage(1)
                }

                swipeableState.snapTo(SwipeDirection.NEUTRAL)
            }

            SwipeDirection.DOWN -> {
                if (!showMemoriesReel) {
                    changeMemoriesReelVisibility(true)
                } else {
                    snapPagerState.scrollToPage(0)
                }

                swipeableState.snapTo(SwipeDirection.NEUTRAL)
            }

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
            flingBehavior = snapPagerFling,
            beyondBoundsPageCount = 1,
            modifier = Modifier,
        ) {
            ReactionsOverlay(
                modifier = Modifier
                    .then(
                        if (it == 0) {
                            Modifier.swipeable(
                                state = swipeableState,
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
                    ),
                selectedReaction = selectedReaction,
                clearSelectedReaction = clearSelectedReactions,
                isReactionsClickable = { isClickable->
                    isReactionsClickable = isClickable
                }
            ) {
                FoodSNAPSPage(
                    index = it,
                    storyListData = storyListData,
                    showMemoriesReel = showMemoriesReel,
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