package android.kotlin.foodclub.utils.composables

import android.annotation.SuppressLint
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.kotlin.foodclub.R
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.views.home.ProgressionBar
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import android.kotlin.foodclub.utils.helpers.checkInternetConnectivity
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL
import java.util.concurrent.TimeUnit

fun extractThumbnailFromMedia(assetFileDesc: AssetFileDescriptor?, atTime: Int): Bitmap? {
    var bitmap: Bitmap? = null
    val retriever: MediaMetadataRetriever
    if (assetFileDesc != null && assetFileDesc.fileDescriptor.valid()) try {
        retriever = MediaMetadataRetriever()
        assetFileDesc.apply {
            retriever.setDataSource(
                fileDescriptor,
                startOffset,
                length
            )
        }

        bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            retriever.getScaledFrameAtTime(
                atTime.toLong(),
                MediaMetadataRetriever.OPTION_CLOSEST,
                1280,
                720
            )
        } else {
            retriever.getFrameAtTime(atTime.toLong())
        }
        assetFileDesc.close()
        retriever.release()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return bitmap
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class)
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun VideoScroller(
    video: VideoModel,
    pagerState: PagerState,
    pageIndex: Int,
    onSingleTap: (exoPlayer: ExoPlayer) -> Unit,
    onDoubleTap: (exoPlayer: ExoPlayer, offset: Offset) -> Unit,
    onVideoDispose: () -> Unit = {},
    onVideoGoBackground: () -> Unit = {}
) {
    val context = LocalContext.current
    var thumbnail by remember {
        mutableStateOf<Pair<Bitmap?, Boolean>>(Pair(null, true))
    }
    var isFirstFrameLoad = remember { false }

    var totalDuration by remember { mutableLongStateOf(0L) }
    var currentTime by remember { mutableLongStateOf(0L) }
    var bufferedPercentage by remember { mutableIntStateOf(0) }

    val job: Job?
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.IO) {
            val bm = if (video.videoLink.startsWith("asset:///")) extractThumbnailFromMedia(
                context.assets.openFd(video.videoLink.substring(9)), 1
            ) else {
                try {
                    BitmapFactory.decodeStream(
                        URL(video.thumbnailLink).openConnection().getInputStream()
                    )
                } catch (e: IOException) {
                    Log.d("VideoScroller", "Cannot fetch thumbnail. No connection")
                    null
                }
            }
            withContext(Dispatchers.Main) {
                thumbnail = thumbnail.copy(first = bm, second = thumbnail.second)
            }
        }
    }
    if (pagerState.settledPage == pageIndex) {
        val exoPlayer = remember(context) {
            ExoPlayer.Builder(context).build().apply {
                videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
                repeatMode = Player.REPEAT_MODE_ONE
                setMediaItem(MediaItem.fromUri(Uri.parse(video.videoLink)))
                playWhenReady = true
                prepare()
                addListener(object : Player.Listener {
                    override fun onRenderedFirstFrame() {
                        super.onRenderedFirstFrame()
                        isFirstFrameLoad = true
                        thumbnail = thumbnail.copy(second = false)

                    }
                })
            }
        }

        job = coroutineScope.launch {
            while (isActive) {
                currentTime = exoPlayer.currentPosition.coerceAtLeast(0L)
                delay(50)
            }
        }

        val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
        DisposableEffect(key1 = lifecycleOwner) {
            val listener =
                object : Player.Listener {
                    override fun onEvents(player: Player, events: Player.Events) {
                        super.onEvents(player, events)
                        totalDuration = player.duration.coerceAtLeast(0L)
                        currentTime = player.currentPosition.coerceAtLeast(0L)
                        bufferedPercentage = player.bufferedPercentage
                    }
                }
            val lifeCycleObserver = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_STOP -> {
                        exoPlayer.pause()
                        onVideoGoBackground()
                    }

                    Lifecycle.Event.ON_START -> exoPlayer.play()
                    else -> {}
                }
            }
            exoPlayer.addListener(listener)
            lifecycleOwner.lifecycle.addObserver(lifeCycleObserver)
            onDispose {
                exoPlayer.removeListener(listener)
                exoPlayer.release()
                lifecycleOwner.lifecycle.removeObserver(lifeCycleObserver)
            }
        }

        val playerView = remember {
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
        val isInternetConnected by rememberUpdatedState(newValue = checkInternetConnectivity(context))

        val brush = shimmerBrush()
        if (isInternetConnected) {
            Box(modifier = Modifier.fillMaxSize()) {
                AndroidView(factory = {
                    playerView
                }, modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        onSingleTap(exoPlayer)
                    }, onDoubleTap = { offset ->
                        onDoubleTap(exoPlayer, offset)
                    })
                })
                ProgressionBar(
                    totalDuration,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    totalDuration = { totalDuration },
                    currentTime = { currentTime },
                    onSeekChanged = { timeMs: Float ->
                        exoPlayer.seekTo(timeMs.toLong())
                    }
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush)
            )
        }

        DisposableEffect(exoPlayer) {
            onDispose {
                thumbnail = thumbnail.copy(second = true)
                exoPlayer.release()
                onVideoDispose()
                job.cancel()
            }
        }
    }

    if (thumbnail.second) {
        if (thumbnail.first != null) {
            AsyncImage(
                model = thumbnail.first,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Gray)
            )
        }
    }

}

@ExperimentalMaterial3Api
@Composable
fun BottomControls(
    modifier: Modifier = Modifier,
    totalDuration: () -> Long,
    currentTime: () -> Long,
    bufferPercentage: () -> Int,
    onSeekChanged: (timeMs: Float) -> Unit
) {
    val duration = remember(totalDuration()) { totalDuration() }
    val videoTime = remember(currentTime()) { currentTime() }
    val buffer = remember(bufferPercentage()) { bufferPercentage() }
    val interactionSource = remember { MutableInteractionSource() }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Column(
        modifier = modifier
            .background(Color.Transparent)
            .height(dimensionResource(id = R.dimen.dim_10))
            .fillMaxWidth(),
    ) {
        Slider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.dim_5)),
            value = videoTime.toFloat(),
            onValueChange = onSeekChanged,
            valueRange = 0f..duration.toFloat(),
            colors = SliderDefaults.colors(
                thumbColor = Color.Transparent,
                activeTrackColor = Color(android.graphics.Color.parseColor("#7EC60B"))
            ),
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = interactionSource,
                    thumbSize = DpSize(dimensionResource(id = R.dimen.dim_1), dimensionResource(id = R.dimen.dim_1)),
                    colors = SliderDefaults.colors(thumbColor = Color.Transparent)
                )
            },
        )
    }
}

fun Long.formatMinSec(): String {
    return if (this == 0L) {
        "..."
    } else {
        String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(this),
            TimeUnit.MILLISECONDS.toSeconds(this) -
                    TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(this)
                    )
        )
    }
}
