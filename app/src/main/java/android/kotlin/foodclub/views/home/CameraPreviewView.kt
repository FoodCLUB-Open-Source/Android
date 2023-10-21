package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.navigation.Graph
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
            .padding(20.dp)
    ) {

        Button(
            onClick = {
                navController.popBackStack()
            },
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier
                .padding(vertical = 30.dp)
                .width(40.dp)
                .height(40.dp)
                //.blur(radius = 20.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)

        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_close_24),
                contentDescription = "Story",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
            )
        }

        Button(
            onClick = {
                navController.navigate(
                    // URI ROUTE ON CLICK
                    route = if (state.contains("recipe")) "CREATE_RECIPE_VIEW" else Graph.HOME//"CreateRecipeRoute/${Uri.encode(uri)}"
                )
            },
            modifier = Modifier
                // ALIGNMENT
                .align(Alignment.BottomEnd)
                .padding(vertical = 40.dp)
                .width(90.dp)
                .height(55.dp)
            ,
            contentPadding = PaddingValues(10.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)

        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = "Story",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
            )
        }
    }
}
