package live.foodclub.utils.composables

import live.foodclub.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * Before passing imageResource, gather it's width and height
 * Need to define image width and height to the function
 * Example Usage:
 *
 *    val image = R.drawable.`object`
 *    CreatorsViewDraggableImageText(
 *        imageResource = image,
 *        imageWidth = 230.dp,
 *        imageHeight = 40.dp,
 *        textFieldColors = colors,
 *        fontSize = 17.sp,
 *        fontFamily = montserratFamily,
 *        inputTextColor = Color.White
 *    )
 * */

@Composable
fun CreatorsViewDraggableImageText(
    imageResource: Int,
    imageWidth: Dp,
    imageHeight: Dp,
    fontSize: TextUnit,
    fontFamily: FontFamily,
    inputTextColor: Color
){
    val configuration = LocalConfiguration.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val isTyping = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val focused = remember { mutableStateOf(false) }

    val halfScreenWidth = (configuration.screenWidthDp / 2).dp
    val heightInDp = configuration.screenHeightDp.dp

    var offsetX by remember { mutableFloatStateOf(halfScreenWidth.value) }
    var offsetY by remember { mutableFloatStateOf(heightInDp.value) }

    val defaultTextFieldColors = TextFieldDefaults.colors(
        disabledTextColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
    )


    var defaultText by remember {
        mutableStateOf("")
    }
    val placeholder = stringResource(id = R.string.textfield_placeholder)

    Box(modifier = Modifier
        .background(Color.Transparent) // change to black for testing
        .focusRequester(focusRequester)
        .fillMaxSize()
        .clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            if (isTyping.value || focused.value) {
                focusManager.clearFocus()
                isTyping.value = false
                focused.value = false
            }
        }
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "",
            modifier = Modifier
                .height(imageHeight)
                .width(imageWidth)
                .graphicsLayer(
                    translationX = offsetX,
                    translationY = offsetY
                )
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        print(dragAmount)
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
        )

        Layout(
            content = {
                TextField(
                    value = defaultText,
                    onValueChange = { newText ->
                        defaultText = newText
                        isTyping.value = true
                    },
                    textStyle = TextStyle(color = Color.White, fontSize = fontSize, textAlign = TextAlign.Center),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
                    colors = defaultTextFieldColors,
                    placeholder = {
                        Text(text = placeholder, color = inputTextColor, fontSize = fontSize, fontFamily = fontFamily)
                    },
                    modifier = Modifier
                        .background(Color.Transparent)
                        .align(Alignment.Center)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                focused.value = focusState.isFocused
                            }
                        }
                )
            }
        ) { measurables, constraints ->
            val placeable = measurables.first().measure(constraints)

            // Calculate the center position based on the image's width
            val centerX = offsetX + imageWidth.toPx() / 2 - placeable.width / 2
            val textOffsetY = 50f

            layout(placeable.width, placeable.height) {
                placeable.place(
                    x = centerX.roundToInt(),
                    y = (offsetY + textOffsetY.dp.toPx()).roundToInt()
                )
            }
        }
    }
}
