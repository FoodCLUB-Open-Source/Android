package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismissContainer(
    onDismiss: () -> Unit,
    dismissContent: @Composable (Modifier) -> Unit
) {
    var notSwiped by remember { mutableStateOf(false) }
    val dismissState = rememberDismissState(
        confirmValueChange = { dismiss ->
            if (dismiss == DismissValue.DismissedToEnd) notSwiped =
                !notSwiped
            dismiss != DismissValue.DismissedToEnd
        }
    )

    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
        LaunchedEffect(key1 = true) {
            onDismiss()
            dismissState.reset()
        }
    } else {
        LaunchedEffect(key1 = true) {
            dismissState.reset()
        }
    }

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        background = {
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.Default -> Color.White
                    DismissValue.DismissedToEnd -> Color.White
                    DismissValue.DismissedToStart -> Color.Red
                }, label = ""
            )
            val alignment = Alignment.CenterEnd
            val icon = Icons.Default.Delete

            val scale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f,
                label = ""
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = dimensionResource(id = R.dimen.dim_20)),
                contentAlignment = alignment
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier.scale(scale),
                    tint = Color.White
                )
            }
        },
        dismissContent = {
            dismissContent(Modifier)
        }
    )
}

