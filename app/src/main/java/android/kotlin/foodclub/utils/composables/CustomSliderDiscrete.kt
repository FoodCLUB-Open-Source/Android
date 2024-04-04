package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSliderDiscrete(
    sliderWidth: Dp? = null,
    startValue: Float = 1f,
    maxValue: Float,
    onValueChange: (Int) -> Unit,
    tickSize: Int = 3,
    inactiveTrackColor: Color = Color.Black,
    stepsColor: Color = Color.Black
) {
    var sliderPosition by remember { mutableFloatStateOf(startValue) }
    val density = LocalDensity.current
    var width by remember { mutableStateOf(sliderWidth ?: 0.dp) }
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = if(sliderWidth == null)
            Modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    width = with(density) { it.size.width.toDp() }
                }
        else
            Modifier.width(width)
    ) {
        val steps = maxValue.toInt() - 1
        val valueRange = startValue..maxValue
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.dim_10))
            .height(tickSize.dp)
        ) {
            val canvasWidth  = size.width
            val stepWidth = canvasWidth / (steps + 1)
            for (i in 0..(steps + 1) ) {
                val x = stepWidth * (i)
                drawCircle(
                    color = if(i < sliderPosition) foodClubGreen else stepsColor,
                    radius = tickSize.dp.toPx(),
                    center = Offset(x, (size.height / 2))
                )
            }
        }
        val colors = SliderDefaults.colors(
            activeTrackColor = foodClubGreen,
            inactiveTrackColor = inactiveTrackColor,
            activeTickColor = foodClubGreen
        )
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                onValueChange(it.roundToInt())
                            },
            steps = steps,
            modifier = Modifier
                .fillMaxWidth()
                .height((tickSize / 2).dp),
            valueRange = valueRange,
            track = { sliderPositions ->
                SliderDefaults.Track(
                    modifier = Modifier.scale(scaleX = 1f, scaleY = 0.5f),
                    sliderPositions = sliderPositions,
                    colors = colors
                )
            },
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = interactionSource,
                    modifier = Modifier.scale(scale = 0.8f),
                    colors = SliderDefaults.colors(thumbColor = foodClubGreen)
                )
            },

        )
        Text(
            text = (sliderPosition).roundToInt().toString(),
            fontSize = dimensionResource(id = R.dimen.dim_16).value.sp,
            fontFamily = Montserrat,
            letterSpacing = TextUnit(-0.64f, TextUnitType.Sp),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .offset(
                    x = dimensionResource(id = R.dimen.dim_5) +
                            (width - dimensionResource(id = R.dimen.dim_20)) * ((sliderPosition - startValue) / (maxValue - startValue)),
                    y = dimensionResource(id = R.dimen.dim_10)
                )
                .fillMaxWidth(),
            color = Color.Black
        )
    }
}