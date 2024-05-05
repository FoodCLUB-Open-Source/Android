 package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource


@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
) {
    var rotation by remember { mutableFloatStateOf(0f) }

    val infiniteTransition = rememberInfiniteTransition(label = stringResource(R.string.loading_indicator_label))

    val rotationValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ), label = stringResource(R.string.loading_indicator_rotation_label)
    )

    LaunchedEffect(rotationValue) {
        rotation = rotationValue
    }


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = rotation
                },
            painter = painterResource(id = R.drawable.loading_indicator),
            contentDescription = stringResource(R.string.loading_indicator_cd)
        )
    }
}