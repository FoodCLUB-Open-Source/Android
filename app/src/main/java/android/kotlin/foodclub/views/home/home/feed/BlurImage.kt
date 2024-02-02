package android.kotlin.foodclub.views.home.home.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.dp

@Composable
fun BlurImage(
    content: @Composable () -> Unit
) {
    Box {
        content()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    val path = Path()
                    path.addRoundRect(
                        RoundRect(
                            left = size.width / 1.25f, top = size.height / 1.425f,
                            right = size.width / 1.05f, bottom = size.height / 1.27f,
                            topLeftCornerRadius = CornerRadius(100f, 100f),
                            topRightCornerRadius = CornerRadius(100f, 100f),
                            bottomLeftCornerRadius = CornerRadius(100f, 100f),
                            bottomRightCornerRadius = CornerRadius(100f, 100f),
                        ),
                    )
                    clipPath(path) {
                        this@drawWithContent.drawContent()
                    }
                }
                .blur(
                    radiusX = 10.dp, radiusY = 10.dp
                )
        ) {
            content()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .drawWithContent {
                    val path = Path()
                    path.addRoundRect(
                        RoundRect(
                            left = size.width / 1.05f, top = size.height / 1.255f,
                            right = size.width / 1.25f, bottom = size.height / 1.11f,
                            topLeftCornerRadius = CornerRadius(100f, 100f),
                            topRightCornerRadius = CornerRadius(100f, 100f),
                            bottomLeftCornerRadius = CornerRadius(100f, 100f),
                            bottomRightCornerRadius = CornerRadius(100f, 100f),
                        ),
                    )
                    clipPath(path) {
                        this@drawWithContent.drawContent()
                    }
                }
                .blur(
                    radiusX = 10.dp, radiusY = 10.dp
                )
        ) {
            content()
        }
    }
}