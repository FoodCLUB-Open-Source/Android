package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.navigation.graphs.Graph
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController

@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun CameraPreviewView(
    uri: String,
    state:String,
    navController: NavController // NEED NAV CONTROLLER
) {
    val context = LocalContext.current

    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
            repeatMode = Player.REPEAT_MODE_ONE
            setMediaItem(MediaItem.fromUri(uri))
            playWhenReady = true
            prepare()
            addListener(object : Player.Listener {
                override fun onRenderedFirstFrame() {
                    super.onRenderedFirstFrame()
                }
            })
        }
    }

    DisposableEffect(
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = false
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    ) {
        onDispose {
            exoPlayer.release()
        }
    }

    // NEXT BUTTON + VIDEO URI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                navController.navigate(
                    // URI ROUTE ON CLICK
                    route = if (state.contains("recipe")) "CREATE_RECIPE_VIEW" else Graph.HOME//"CreateRecipeRoute/${Uri.encode(uri)}"
                )
            },
            modifier = Modifier
                // ALIGNMENT
                .align(Alignment.BottomCenter)
        ) {
            Text(text = "Next")
        }
    }
}
