package android.kotlin.foodclub.views.home.home

import android.kotlin.foodclub.domain.models.home.VideoModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import coil.compose.rememberAsyncImagePainter

@Composable
fun SnapStoryView(
    storyListData: List<VideoModel>,
    modifier: Modifier
) {
    var sizeImage by remember { mutableStateOf(IntSize.Zero) }
    val gradient = Brush.verticalGradient(
        colors = listOf(Color.White, Color.Transparent),
        startY = 0f,
        endY =  sizeImage.height.toFloat()/2,
        tileMode = TileMode.Clamp
    )

    Box(
        modifier = modifier
    ){
        Image(
            painter = rememberAsyncImagePainter(model = storyListData[0].thumbnailLink),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    sizeImage = it.size
                }
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(gradient)
        )


    }
}