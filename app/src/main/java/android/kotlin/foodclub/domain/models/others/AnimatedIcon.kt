package android.kotlin.foodclub.domain.models.others

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

class AnimatedIcon(val iconResourceId: Int, val iconSize: Dp, private val density: Density) {
    var dpOffset = DpOffset.Unspecified
        private set

    var startAnimation = MutableStateFlow(false)
        private set

    val rotationAngle = (-10..10).random().toFloat()

    suspend fun animate(newOffset: Offset) {
        setNewOffset(newOffset)
        startAnimation.value = true
        delay(400)
        startAnimation.value = false
    }

    private fun setNewOffset(newOffset: Offset) {
        dpOffset = DpOffset(
            x = density.run { newOffset.x.toInt().toDp().plus(-iconSize/2) },
            y = density.run { newOffset.y.toInt().toDp().plus(-iconSize/2) }
        )
    }
}