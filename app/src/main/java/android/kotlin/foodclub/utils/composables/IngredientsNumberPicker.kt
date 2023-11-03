package android.kotlin.foodclub.utils.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditIngredientQuantityPicker(
    modifier: Modifier = Modifier,
    quantity: List<Int>,
    grammage: List<String>,
    types: List<String>,
    startIndex: Int = 0,
    listScrollCount: Int = Integer.MAX_VALUE
) {
    var selectedQuantityState by remember { mutableIntStateOf(quantity[0]) }
    var selectedGrammageState by remember { mutableStateOf(grammage[0]) }
    var selectedTypeState by remember { mutableStateOf(types[0]) }

    val quantityListState = rememberLazyListState(initialFirstVisibleItemIndex = startIndex)
    val grammageListState = rememberLazyListState(initialFirstVisibleItemIndex = startIndex)
    val typeListState = rememberLazyListState(initialFirstVisibleItemIndex = startIndex)

    val quantityListFling = rememberSnapFlingBehavior(lazyListState = quantityListState)
    val grammageListFling = rememberSnapFlingBehavior(lazyListState = grammageListState)
    val typeListFling = rememberSnapFlingBehavior(lazyListState = typeListState)

    val itemHeightPixels = remember { mutableStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels.value)

    // setting selected states
    LaunchedEffect(quantityListState) {
        snapshotFlow { quantityListState.firstVisibleItemIndex }
            .map { index ->
                val validIndex = index % quantity.size
                quantity.getOrNull(validIndex)
            }
            .filterNotNull()
            .distinctUntilChanged()
            .collect { selectedQuantityState = it }
    }

    LaunchedEffect(grammageListState) {
        snapshotFlow { grammageListState.firstVisibleItemIndex }
            .map { index ->
                val validIndex = index % grammage.size
                grammage.getOrNull(validIndex)
            }
            .filterNotNull()
            .distinctUntilChanged()
            .collect { selectedGrammageState = it }
    }
    LaunchedEffect(typeListState) {
        snapshotFlow { typeListState.firstVisibleItemIndex }
            .map { index ->
                val validIndex = index % types.size
                types.getOrNull(validIndex)
            }
            .filterNotNull()
            .distinctUntilChanged()
            .collect { selectedTypeState = it }
    }

    Box(modifier = modifier.fillMaxHeight(0.6f)) {
        Row(modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly) {
            LazyColumn(
                state = quantityListState,
                flingBehavior = quantityListFling,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(itemHeightDp * 5)
                    .weight(1f)
            ) {
                items(listScrollCount) { index ->
                    val currentItem = quantity[index % quantity.size]
                    val isSelected = currentItem == selectedQuantityState
                    val background = if (isSelected) Color.LightGray else Color.Transparent

                    PickerRow(
                        background = background,
                        currentItem = currentItem.toString(),
                        isSelected = isSelected,
                        itemHeightPixels = itemHeightPixels,
                        currentColumn = 1
                    )
                }
            }
            LazyColumn(
                state = grammageListState,
                flingBehavior = grammageListFling,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(itemHeightDp * 5)
                    .weight(1f)
            ) {
                items(listScrollCount) { index ->
                    val currentItem = grammage[index % grammage.size]
                    val isSelected = currentItem == selectedGrammageState
                    val background = if (isSelected) Color.LightGray else Color.Transparent

                    PickerRow(
                        background = background,
                        currentItem = currentItem,
                        isSelected = isSelected,
                        itemHeightPixels = itemHeightPixels,
                        currentColumn = 2
                    )
                }
            }
            LazyColumn(
                state = typeListState,
                flingBehavior = typeListFling,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(itemHeightDp * 5)
                    .weight(1f)
            ) {
                items(listScrollCount) { index ->
                    val currentItem = types[index % types.size]
                    val isSelected = currentItem == selectedTypeState
                    val background = if (isSelected) Color.LightGray else Color.Transparent

                    PickerRow(
                        background = background,
                        currentItem = currentItem,
                        isSelected = isSelected,
                        itemHeightPixels = itemHeightPixels,
                        currentColumn = 3
                    )
                }
            }
        }
    }
}

@Composable
private fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }

@Composable
private fun PickerRow(
    background: Color,
    currentItem: String,
    isSelected: Boolean,
    itemHeightPixels: MutableState<Int>,
    currentColumn: Int
){
    val cornerShape: RoundedCornerShape = when (currentColumn) {
        1 -> RoundedCornerShape(topStart = 5.dp,  bottomStart = 5.dp)
        3-> RoundedCornerShape(topEnd = 5.dp, bottomEnd = 5.dp)
        else -> RoundedCornerShape(0.dp)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged { size -> itemHeightPixels.value = size.height }
            .then(
                Modifier.background(
                    shape = cornerShape,
                    color = background
                ).padding(8.dp)
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currentItem,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp,
                color = if (isSelected) Color.Black else Color.Gray
            )
        }
    }
}