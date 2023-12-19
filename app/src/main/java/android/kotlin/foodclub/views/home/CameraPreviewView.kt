package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.views.home.gallery.GalleryType
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    navController: NavController
) {
    val context = LocalContext.current

    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
            repeatMode = Player.REPEAT_MODE_ONE
            setMediaItem(MediaItem.fromUri(uri))
            playWhenReady = true
            prepare()
            addListener(object : Player.Listener {})
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dim_20))
    ) {

        Button(
            onClick = {
                navController.popBackStack()
            },
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_0)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.dim_30))
                .width(dimensionResource(id = R.dimen.dim_40))
                .height(dimensionResource(id = R.dimen.dim_40))

        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_close_24),
                contentDescription = stringResource(id = R.string.story),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.dim_25))
                    .height(dimensionResource(id = R.dimen.dim_25))
            )
        }

        Button(
            onClick = {
                navController.navigate(
                    route = if (state.contains(GalleryType.RECIPE.state)) "CREATE_RECIPE_VIEW" else Graph.HOME
                )
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(vertical =  dimensionResource(id = R.dimen.dim_40))
                .width(dimensionResource(id = R.dimen.dim_90))
                .height(dimensionResource(id = R.dimen.dim_55))
            ,
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_10)),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)

        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = stringResource(id = R.string.story),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.dim_20))
                    .height(dimensionResource(id = R.dimen.dim_20))
            )
        }
    }
}
