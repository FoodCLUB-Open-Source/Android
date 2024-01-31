package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSliderDiscrete(
    sliderWidth: Dp,
    initialValue: Float = 0f,
    maxValue: Float,
    onValueChange: (Int) -> Unit,
    tickSize: Int = 3
) {
    var sliderPosition by remember { mutableFloatStateOf(initialValue) }
    Box(modifier = Modifier.width(sliderWidth)) {
        val steps = maxValue.toInt() - 1
        val valueRange = 0f..maxValue
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.dim_10))
            .height(tickSize.dp)
        ) {
            val canvasWidth  = size.width
            val stepWidth = canvasWidth / (steps + 1)

            for (i in 0..steps) {
                val x = stepWidth * (i)
                drawCircle(
                    color = Color.Black,
                    radius = tickSize.dp.toPx(),
                    center = Offset(x, (size.height / 2))
                )
            }

            drawCircle(
                color = Color.Black,
                radius = tickSize.dp.toPx(),
                center = Offset((stepWidth*steps+stepWidth), (size.height / 2))
            )
        }
        val colors = SliderDefaults.colors(activeTrackColor = Color.Black, inactiveTrackColor = Color.Black)
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it ;
                onValueChange(it.toInt()) },
            steps = steps,
            modifier = Modifier
                .fillMaxWidth()
                .height(tickSize.dp),
            valueRange = valueRange,
            track = { sliderPositions ->
                SliderDefaults.Track(
                    modifier = Modifier.scale(scaleX = 1f, scaleY = 0.5f),
                    sliderPositions = sliderPositions,
                    colors = colors
                )
            },
            colors = SliderDefaults.colors(
                thumbColor = foodClubGreen
            )
        )
        Text(
            text = (sliderPosition).toInt().toString(),
            fontSize = dimensionResource(id = R.dimen.dim_16).value.sp,
            fontFamily = Montserrat,
            letterSpacing = TextUnit(-0.64f, TextUnitType.Sp),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .offset(
                    x = dimensionResource(id = R.dimen.dim_5) +
                        (sliderWidth - dimensionResource(id = R.dimen.dim_20)) * (sliderPosition / maxValue),
                    y= dimensionResource(id = R.dimen.dim_10)
                )
                .fillMaxWidth()
        )
    }
}