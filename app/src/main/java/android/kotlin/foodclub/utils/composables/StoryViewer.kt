package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.models.stories.StoryModel
import android.kotlin.foodclub.utils.helpers.TimeUtil
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp

@Composable
fun StoryView(
    storyEnabled: Boolean,
    storyDetails: StoryModel,
    callbackDisableStory: () -> Unit,
    currentOffset: IntOffset,
    modifier: Modifier = Modifier
) {

    AnimatedVisibility(
        visible = storyEnabled,
        enter =
        slideIn(animationSpec = tween(durationMillis = 250)) {
            IntOffset(
                -it.width / 2,
                -it.height / 2
            ).plus(currentOffset)
        }
                + scaleIn(animationSpec = tween(durationMillis = 250)),
        exit = slideOut { IntOffset(-it.width / 2, -it.height / 2).plus(currentOffset) }
                + scaleOut(animationSpec = tween(durationMillis = 250))
    ) {
        val density = LocalDensity.current
//        val anchors = with(density) {
//            DraggableState {
//                DragValue.Start at 0f
//                DragValue.End at 400.dp.toPx()
//            }
//        }
        // State to track the vertical offset
        val verticalOffset = remember { mutableFloatStateOf(0f) }

        // Create a draggable state with a lambda to handle drag changes
        val dragState = rememberDraggableState { delta ->
            // Update the vertical offset based on the drag delta
            verticalOffset.floatValue += delta
        }

//        val swipeState = remember {
//            AnchoredDraggableState(
//                initialValue = DragValue.Start,
//                anchors = anchors,
//                animationSpec = tween(
//                    durationMillis = 300,
//                    easing = FastOutSlowInEasing
//                ),
//                positionalThreshold = { distance: Float -> distance },
//                velocityThreshold = { with(density) { 125.dp.toPx() } },
//                confirmValueChange = {
//                    if (it == DragValue.End) {
//                        callbackDisableStory()
//                    }
//                    return@AnchoredDraggableState true
//                }
//            )
//        }

        Box(
            modifier = modifier
                .draggable(
                    state = dragState,
                    orientation = Orientation.Vertical
                )
                .offset { IntOffset(x = 0, y = verticalOffset.floatValue.toInt()) }
                .fillMaxSize()
        ) {
            Image(
                painter = storyDetails.storyPhoto,
                contentDescription = stringResource(id = R.string.foodsnaps),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = modifier.fillMaxSize()
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.dim_20),
                        vertical = dimensionResource(id = R.dimen.dim_60)
                    )
            ) {
                StoryInfo(
                    painter = storyDetails.authorPhotoPainter,
                    name = storyDetails.authorName,
                    time = storyDetails.timeCreated
                )
                Button(
                    onClick = { callbackDisableStory() },
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_12)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0x00FFFFFF).copy(alpha = 0.1f),
                        contentColor = Color(0x00FFFFFF).copy(alpha = 0.1f)
                    ),
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_4)),
                    modifier = modifier.size(dimensionResource(id = R.dimen.dim_40))
                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_close_24),
                        contentDescription = stringResource(id = R.string.close_story)
                    )
                }
            }
        }

    }
}

@Composable
fun StoryInfo(
    painter: Painter,
    name: String,
    time: Long,
    modifier: Modifier = Modifier
) {
    Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_10))) {
        Image(
            painter = painter,
            contentDescription = stringResource(id = R.string.author_photo),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(dimensionResource(id = R.dimen.dim_45))
                .clip(CircleShape)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_5), Alignment.CenterVertically),
            modifier = modifier.height(dimensionResource(id = R.dimen.dim_45))
        ) {
            Text(
                text = name,
                color = Color.White,
                fontSize = dimensionResource(id = R.dimen.fon_16).value.sp
            )
            Text(
                text = stringResource(id = R.string.time_ago, TimeUtil.getHoursAgoFromNow(time)),
                color = Color.White,
                fontSize = dimensionResource(id = R.dimen.fon_12).value.sp
            )
        }
    }
}