package live.foodclub.utils.composables

import live.foodclub.R
import live.foodclub.config.ui.foodClubGreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource

data class FabButtonItem(val iconRes: ImageVector, val label: String)


interface FabButtonMain {
    val iconRes: ImageVector
    val iconRotate: Float?
}

private class FabButtonMainImpl(
    override val iconRes: ImageVector,
    override val iconRotate: Float?
) : FabButtonMain

fun FabButtonMain(iconRes: ImageVector = Icons.Filled.Add, iconRotate: Float = 45f): FabButtonMain =
    FabButtonMainImpl(iconRes, iconRotate)

// FabButtonSub.kt
interface FabButtonSub {
    val iconTint: Color
    val backgroundTint: Color
}

private class FabButtonSubImpl(
    override val iconTint: Color,
    override val backgroundTint: Color
) : FabButtonSub

fun FabButtonSub(
    backgroundTint: Color = foodClubGreen,
    iconTint: Color = Color.White
): FabButtonSub = FabButtonSubImpl(iconTint, backgroundTint)

sealed class FabButtonState {
    data object Collapsed : FabButtonState()
    data object Expand : FabButtonState()

    fun isExpanded() = this == Expand

    fun toggleValue() = if (isExpanded()) {
        Collapsed
    } else {
        Expand
    }
}

@Composable
fun rememberMultiFabState() =
    remember { mutableStateOf<FabButtonState>(FabButtonState.Expand) }@Composable
fun MiniFabItem(
    item: FabButtonItem,
    fabOption: FabButtonSub,
    onFabItemClicked: (item: FabButtonItem) -> Unit
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(end = dimensionResource(id = R.dimen.dim_10)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dim_10)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.label,
            style = typography.labelSmall,
            color = Color.Black,
            modifier = Modifier
                .clip(RoundedCornerShape(size = dimensionResource(id = R.dimen.dim_8)))
                .background(Color.Gray.copy(alpha = 0.5f))
                .padding(all = dimensionResource(id = R.dimen.dim_8))
        )

        FloatingActionButton(
            onClick = { onFabItemClicked(item) },
            modifier = Modifier.size(dimensionResource(id = R.dimen.dim_40)),
            containerColor = fabOption.backgroundTint,
            contentColor = fabOption.iconTint
        ) {
            Icon(
                imageVector = item.iconRes,
                contentDescription = "stringResource(R.string.float_icon)",
                tint = fabOption.iconTint
            )
        }
    }
}

@Composable
fun MultiFloatingActionButton(
    modifier: Modifier = Modifier,
    items: List<FabButtonItem>,
    fabState: MutableState<FabButtonState> = rememberMultiFabState(),
    fabIcon: FabButtonMain,
    fabOption: FabButtonSub = FabButtonSub(),
    onFabItemClicked: (fabItem: FabButtonItem) -> Unit,
    stateChanged: (fabState: FabButtonState) -> Unit = {}
) {
    val rotation by animateFloatAsState(
        if (fabState.value == FabButtonState.Expand) {
            fabIcon.iconRotate ?: 0f
        } else {
            0f
        }, label = "stringResource(R.string.main_fab_rotation)"
    )

    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(
            visible = fabState.value.isExpanded(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            LazyColumn(
                modifier = Modifier.wrapContentSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy( dimensionResource(id = R.dimen.dim_15))
            ) {
                items(items.size) { index ->
                    MiniFabItem(
                        item = items[index],
                        fabOption = fabOption,
                        onFabItemClicked = onFabItemClicked
                    )
                }
                item {}
            }
        }

        FloatingActionButton(
            onClick = {
                fabState.value = fabState.value.toggleValue()
                stateChanged(fabState.value)
            },
            containerColor = fabOption.backgroundTint,
            contentColor = fabOption.iconTint
        ) {
            Icon(
                imageVector = fabIcon.iconRes,
                contentDescription = "stringResource(R.string.main_fab_button)",
                modifier = Modifier.rotate(rotation),
                tint = fabOption.iconTint
            )
        }
    }
}
