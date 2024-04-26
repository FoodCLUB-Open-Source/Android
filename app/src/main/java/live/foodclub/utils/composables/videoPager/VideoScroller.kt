package live.foodclub.utils.composables.videoPager

import live.foodclub.domain.models.home.VideoModel
import live.foodclub.utils.composables.shimmerBrush
import live.foodclub.utils.helpers.checkInternetConnectivity
import android.net.Uri
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

const val VIDEO_PROGRESS_BAR_VISIBLE_TIME = 3000L
@OptIn(ExperimentalFoundationApi::class)
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun VideoScroller(
    exoPlayer: ExoPlayer,
    video: VideoModel,
    pagerState: PagerState,
    pageIndex: Int,
    onSingleTap: (exoPlayer: ExoPlayer) -> Unit,
    onDoubleTap: (exoPlayer: ExoPlayer, offset: Offset) -> Unit,
    onVideoDispose: () -> Unit = {},
    onVideoGoBackground: () -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val isInternetConnected by rememberUpdatedState(newValue = checkInternetConnectivity(context))
    val brush = shimmerBrush()
    var totalDuration by remember { mutableLongStateOf(0L) }
    var currentTime by remember { mutableLongStateOf(0L) }
    val coroutineScope = rememberCoroutineScope()

    var thumbnail by remember {
        mutableStateOf(ThumbnailState(null, true))
    }

    var lastPause by remember {
        mutableLongStateOf(System.currentTimeMillis()- VIDEO_PROGRESS_BAR_VISIBLE_TIME)
    }
    var videoProgressBarVisible by remember {
        mutableStateOf(false)
    }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(video.thumbnailLink)
            .crossfade(true)
            .build()
    )
    LaunchedEffect(true) {
        thumbnail = try {
            thumbnail.copy(painter = painter, isLoading= true)
        } catch (e: Exception) {
            thumbnail.copy(isLoading = false)
        }
    }

    if (pagerState.settledPage == pageIndex){
        DisposableEffect(Unit) {
            val lifeCycleObserver = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_STOP -> {
                        exoPlayer.pause()
                        onVideoGoBackground()
                    }
                    Lifecycle.Event.ON_PAUSE -> exoPlayer.pause()
                    Lifecycle.Event.ON_RESUME -> exoPlayer.play()
                    Lifecycle.Event.ON_START -> exoPlayer.play()
                    else -> {}
                }
            }
            val listener = object : Player.Listener {
                override fun onEvents(player: Player, events: Player.Events) {
                    super.onEvents(player, events)
                    totalDuration = player.duration.coerceAtLeast(0L)
                    currentTime = player.currentPosition.coerceAtLeast(0L)
                }
            }
            val firstFrameListener = object : Player.Listener {
                override fun onRenderedFirstFrame() {
                    super.onRenderedFirstFrame()
                    thumbnail = thumbnail.copy(isLoading = false)
                }
            }

            lifecycleOwner.lifecycle.addObserver(lifeCycleObserver)
            exoPlayer.addListener(listener)
            exoPlayer.addListener(firstFrameListener)
            onDispose {
                exoPlayer.removeListener(listener)
                exoPlayer.removeListener(firstFrameListener)
                lifecycleOwner.lifecycle.removeObserver(lifeCycleObserver)
            }
        }

        LaunchedEffect(key1 = video.videoId) {
            val mediaItem = MediaItem.fromUri(Uri.parse(video.videoLink))
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()

            exoPlayer.apply {
                videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
                repeatMode = Player.REPEAT_MODE_ONE
                playWhenReady = true
            }
        }

        DisposableEffect(true) {
            val updateIntervalMillis = 50L
            val job = coroutineScope.launch {
                while (isActive) {
                    currentTime = exoPlayer.currentPosition.coerceAtLeast(0L)
                    delay(updateIntervalMillis)
                }
            }
            onDispose {
                job.cancel()
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                onVideoDispose()
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

        if (isInternetConnected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                AndroidView(
                    factory = {
                        playerView
                    },
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                onSingleTap(exoPlayer)
                                lastPause = System.currentTimeMillis()
                                videoProgressBarVisible = true
                            },
                            onDoubleTap = { offset ->
                                onDoubleTap(exoPlayer, offset)
                            }
                        )
                    }
                )

                LaunchedEffect(lastPause){
                    delay(VIDEO_PROGRESS_BAR_VISIBLE_TIME)
                    videoProgressBarVisible = (System.currentTimeMillis() - lastPause).coerceAtMost(
                        VIDEO_PROGRESS_BAR_VISIBLE_TIME
                    ) < VIDEO_PROGRESS_BAR_VISIBLE_TIME
                }

                AnimatedVisibility (
                    visible = videoProgressBarVisible,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    enter = EnterTransition.None
                ) {
                    VideoProgressBar(
                        modifier = Modifier,
                        currentTime = { currentTime },
                        totalDuration = { totalDuration }
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush)
            )
        }
    }

    if (thumbnail.isLoading) {
        if (thumbnail.painter != null) {
            Image(
                painter = thumbnail.painter!!,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush)
            )
        }
    }
}

data class ThumbnailState(
    val painter: Painter?,
    val isLoading: Boolean
)
