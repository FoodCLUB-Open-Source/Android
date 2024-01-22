package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomSlider(
    sliderWidth: Dp,
    initialValue: Float = 0f,
    maxValue: Float,
    onValueChange: (Int) -> Unit,
    textOnTop: Boolean = true,
    discreteSlider : Boolean = false) {
    var sliderPosition by remember { mutableFloatStateOf(initialValue) }
    Column(modifier = Modifier.width(sliderWidth)) {
        val steps = maxValue.toInt() - 1
        if(textOnTop) {
            Text(
                text = sliderPosition.toInt().toString(),
                fontSize = dimensionResource(id = R.dimen.dim_16).value.sp,
                fontFamily = Montserrat,
                letterSpacing = TextUnit(-0.64f, TextUnitType.Sp),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.offset(
                    x = dimensionResource(id = R.dimen.dim_5) +
                            (sliderWidth - dimensionResource(id = R.dimen.dim_20)) * (sliderPosition / maxValue)
                )
            )
        }
        Slider(
            modifier = Modifier.width(sliderWidth),
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                onValueChange(it.toInt())
            },
            steps = if(discreteSlider) steps else 0,
            valueRange = 0f..maxValue,
            colors = SliderDefaults.colors(
                thumbColor = foodClubGreen,
                activeTrackColor = colorResource(R.color.custom_slider_track_color),
                inactiveTrackColor = colorResource(R.color.custom_slider_track_color),
                activeTickColor = Color.Black,
                inactiveTickColor = Color.Black
            ),
        )
        if(!textOnTop) {
            Text(
                text = sliderPosition.toInt().toString(),
                fontSize = dimensionResource(id = R.dimen.dim_16).value.sp,
                fontFamily = Montserrat,
                letterSpacing = TextUnit(-0.64f, TextUnitType.Sp),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.offset(
                    x = dimensionResource(id = R.dimen.dim_5) +
                            (sliderWidth - dimensionResource(id = R.dimen.dim_20)) * (sliderPosition / maxValue)
                )
            )
        }
    }

}