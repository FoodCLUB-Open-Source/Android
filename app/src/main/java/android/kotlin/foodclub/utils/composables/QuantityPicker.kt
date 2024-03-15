package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.domain.enums.QuantityUnit
import android.kotlin.foodclub.domain.models.products.Ingredient
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

/**
 * Custom QuantityPicker composable for selecting quantity, unit, and type.
 *
 * @param ingredient The Ingredient object containing initial values.
 * @param units List of available QuantityUnit options.
 * @param onQuantityUnitSelected Callback when quantity, unit, or type is selected.
 * @param onIngredientUpdated Callback when the ingredient is updated.
 */

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class
)
@Composable
fun QuantityPicker(
    ingredient: Ingredient,
    units: List<QuantityUnit>,
    onQuantityUnitSelected: (quantity: Int, unit: QuantityUnit) -> Unit,
    onIngredientUpdated: (Ingredient) -> Unit
) {
    var quantity by remember { mutableStateOf(if(ingredient.quantity== 0)"" else ingredient.quantity.toString()) }

    var isError by remember {
        mutableStateOf(false)
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    val lightGrayAlpha = 0.2f

    var selectedUnit by remember { mutableStateOf(units[0]) }
    val lazyListContents = units.map { it.longName }.toMutableList().apply {
        add(0, "")
        add("")
    }

    val typeListState = rememberLazyListState()

    val typeListFling = rememberSnapFlingBehavior(lazyListState = typeListState)

    val itemHeightPixels = remember { mutableIntStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels.intValue)
    val TAG = "IngredientsNumberPicker"

    LaunchedEffect(typeListState) {
        snapshotFlow { typeListState.firstVisibleItemIndex }
            .map { index -> units[index] }
            .filterNotNull()
            .distinctUntilChanged()
            .collect {
                Log.i(TAG, "SELECTED type $it")
                selectedUnit = it
            }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dim_6))
    ) {

        Text(
            text = stringResource(id = R.string.quantity),
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.fon_22).value.sp,

            fontFamily = Montserrat,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_8))
        )

        Text(
            text = stringResource(id = R.string.quantity_picker_discription),
            color = Color.Gray,
            fontSize = dimensionResource(id = R.dimen.fon_14).value.sp,
            fontFamily = Montserrat,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dim_16))
        )
        BasicTextField(
            value = quantity,
            onValueChange = {
                val newText = it.trim()

                if (newText.isNotEmpty()) {
                    if (newText.toIntOrNull() != null &&
                        newText.toInt() < Int.MAX_VALUE
                    ) {

                        quantity = newText
                        isError = false

                    } else {
                        isError = true
                    }

                } else {
                    quantity = ""
                }
            },
            decorationBox = {
                TextFieldDefaults.DecorationBox(
                    value = quantity,
                    innerTextField = it,
                    enabled = true,
                    singleLine = false,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = remember { MutableInteractionSource() },
                    isError = isError,
                    supportingText = if (!isError) null else {
                        {
                            if (quantity.isDigitsOnly())
                            {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = stringResource(id = R.string.quantity_overflow_error_message),
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                            else
                            {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = stringResource(id = R.string.invalid_quantity_error_message),
                                    color = MaterialTheme.colorScheme.error
                                )
                            }

                        }
                    },
                    placeholder = {
                        Text(stringResource(id = R.string.quantity), color = Color.Gray)
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.LightGray.copy(alpha = lightGrayAlpha),
                        focusedContainerColor = Color.LightGray.copy(alpha = lightGrayAlpha),
                        cursorColor = foodClubGreen,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_save_alt_24),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {

                                }
                                .padding(dimensionResource(id = R.dimen.dim_8))
                        )
                    },
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_10))
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.dim_60))
                .padding(bottom = dimensionResource(id = R.dimen.dim_16)),
        )

        /*
        TextField(
            value = quantity,
            onValueChange = {
                val newText = it.trim()

                if (newText.isNotEmpty()) {
                    if (newText.toIntOrNull() != null &&
                        newText.toInt() < Int.MAX_VALUE) {

                        quantity = newText
                        isError = false

                    } else {
                        isError = true
                    }

                } else {
                    quantity = ""
                }
            },
            isError = isError,
            supportingText = if (!isError) null else {
                {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.quantity_overflow_error_message),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            placeholder = {
                Text(stringResource(id = R.string.quantity), color = Color.Gray)
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.LightGray.copy(alpha = lightGrayAlpha),
                focusedContainerColor = Color.LightGray.copy(alpha = lightGrayAlpha),
                cursorColor = foodClubGreen,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(id = R.dimen.dim_16)),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.outline_note_alt_24),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {

                        }
                        .padding(dimensionResource(id = R.dimen.dim_8))
                )
            }
        )
        
         */

        onQuantityUnitSelected(
            if (quantity == "0" || quantity == "") 1 else quantity.toInt(),
            selectedUnit
        )

        LazyColumn(
            state = typeListState,
            flingBehavior = typeListFling,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeightDp * 3)

        ) {
            items(lazyListContents.size) { index ->
                val currentItem = lazyListContents[index]
                val isSelected = currentItem == selectedUnit.longName

                PickerRow(
                    currentItem = currentItem,
                    isSelected = isSelected,
                    itemHeightPixels = itemHeightPixels,
                )
            }
        }

        Button(
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),

            colors = ButtonDefaults.buttonColors(
                containerColor = foodClubGreen,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_15)),
            onClick = {

                val updatedQuantity: Int =
                    if (quantity == "0" || quantity == "") 1 else quantity.toInt()
                ingredient.quantity = updatedQuantity
                ingredient.unit = selectedUnit
                onIngredientUpdated(ingredient)
            }
        ) {
            Text(
                text = stringResource(id = R.string.save),
                color = Color.White,
                fontFamily = Montserrat,
                fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

    }
}

@Composable
private fun PickerRow(

    currentItem: String,
    isSelected: Boolean,
    itemHeightPixels: MutableState<Int>,

    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged { size -> itemHeightPixels.value = size.height }
            .then(
                Modifier
                    .padding(dimensionResource(id = R.dimen.dim_8))
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)))
                .background(if (isSelected) foodClubGreen.copy(alpha = 0.1f) else Color.White)
                .padding(dimensionResource(id = R.dimen.dim_8))
        ) {
            Text(
                text = currentItem,
                maxLines = 1,
                color = if (isSelected) Color.DarkGray else Color.LightGray,
                fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontFamily = Montserrat,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditIngredientBottomModal(
    ingredient: Ingredient,
    onDismissRequest: (Boolean) -> Unit,
    onEdit: (Ingredient) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onDismissRequest(false)
        },
        modifier = Modifier.height(dimensionResource(id = R.dimen.dim_450)),
        containerColor = Color.White
    ) {
        val units = listOf(
            QuantityUnit.GRAMS,
            QuantityUnit.KILOGRAMS,
            QuantityUnit.LITERS,
            QuantityUnit.MILLILITERS
        )
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(
                    start = dimensionResource(id = R.dimen.dim_10),
                    end = dimensionResource(id = R.dimen.dim_10)
                )
        ) {
            QuantityPicker(
                ingredient,
                units = units,
                onQuantityUnitSelected = { quantity, unit ->
                    ingredient.quantity = quantity
                },
                onIngredientUpdated = {
                    onEdit(it)
                    onDismissRequest(false)
                },

                )
        }
    }
}





