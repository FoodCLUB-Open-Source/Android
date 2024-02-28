package android.kotlin.foodclub.views.home.home.foodSNAPS

import android.kotlin.foodclub.domain.enums.Reactions
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
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
    var visibility by remember { mutableStateOf(selectedReaction != Reactions.ALL) }
    val configuration = LocalConfiguration.current
    val targets = IntOffset(
        x = Random.nextInt(0 , configuration.screenWidthDp),
        y = configuration.screenHeightDp
    )

    val scope = rememberCoroutineScope()
    val particlesList = remember { mutableStateListOf<Particle>() }

    LaunchedEffect(selectedReaction){
        if (selectedReaction == Reactions.ALL) return@LaunchedEffect

        for (i in 1..PARTICLE_QUANTITY){
            scope.launch {
                delay((i * DELAY_BETWEEN_PARTICLES).toLong())
                particlesList.add(
                    Particle(
                        i,
                        offsetX = Random.nextInt(0, configuration.screenWidthDp),
                        offsetY = Random.nextInt(0, (configuration.screenHeightDp * 0.1).toInt()),
                        selectedReaction
                    )
                )
            }
        }
    }

    LaunchedEffect(key1 = visibility) {
        if (!visibility) {
            isReactionsClickable(true)
            return@LaunchedEffect
        }
        isReactionsClickable(false)
        visibility = false
        clearSelectedReaction()
        isReactionsClickable(true)
    }
    LaunchedEffect(key1 = selectedReaction) {
        if (selectedReaction == Reactions.ALL) return@LaunchedEffect
        visibility = true
    }

    Box(modifier = modifier.fillMaxSize()) {
        content()

        MainUI(
            particlesList,
            targets
        ) { particleToRemove ->
            particlesList.filter { currentParticle ->
                currentParticle.id != particleToRemove.id
            }
        }
    }
}
@Composable
fun MainUI(
    particlesList: List<Particle>,
    targets: IntOffset,
    onAnimationFinished: (Particle) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (particlesList.isNotEmpty()){
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
                    ) {
                        onAnimationFinished(it)
                    }
                }
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
    onAnimationFinished: (Particle) -> Unit
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
        onAnimationFinished(item)
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
            Modifier.size(40.dp)
        )
    }
}

private const val PARTICLE_QUANTITY = 5
private const val PARTICLE_Y_SPEED = 6000
private const val PARTICLE_X_SPEED = 6000
private const val OPACITY = 500
private const val OPACITY_SPEED = 2000
private const val OFFSET_ABOVE_HEIGHT = 500
private const val X_OFFSET_REDUCTION = 500
private const val DELAY_BETWEEN_PARTICLES = 100

data class Particle(
    val id: Int,
    val offsetX: Int,
    val offsetY: Int,
    val reactions: Reactions
)