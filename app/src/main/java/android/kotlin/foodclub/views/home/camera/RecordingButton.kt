package android.kotlin.foodclub.views.home.camera

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.foodClubGreen
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp

@Composable
fun RecordingButton(isRecording: Boolean) {
    val progress by animateFloatAsState(
        targetValue = if (isRecording) 1f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(80.dp)
    ) {
        CircularProgressIndicator(
            progress = 1f,
            strokeWidth = 5.dp,
            color = Color.White,
            modifier = Modifier.size(80.dp)
        )
        CircularProgressIndicator(
            progress = progress,
            strokeWidth = 5.dp,
            color = foodClubGreen,
            modifier = Modifier.size(80.dp)
        )
        Canvas(modifier = Modifier.size(60.dp)) {
            drawCircle(color = Color(0xFFCACBCB))
        }
        // Record button
    }

}

@Composable
fun RecordingClipsButton(
    isRecording: Boolean,
    removeClip: Boolean = false,
    removeUpdate: (Boolean) -> Unit = {},
    addClip: Boolean = false,
    clipUpdate: (Boolean) -> Unit = {},
    state: CameraState? = null,
    timeLimitMilliseconds: Int = 6000
) {

    val (rememberProgress, progressUpdate) = remember {
        mutableFloatStateOf(0f)
    }

    val clipArcs = remember {
        mutableListOf<Float>()
    }

    if (removeClip) {
        if (clipArcs.size > 1) {
            clipArcs.removeAt(clipArcs.lastIndex)
            progressUpdate(clipArcs[clipArcs.lastIndex])
        } else if (clipArcs.isNotEmpty()) {
            clipArcs.removeAt(clipArcs.lastIndex)
            progressUpdate(0f)
        }

        removeUpdate(false)
    }

    val progress: Float = (state?.totalMilliseconds ?: 0).toFloat() / timeLimitMilliseconds

    /*
    val progress by animateFloatAsState(
        targetValue = if (isRecording) 1f else rememberProgress,
        animationSpec = if (isRecording) {infiniteRepeatable<Float>(
            animation = tween<Float>( 60000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )}
        else
        {
            snap(500)
        }
       , label = ""
    )

     */

    if (!isRecording) {
        if (progress != 0f && addClip) {
            clipArcs.add(progress) //Constantly adding to clip Arcs should only add at one point
            progressUpdate(progress)
            clipUpdate(false)
        }

    }



    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(dimensionResource(id = R.dimen.dim_80))
    ) {

        Canvas(modifier = Modifier.fillMaxSize())
        {
            drawArc(
                color = Color(0x55FFFFFF),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 25f)
            )

        }

        CircularProgressIndicator(
            progress = progress,
            strokeWidth = dimensionResource(id = R.dimen.dim_5),
            color = foodClubGreen,
            modifier = Modifier.size(dimensionResource(id = R.dimen.dim_80))
        )

        //val flt_25 = integerResource(id = R.integer.int_0pt5).toFloat()

        Canvas(modifier = Modifier.fillMaxSize())
        {
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

        Canvas(modifier = Modifier.size(dimensionResource(id = R.dimen.dim_60))) {
            drawCircle(color = Color(0xFFCACBCB))
        }
        // Record button
    }

}
