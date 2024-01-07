package android.kotlin.foodclub.utils.composables

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomSlider(sliderWidth: Dp, initialValue: Float = 0f, maxValue: Float, onValueChange: (Int) -> Unit) {
    var sliderPosition by remember { mutableStateOf(initialValue) }
    Column(modifier = Modifier.width(sliderWidth)) {
        Text(
            text = sliderPosition.toInt().toString(),
            fontSize = 16.sp,
            fontFamily = Montserrat,
            letterSpacing = TextUnit(-0.64f, TextUnitType.Sp),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.offset(
                x = 5.dp + (sliderWidth - 20.dp) * (sliderPosition / maxValue)
            )
        )
        Slider(
            modifier = Modifier.width(sliderWidth),
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                onValueChange(it.toInt())
            },
            valueRange = 0f..maxValue,
            colors = SliderDefaults.colors(
                thumbColor = foodClubGreen,
                activeTrackColor = Color(0xFFD9D9D9),
                inactiveTrackColor = Color(0xFFD9D9D9)
            ),
        )
    }

}