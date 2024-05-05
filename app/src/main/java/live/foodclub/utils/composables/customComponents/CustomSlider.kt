package live.foodclub.utils.composables.customComponents

import live.foodclub.config.ui.Montserrat
import live.foodclub.config.ui.foodClubGreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomSlider(
    sliderWidth: Dp? = null,
    initialValue: Float = 0f,
    tickMarksEnabled: Boolean = false,
    maxValue: Float,
    onValueChange: (Int) -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(initialValue) }
    val density = LocalDensity.current
    var width by remember { mutableStateOf(sliderWidth ?: 0.dp) }
    Column(
        modifier = if(sliderWidth == null)
            Modifier.fillMaxWidth().onGloballyPositioned {
                width = with(density) { it.size.width.toDp() }
            }
        else
            Modifier.width(width)
    ) {
        if(width != 0.dp) {
            Slider(
                modifier = Modifier.width(width),
                value = sliderPosition,
                onValueChange = {
                    sliderPosition = it
                    onValueChange(it.toInt())
                },
                valueRange = 0f..maxValue,
                colors = SliderDefaults.colors(
                    thumbColor = foodClubGreen,
                    activeTrackColor = foodClubGreen,
                    inactiveTrackColor = Color(0xFFD9D9D9)
                ),
                steps = if(tickMarksEnabled) maxValue.toInt() - 1 else 0
            )
            Text(
                text = sliderPosition.toInt().toString(),
                fontSize = 16.sp,
                fontFamily = Montserrat,
                letterSpacing = TextUnit(-0.64f, TextUnitType.Sp),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.offset(
                    x = 5.dp + (width - 20.dp) * (sliderPosition / maxValue)
                )
            )
        }

    }

}