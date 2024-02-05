package android.kotlin.foodclub.views.home.home.foodSNAPS

import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.enums.Reactions
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateIntAsState
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun ReactionsOverlay(
    modifier: Modifier,
    selectedReaction: Reactions,
    clearSelectedReaction: () -> Unit,
    isReactionsClickable: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    var visibility by remember { mutableStateOf(selectedReaction != Reactions.ALL) }

    LaunchedEffect(key1 = visibility) {
        if (!visibility) {
            isReactionsClickable(true)
            return@LaunchedEffect
        }
        isReactionsClickable(false)
        delay(MAX_ANIMATION_DURATION.toLong() + DELAY_MILLIS)
        visibility = false
        clearSelectedReaction()
        isReactionsClickable(true)
    }

    LaunchedEffect(key1 = selectedReaction) {
        if (selectedReaction == Reactions.ALL) return@LaunchedEffect
        visibility = true
    }

    Box(modifier = modifier) {
        content()

        AnimatedVisibility(
            visible = visibility,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(
                    durationMillis = MAX_ANIMATION_DURATION.toInt() - DELAY_MILLIS,
                    easing = LinearEasing,
                    delayMillis = DELAY_MILLIS
                )
            ),
            exit = ExitTransition.None
        ) {
            val particles = remember { calculateParticleParams(reaction = selectedReaction) }

            val height by animateIntAsState(
                targetValue = MIN_HEIGHT,
                animationSpec = tween(
                    durationMillis = MAX_ANIMATION_DURATION.toInt(),
                    easing = LinearOutSlowInEasing
                ), label = stringResource(id = R.string.height_transition)
            )

            Layout(
                modifier = modifier.padding(bottom = dimensionResource(id = R.dimen.reactions_overlay_bottom_padding)),
                content = {
                    for (i in 0 until PARTICLE_QUANTITY) {
                        Particle(particles[i])
                    }
                }
            ) { measurables, constraints ->
                val placeables = measurables.map { it.measure(constraints) }
                layout(constraints.maxWidth, height) {
                    placeables.forEachIndexed { index, placeable ->
                        val params = particles[index]
                        placeable.placeRelative(
                            x = particleXPosition(params.horizontalFraction, constraints.maxWidth),
                            y = particleYPosition(params.verticalFraction, height)
                        )
                    }
                }
            }
        }
    }
}

private fun calculateParticleParams(
    reaction: Reactions
): List<ParticleModel> {
    val random = Random(System.currentTimeMillis().toInt())
    val result = mutableListOf<ParticleModel>()
    for (i in 0 until PARTICLE_QUANTITY) {
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

private fun particleXPosition(
    horizontalFraction: Float,
    maxWidth: Int
) = ((horizontalFraction * maxWidth).toInt() - (maxWidth / PARTICLE_X_MULTIPLIER)).coerceIn(
    minimumValue = PARTICLE_X_OFFSET,
    maximumValue = maxWidth - PARTICLE_X_OFFSET
)

private fun particleYPosition(
    verticalFraction: Float,
    height: Int
) = ((verticalFraction * height).toInt() + height / PARTICLE_Y_MULTIPLIER)

@Composable
private fun Particle(model: ParticleModel) {
    if (model.reaction == Reactions.ALL) return

    val transitionState = remember {
        MutableTransitionState(0.1f).apply {
            targetState = 0f
        }
    }

    val transition = updateTransition(
        transitionState,
        label = stringResource(id = R.string.particle_transition)
    )

    val alpha by transition.animateFloat(
        transitionSpec = {
            keyframes {
                durationMillis = model.duration
                0.1f at START_OF_ANIMATION
                1f at (model.duration * 0.1f).toInt()
                1f at (model.duration * 0.8f).toInt()
                0f at model.duration
            }
        }, label = ""
    ) { it }

    val targetScale = model.initialScale * TARGET_PARTICLE_SCALE_MULTIPLIER
    val scale by transition.animateFloat(
        transitionSpec = {
            keyframes {
                durationMillis = model.duration
                model.initialScale at START_OF_ANIMATION
                model.initialScale at (model.duration * 0.7f).toInt()
                targetScale at model.duration
            }
        }, label = ""
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
private const val MIN_HEIGHT = 800 // larger than max as its displacement from top of screen
private const val MAX_HEIGHT = 300
private const val PARTICLE_QUANTITY = 10
private const val PARTICLE_X_OFFSET = 50
private const val PARTICLE_X_MULTIPLIER = 10
private const val PARTICLE_Y_MULTIPLIER = 10
private const val DELAY_MILLIS = 0

data class ParticleModel(
    val verticalFraction: Float,
    val horizontalFraction: Float,
    val initialScale: Float,
    val duration: Int,
    val reaction: Reactions
)