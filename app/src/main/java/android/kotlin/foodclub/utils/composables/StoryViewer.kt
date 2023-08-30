package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.data.models.StoryModel
import android.kotlin.foodclub.utils.enums.DragValue
import android.kotlin.foodclub.utils.helpers.TimeUtil
import android.util.Size
import android.util.StateSet
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoryView(storyEnabled: Boolean, storyDetails: StoryModel,
              callbackDisableStory: () -> Unit, currentOffset: IntOffset,
              modifier: Modifier = Modifier
) {

    AnimatedVisibility(
        visible = storyEnabled,
        enter =
        // slideInVertically(initialOffsetY = { (1.5 * it).toInt() } )
        slideIn(animationSpec = tween(durationMillis = 250)) { IntOffset(- it.width / 2, - it.height / 2).plus(currentOffset) }
                + scaleIn(animationSpec = tween(durationMillis = 250)),
        exit = slideOut(){ IntOffset(- it.width / 2, - it.height / 2).plus(currentOffset) }
                + scaleOut(animationSpec = tween(durationMillis = 250))
    ) {
        val density = LocalDensity.current
        val anchors = with(density) {
            DraggableAnchors {
                DragValue.Start at 0f
                DragValue.End at 400.dp.toPx()
            }
        }
        val swipeState = remember {
            AnchoredDraggableState(
                initialValue = DragValue.Start,
                anchors = anchors,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                ),
                positionalThreshold = { distance: Float -> distance },
                velocityThreshold = { with(density) { 125.dp.toPx() } },
                confirmValueChange = {
                    if(it == DragValue.End) { callbackDisableStory() }
                    return@AnchoredDraggableState true
                }
            )
        }

        Box(
            modifier = Modifier
                .anchoredDraggable(
                    state = swipeState,
                    orientation = Orientation.Vertical,
                )
                .offset {
                    IntOffset(
                        x = 0,
                        y = (swipeState.requireOffset() * 0.2).toInt()
                    )
                }
                .fillMaxSize()
        ) {
            Image(
                painter = storyDetails.storyPhoto,
                contentDescription = "foodsnap",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 60.dp)
            ) {
                StoryInfo(
                    painter = storyDetails.authorPhotoPainter,
                    name = storyDetails.authorName,
                    time = storyDetails.timeCreated
                )
                Button(
                    onClick = { callbackDisableStory() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0x00FFFFFF).copy(alpha = 0.1f),
                        contentColor = Color(0x00FFFFFF).copy(alpha = 0.1f)
                    ),
                    contentPadding = PaddingValues(4.dp),
                    modifier = Modifier.size(40.dp)
                ) {
                    Image(painter = painterResource(R.drawable.baseline_close_24), contentDescription = "close story")
                }
            }
        }

    }
}

@Composable
fun StoryInfo(painter: Painter, name: String, time: Long, modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Image(
            painter = painter,
            contentDescription = "author photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
        )

        Column(verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
            modifier = Modifier.height(45.dp)) {
            Text(
                text = name,
                color = Color.White,
                fontSize = 16.sp
            )
            Text(
                text = TimeUtil.getHoursAgoFromNow(time) + " ago",
                color = Color.White,
                fontSize = 12.sp
            )
        }
    }
}