package live.foodclub.presentation.ui.home.home.foodSNAPS

import live.foodclub.R
import live.foodclub.domain.enums.Reactions
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun ReactionsOverlay(
    modifier: Modifier,
    selectedReaction: Reactions,
    clearSelectedReaction: () -> Unit,
    isReactionsClickable: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    var isReactionSelected by remember { mutableStateOf(selectedReaction != Reactions.ALL) }
    val configuration = LocalConfiguration.current
    val targets = IntOffset(
        x = Random.nextInt(0 , configuration.screenWidthDp),
        y = configuration.screenHeightDp
    )

    val scope = rememberCoroutineScope()
    var particlesList by remember { mutableStateOf(listOf<Particle>()) }

    LaunchedEffect(key1 = selectedReaction){
        if (selectedReaction == Reactions.ALL) return@LaunchedEffect

        if (particlesList.isNotEmpty()){
            particlesList = emptyList()
        }

        for (i in 1..PARTICLE_QUANTITY){
            scope.launch {
                delay((i * DELAY_BETWEEN_PARTICLES).toLong())
                particlesList = ( particlesList + Particle(
                    i,
                    offsetX = Random.nextInt(0, configuration.screenWidthDp),
                    offsetY = Random.nextInt(0, (configuration.screenHeightDp * 0.1).toInt()),
                    selectedReaction
                )).toMutableList()
            }
        }
    }

    LaunchedEffect(key1 = isReactionSelected) {
        isReactionsClickable(false)
        clearSelectedReaction()
        delay(CLICKABLE_DELAY.toLong())
        isReactionsClickable(true)
    }

    LaunchedEffect(key1 = selectedReaction) {
        isReactionSelected = selectedReaction != Reactions.ALL
        if (!isReactionSelected){
            isReactionsClickable(true)
        }
    }

    Box(modifier = modifier) {
        content()

        if (particlesList.isNotEmpty()){
            ParticlesUI(
                particlesList,
                targets
            )
        }
    }
}
@Composable
fun ParticlesUI(
    particlesList: List<Particle>,
    targets: IntOffset
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        particlesList.forEach { particle ->
            key(particle.id) {
                val opacityAnimatable = remember { Animatable(0f) }
                val offsetXAnimatable = remember { Animatable(targets.x, Int.VectorConverter) }
                val offsetYAnimatable = remember { Animatable(targets.y, Int.VectorConverter) }

                SingleReactionContainer(
                    particle,
                    opacityAnimatable,
                    offsetXAnimatable,
                    offsetYAnimatable
                )
            }
        }
    }
}

@Composable
fun SingleReactionContainer(
    item: Particle,
    opacityAnimatable: Animatable<Float, AnimationVector1D>,
    offsetXAnimatable: Animatable<Int, AnimationVector1D>,
    offsetYAnimatable: Animatable<Int, AnimationVector1D>,
) {
    LaunchedEffect(item) {
        val opacity =
            async {
                opacityAnimatable.animateTo(1f, animationSpec = tween(OPACITY))
            }
        val offsetY =
            async {
                offsetYAnimatable.animateTo(
                    targetValue = item.offsetY - OFFSET_ABOVE_HEIGHT,
                    animationSpec = tween(PARTICLE_Y_SPEED)
                )
            }
        val offsetX =
            async {
                offsetXAnimatable.animateTo(
                    targetValue = item.offsetX,
                    animationSpec = tween(PARTICLE_X_SPEED)
                )
            }
        awaitAll(offsetX, offsetY, opacity)
        opacityAnimatable.animateTo(0f, animationSpec = tween(OPACITY_SPEED))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset {
                IntOffset(
                    x = offsetXAnimatable.value.dp.roundToPx(),
                    y = offsetYAnimatable.value.dp.roundToPx()
                )
            }
            .graphicsLayer {
                alpha = opacityAnimatable.value
            }
    ) {
        Image(
            painter = painterResource(id = item.reactions.drawable),
            contentDescription = "",
            Modifier.size(dimensionResource(id = R.dimen.dim_50))
        )
    }
}

private const val PARTICLE_QUANTITY = 15
private const val PARTICLE_Y_SPEED = 6000
private const val PARTICLE_X_SPEED = 6000
private const val OPACITY = 500
private const val OPACITY_SPEED = 2000
private const val OFFSET_ABOVE_HEIGHT = 500
private const val DELAY_BETWEEN_PARTICLES = 100
private const val CLICKABLE_DELAY = 6000

data class Particle(
    val id: Int,
    val offsetX: Int,
    val offsetY: Int,
    val reactions: Reactions
)