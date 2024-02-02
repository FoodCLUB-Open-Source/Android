package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.foodClubGreen
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import kotlinx.coroutines.delay

@Composable
fun VideoProgressBar(
    modifier: Modifier = Modifier,
    currentTime: () -> Long,
    totalDuration: () -> Long,
) {
    var progress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(currentTime(), totalDuration()) {
        while (true) {
            val currentProgress = if (totalDuration() > 0) {
                (currentTime().toFloat() / totalDuration().toFloat()).coerceIn(0f, 1f)
            } else {
                0f
            }

            progress = currentProgress
            delay(50L)
        }
    }

    AnimatedProgressBar(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.dim_5))
            .padding(dimensionResource(id = R.dimen.dim_0)),
        progress = progress
    )
}

@Composable
fun AnimatedProgressBar(
    modifier: Modifier = Modifier,
    progress: Float
) {
    val animatedProgress = remember { Animatable(progress) }

    LaunchedEffect(progress) {
        animatedProgress.animateTo(progress)
    }

    LinearProgressIndicator(
        progress = animatedProgress.value,
        modifier = modifier,
        color = foodClubGreen
    )
}
