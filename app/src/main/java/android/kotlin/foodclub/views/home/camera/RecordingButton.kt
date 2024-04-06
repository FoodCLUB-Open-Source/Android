package android.kotlin.foodclub.views.home.camera

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.darkGrey
import android.kotlin.foodclub.config.ui.foodClubGreen
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.dimensionResource

@Composable
fun RecordingClipsButton(
    isRecording: Boolean,
    onClipRemoved: () -> Unit = {},
    onClipAdded: () -> Unit = {},
    state: CameraState? = null,
    timeLimitMilliseconds: Int = 24000
) {
    val progress = remember {
        mutableStateOf(0f)
    }

    val clipArcs = remember {
        mutableStateListOf<Float>()
    }

    if (isRecording) {
        val currentProgress = (state?.totalMilliseconds ?: 0).toFloat() / timeLimitMilliseconds
        progress.value = currentProgress
    } else {
        if (progress.value != 0f) {
            val lastClip = clipArcs.lastOrNull() ?: 0f
            if (progress.value != lastClip) {
                clipArcs.add(progress.value)
                onClipAdded()
            }
        }
    }

    if (progress.value == 0f && clipArcs.isNotEmpty()) {
        clipArcs.clear()
        onClipRemoved()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(dimensionResource(id = R.dimen.dim_80))
    ) {
        // Draw background circle
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = Color.White,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 25f)
            )
        }

        // Draw recorded clip arcs
        Canvas(modifier = Modifier.fillMaxSize()) {
            clipArcs.forEach {
                drawArc(
                    color = Color.White,
                    startAngle = (360f * it) - 90f,
                    sweepAngle = 3f,
                    useCenter = false,
                    style = Stroke(width = 25f)
                )
            }
        }

        // Draw progress indicator
        CircularProgressIndicator(
            progress = progress.value,
            strokeWidth = dimensionResource(id = R.dimen.dim_5),
            color = foodClubGreen,
            modifier = Modifier.size(dimensionResource(id = R.dimen.dim_80))
        )

        // Draw inner circle
        Canvas(modifier = Modifier.size(dimensionResource(id = R.dimen.dim_60))) {
            drawCircle(color = darkGrey)
        }
    }
}
