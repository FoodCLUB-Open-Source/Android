package android.kotlin.foodclub.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressionBar(durationTime: Long) {
    var progress by remember { mutableStateOf(0f) }
    var timeDelay: Long = 10
    var iterations = durationTime / timeDelay
    var changeIterations = 0
    val progressionRate = (1f / iterations)
    val coroutineScope = rememberCoroutineScope()
    var isChecked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp),
        contentAlignment = Alignment.Center
    ) {

        Slider(
            value = progress,
            onValueChange = {
                progress = it
                changeIterations = (it / progressionRate).toInt()
            },
            modifier = Modifier
                .fillMaxWidth()
                .negativeMargin((-16).dp)
                .padding(0.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color.Transparent,
                activeTrackColor = colorGreen,
                inactiveTrackColor = colorGray,
            ),
            thumb = {
                Box(
                    modifier = Modifier
                        .size(0.dp)
                        .shadow(0.dp, clip = true)
                        .background(colorGreen)

                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        //Logic within this can be linked to the play button on the video
        Button(
            modifier = Modifier.padding(top = 130.dp),
            onClick = {
                isChecked = !isChecked

                coroutineScope.launch {
                    while (((iterations + changeIterations) >= 0) && isChecked) {
                        delay(timeDelay)
                        progress += progressionRate
                        if (progress > 1f) break
                    }
                }
            }
        ) {
            Text("Start")
        }
    }
}

// Function to override the default modifier properties of the slider. This was used to fix the width
fun Modifier.negativeMargin(margin: Dp): Modifier = this.then(
    object : LayoutModifier {
        override fun MeasureScope.measure(
            measurable: Measurable,
            constraints: Constraints
        ): MeasureResult {
            val offset = margin.roundToPx()
            val placeable = measurable.measure(
                constraints.offset(-offset, -offset)
            )
            return layout(placeable.width + offset * 2, placeable.height) {
                placeable.place(offset, 0)
            }
        }
    }
)
