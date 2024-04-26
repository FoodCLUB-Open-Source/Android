package live.foodclub.utils.composables

import live.foodclub.R
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
import androidx.compose.runtime.getValue
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
    val dismissState = rememberDismissState(
        confirmValueChange = { dismiss ->
            if (dismiss == DismissValue.DismissedToStart) {
                onDismiss()
                true
            } else {
                false
            }
        }
    )

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

