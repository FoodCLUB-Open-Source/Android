package android.kotlin.foodclub.views.home.home.foodSNAPS

import android.kotlin.foodclub.domain.enums.Reactions
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun ReactionsOverlay(
    modifier: Modifier,
    selectedReaction: Reactions,
    visible: Boolean,
    content : @Composable () -> Unit
) {
    val quantity = 22

    var visibility by remember { mutableStateOf(visible) }

    LaunchedEffect(key1 = visible) {
        delay(5000)
        visibility = false
    }

    Box(modifier = modifier) {
        content()

        AnimatedVisibility(
            visible = visibility,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(
                    durationMillis = MAX_ANIMATION_DURATION.toInt() - 300,
                    easing = LinearEasing,
                    delayMillis = 300
                )
            ),
            exit = ExitTransition.None
        ) {
            val particles = remember {
                calculateParticleParams(
                    quantity = quantity,
                    reaction = selectedReaction
                )
            }

            val transitionState = remember {
                MutableTransitionState(MIN_HEIGHT).apply {
                    targetState = MAX_HEIGHT
                }
            }
            val transition = updateTransition(transitionState, label = "height transition")
            val height by transition.animateInt(
                transitionSpec = {
                    tween(
                        durationMillis = MAX_ANIMATION_DURATION.toInt(),
                        easing = LinearOutSlowInEasing
                    )
                },
                label = "height animation of particles"
            ) { it }

            Layout(
                modifier = modifier.padding(bottom = 50.dp),
                content = {
                    for (i in 0 until quantity) {
                        Particle(particles[i])
                    }
                }
            ) { measurables, constraints ->
                val placeables = measurables.map { it.measure(constraints) }
                layout(constraints.maxWidth, height) {
                    placeables.forEachIndexed { index, placeable ->
                        val params = particles[index]
                        placeable.placeRelative(
                            x = (params.horizontalFraction * constraints.maxWidth).toInt() - constraints.maxWidth / 2,
                            y = (params.verticalFraction * height).toInt() - height / 2
                        )
                    }
                }
            }
        }
    }
}

private fun calculateParticleParams(
    quantity: Int,
    reaction: Reactions
): List<ParticleModel> {
    val random = Random(System.currentTimeMillis().toInt())
    val result = mutableListOf<ParticleModel>()
    for (i in 0 until quantity) {
        val verticalFraction = random.nextDouble(from = 0.0, until = 1.0).toFloat()
        val horizontalFraction = random.nextDouble(from = 0.0, until = 1.0).toFloat()

        val model =
            ParticleModel(
                verticalFraction = verticalFraction,
                horizontalFraction = horizontalFraction,
                initialScale = lerp(MIN_PARTICLE_SIZE, MAX_PARTICLE_SIZE, verticalFraction),
                duration = lerp(
                    MIN_ANIMATION_DURATION,
                    MAX_ANIMATION_DURATION,
                    verticalFraction
                ).toInt(),
                reaction = reaction
            )
        result.add(
            model
        )
    }

    return result

}

private fun lerp(start: Float, stop: Float, fraction: Float) =
    (start * (1 - fraction) + stop * fraction)

@Composable
private fun Particle(model: ParticleModel) {
    val transitionState = remember {
        MutableTransitionState(0.1f).apply {
            targetState = 0f
        }
    }

    val targetScale = remember { model.initialScale * TARGET_PARTICLE_SCALE_MULTIPLIER }

    val transition = updateTransition(transitionState, label = "particle transition")

    val alpha by transition.animateFloat(
        transitionSpec = {
            keyframes {
                durationMillis = model.duration
                0.1f at START_OF_ANIMATION
                1f at (model.duration * 0.1f).toInt()
                1f at (model.duration * 0.8f).toInt()
                0f at model.duration
            }
        },
        label = "alpha animation of particle"
    ) { it }

    val scale by transition.animateFloat(
        transitionSpec = {
            keyframes {
                durationMillis = model.duration
                model.initialScale at START_OF_ANIMATION
                model.initialScale at (model.duration * 0.7f).toInt()
                targetScale at model.duration
            }
        },
        label = "scale animation of particle"
    ) { it }

    Image(
        modifier = Modifier
            .size(PARTICLE_SIZE.dp)
            .scale(scale)
            .alpha(alpha),
        painter = painterResource(id = model.reaction.drawable),
        contentDescription = ""
    )
}

private const val TARGET_PARTICLE_SCALE_MULTIPLIER = 1.3f
private const val START_OF_ANIMATION = 0
private const val MIN_PARTICLE_SIZE = 1f
private const val MAX_PARTICLE_SIZE = 2.6f
private const val MIN_ANIMATION_DURATION = 1200f
private const val MAX_ANIMATION_DURATION = 1500f
private const val PARTICLE_SIZE = 20
private const val MIN_HEIGHT = 300
private const val MAX_HEIGHT = 800

data class ParticleModel(
    val verticalFraction: Float,
    val horizontalFraction: Float,
    val initialScale: Float,
    val duration: Int,
    val reaction: Reactions
)