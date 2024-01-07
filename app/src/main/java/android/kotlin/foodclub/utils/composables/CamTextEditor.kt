package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Avenir
import android.kotlin.foodclub.config.ui.Orange
import android.kotlin.foodclub.config.ui.foodClubGreen
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun TextEditor(
    onDoneClicked: (String) -> Unit,
    onTextDeleted: () -> Unit
) {

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var isPaletteColor = remember { mutableStateOf(false) }
    var selectedFont by remember { mutableStateOf("" )}
    var fontWeight by remember { mutableStateOf(400f) }
    var textSize by remember { mutableStateOf(16f) }
    var textColor by remember { mutableStateOf(Color.White) }
    var textContent by remember { mutableStateOf<String?>(null) }
    var isDeleteButtonVisible by remember { mutableStateOf(false) }
    val fontList = listOf(
         stringResource(id =R.string.PlusJakartaSans) to R.font.plusjakartasans_regular,
         stringResource(id =R.string.AvenirBlack) to R.font.avenirblack,
         stringResource(id =R.string.AvenirBook) to R.font.avenirbook,
         stringResource(id =R.string.MontserratBold) to R.font.montserratbold,
         stringResource(id =R.string.MontserratMedium) to R.font.montserratmedium,
         stringResource(id =R.string.MontserratRegular) to R.font.montserratregular,
         stringResource(id =R.string.MontserratSemibold) to R.font.montserratsemibold,
         stringResource(id =R.string.RalewayExtraBold) to R.font.ralewayextrabold,
         stringResource(id =R.string.Satoshi) to R.font.satoshi
        //TODO add new fonts
    )
        val colorList = listOf(
        Color.White, Color.Black, Color.Blue, Color.Green,
        Color.Yellow, Orange, Color.Red, foodClubGreen,Color.Magenta,Color.Cyan
    )

        Box (
            Modifier
                .background(Color.Black)
                .fillMaxSize()

            ){
                Column(Modifier.align(Alignment.CenterStart)) {

                    VerticalSliderw(
                        value = textSize,
                        onValueChange = {
                            textSize = it
                        },
                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.dim_200))
                            .height(dimensionResource(id = R.dimen.dim_50))
                            .background(Color.Transparent)
                    )

                }

            IconButton(
                onClick = {
                   //TODO back navigation
                },
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.dim_16))
                    .size(dimensionResource(id = R.dimen.dim_48))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)))
                    .background(Color.DarkGray)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    painter = painterResource(id =R.drawable.back_arrow ) ,
                    contentDescription = stringResource(id =R.string.Toggle_Palette_Font),
                    tint = Color.White
                )
            }
            IconButton(
                onClick = {
                    isPaletteColor.value = !isPaletteColor.value
                },
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dim_80))
                    .clip(CircleShape)
                    .align(Alignment.TopCenter)
            ) {
                val imagePainter = if (isPaletteColor.value) {
                    painterResource(id = R.drawable.colorpaletteblank)
                } else {
                    painterResource(id = R.drawable.colorpalet)
                }
                Image(
                    painter = imagePainter,
                    contentDescription = stringResource(id =R.string.Toggle_Palette_Font),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dim_48))
                )
            }
            Text(
                text = stringResource(id = R.string.Done),
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.dim_16))
                    .wrapContentSize()
                    .padding(dimensionResource(id = R.dimen.dim_16))
                    .size(dimensionResource(id = R.dimen.dim_50))
                    .clickable {
                        //TODO Save changes
                    }
                    .align(Alignment.TopEnd),
                color = Color.White,
                fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
                fontFamily = Avenir,
                fontWeight = FontWeight.Bold
            )

            Box(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .align(Alignment.Center)
                        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }

                        .pointerInput(Unit) {
                            detectTransformGestures { _, panGesture, _, _ ->
                                offsetX += panGesture.x
                                offsetY += panGesture.y
                            }
                        }
                )
                {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(dimensionResource(id = R.dimen.dim_5))
                            .background(Color.Transparent)
                            .border(
                                border = BorderStroke(
                                    dimensionResource(id = R.dimen.dim_1),
                                    Color.White
                                ),
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .background(Color.Transparent)
                                .wrapContentSize()
                        ) {
                            val fontFamily = when (selectedFont) {
                                stringResource(id =R.string.PlusJakartaSans)-> FontFamily(Font(R.font.plusjakartasans_regular))
                                stringResource(id =R.string.AvenirBlack)-> FontFamily(Font(R.font.avenirblack))
                                stringResource(id =R.string.AvenirBook) -> FontFamily(Font(R.font.avenirbook))
                                stringResource(id =R.string.MontserratBold) -> FontFamily(Font(R.font.montserratbold))
                                stringResource(id =R.string.MontserratMedium) -> FontFamily(Font(R.font.montserratmedium))
                                stringResource(id =R.string.MontserratRegular) -> FontFamily(Font(R.font.montserratregular))
                                stringResource(id =R.string.MontserratSemibold) -> FontFamily(Font(R.font.montserratsemibold))
                                stringResource(id =R.string.RalewayExtraBold)-> FontFamily(Font(R.font.ralewayextrabold))
                                stringResource(id =R.string.Satoshi) -> FontFamily(Font(R.font.satoshi))
                                else -> FontFamily.Default
                            }
                            BasicTextField(
                                value = textContent.orEmpty(),
                                onValueChange = {
                                    textContent = it
                                },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(dimensionResource(id = R.dimen.dim_16)),
                                textStyle = LocalTextStyle.current.copy(
                                    color = textColor,
                                    fontSize = textSize.sp,
                                    fontWeight = FontWeight(fontWeight.toInt()),
                                    fontFamily = fontFamily,
                                ),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done,
                                ),
                                cursorBrush = SolidColor(foodClubGreen)
                            )

                        }
                    }
                    IconButton(
                        onClick = {
                            textContent = ""
                            isDeleteButtonVisible = false
                        },
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.dim_20))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_5)))
                            .background(Color.White)
                            .align(Alignment.BottomEnd)
                            .padding(dimensionResource(id = R.dimen.dim_2))
                    ) {
                        Icon(painter = painterResource(id = R.drawable.baseline_zoom_out_map_24), contentDescription = "", tint = Color.Black)
                    }
                    IconButton(
                        onClick = {
                            textContent = ""
                            isDeleteButtonVisible = false
                        },
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.dim_20))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_5)))
                            .background(Color.White)
                            .align(Alignment.TopEnd)
                            .padding(dimensionResource(id = R.dimen.dim_3))
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = stringResource(id =R.string.Delete), tint = Color.Black)
                    }
                    IconButton(
                        onClick = {
                            textContent = ""
                            isDeleteButtonVisible = false
                        },
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.dim_20))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_5)))
                            .background(Color.White)
                            .align(Alignment.TopStart)
                            .padding(dimensionResource(id = R.dimen.dim_3))
                    ) {
                        Icon(painter = painterResource(id = R.drawable.baseline_delete_24), contentDescription = stringResource(id =R.string.Delete), tint = Color.Black)
                    }
                }


            if (!isPaletteColor.value) {
                LazyRow(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.dim_16))
                        .align(Alignment.BottomCenter)
                ) {
                    items(fontList) { (fontName, fontResId) ->
                        FontButton(
                            stringResource(id =R.string.fontdisplay),
                            FontFamily(Font(fontResId)),
                            { selectedFont = fontName },
                            selectedFont == fontName
                        )
                    }
                }
            } else {
                LazyRow(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.dim_16))
                        .align(Alignment.BottomCenter)
                ) {
                    item {
                        Icon(
                            Icons.Default.Create,
                            contentDescription = stringResource(id =R.string.Custom_Color),
                            modifier = Modifier
                                .padding(dimensionResource(id = R.dimen.dim_4))
                                .clip(CircleShape)
                                .background(Color.White)
                                .border(
                                    dimensionResource(id = R.dimen.dim_4),
                                    Color.White,
                                    shape = CircleShape
                                )
                                .size(dimensionResource(id = R.dimen.dim_40)),
                            tint = Color.Black
                        )
                    }

                    colorList.forEach { color ->
                        item {
                            ColorButton(color) { textColor = color }
                        }
                    }
                }

            }
        }
}
@Composable
fun FontButton(
    fontName: String,
    font: FontFamily,
    onClickAction: () -> Unit,
    isSelected: Boolean
) {
    val backgroundColor = remember(isSelected) {
        if (isSelected) {
            Color.White
        } else {
            Color.Gray.copy(alpha = 0.5f)
        }
    }
    val textColor = remember(isSelected) {
        if (isSelected) {
            foodClubGreen
        } else {
            Color.White.copy(alpha = 0.5f)
        }
    }

    Text(
        text = fontName,
        color = textColor,
        fontFamily = font,
        fontSize =dimensionResource(id = R.dimen.fon_15).value.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .clickable {
                onClickAction()
            }
            .size(dimensionResource(id = R.dimen.dim_40))
            .background(backgroundColor, CircleShape)
            .padding(dimensionResource(id = R.dimen.dim_10))
    )
    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_5)))
}

@Composable
fun ColorButton(
    color: Color,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.dim_4))
            .clip(CircleShape)
            .background(color)
            .border(dimensionResource(id = R.dimen.dim_4), Color.White, shape = CircleShape)
            .size(dimensionResource(id = R.dimen.dim_40))
    ) {

    }

}
@Composable
fun VerticalSliderw(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 8f..72f,
    steps: Int = 100,
    onValueChangeFinished: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SliderColors = SliderDefaults.colors(Color.White, activeTrackColor = Color.White, inactiveTrackColor = Color.Gray)
) {
    Slider(
        colors = colors,
        interactionSource = interactionSource,
        onValueChangeFinished = onValueChangeFinished,
        steps = steps,
        valueRange = valueRange,
        enabled = enabled,
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .graphicsLayer {
                rotationZ = 270f
                transformOrigin = TransformOrigin(0f, 0f)
            }
            .padding(horizontal = dimensionResource(id = R.dimen.dim_8))
            .fillMaxHeight()
            .wrapContentSize()
    )


}
@Preview
@Composable
fun Preview()
{
    Box (
        Modifier.fillMaxSize()
    )
    {
        TextEditor(
            onDoneClicked = {
            //TODO
            },

            onTextDeleted = {
            //TODO
            }
        )
    }
}