package live.foodclub.presentation.ui.home.trimmer

import live.foodclub.R
import live.foodclub.config.ui.Raleway
import live.foodclub.config.ui.trimmerFilmSelectEdge
import live.foodclub.config.ui.trimmerTimelineText
import live.foodclub.domain.models.others.TrimmedVideo
import live.foodclub.utils.composables.LoadingView
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil

@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun TrimmerView(state: TrimmerState, events: TrimmerEvents) {
    val currentTime = remember { mutableLongStateOf(0L) }
    var isPlaying by remember { mutableStateOf(false) }
    val localContext = LocalContext.current
    val systemUiController = rememberSystemUiController()

    var isResetting by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isResetting){
        currentTime.value = 0L
        state.player.play()
        isPlaying = true
        isResetting = false
    }

    val timeSeconds = 120
    val totalWidth = (timeSeconds * 6 + 26).dp
    val dpPerSecond = (totalWidth.value - 26) / timeSeconds

    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = Color.Transparent
        )
    }

    AndroidView(
        factory = { context ->
            PlayerView(context).also {
                it.player = state.player
                it.useController = false
                it.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                it.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        update = {
            when (lifecycle) {
                Lifecycle.Event.ON_PAUSE -> {
                    it.onPause()
                    it.player?.pause()
                }
                Lifecycle.Event.ON_RESUME -> {
                    it.onResume()
                }
                else -> Unit
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    events.togglePlay()
                    isPlaying = !isPlaying
                }
            }
    )

    DisposableEffect(state) {
        val coroutineScope = CoroutineScope(Dispatchers.Main)

        // Start the coroutine to update progress
        val job = coroutineScope.launch {
            while (true) {
                val timeElapsed = state.videoObjects.filter {
                    it.id < state.player.currentMediaItemIndex }.sumOf { it.duration }
                currentTime.longValue = state.player.currentPosition + timeElapsed
                delay(500) // Update every second (adjust as needed)
            }
        }

        // Cancel the coroutine when the effect is disposed (e.g., when the composable is removed)
        onDispose {
            job.cancel()
        }
    }

    Box(Modifier.fillMaxSize()){
        Box(Modifier.align(Alignment.BottomCenter)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dim_16))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.dim_10)),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            state.player.pause()
                            events.resetState()
                            isResetting = !isResetting
                                  },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier
                            .padding(end = dimensionResource(R.dimen.dim_28))
                            .width(dimensionResource(R.dimen.dim_92))
                            .height(dimensionResource(R.dimen.dim_54))
                            .clip(RoundedCornerShape(dimensionResource(R.dimen.dim_10)))
                            .background(Color.White)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    Button(
                        onClick = { events.createVideo(localContext) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier
                            .padding(end = dimensionResource(R.dimen.dim_28))
                            .width(dimensionResource(R.dimen.dim_92))
                            .height(dimensionResource(R.dimen.dim_54))
                            .clip(RoundedCornerShape(dimensionResource(R.dimen.dim_10)))
                            .background(Color.White)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.right_arrow),
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                }
                BottomTrimmerControl(
                    timeSeconds = 120,
                    totalWidth = totalWidth,
                    dpPerSecond = dpPerSecond,
                    videoList = state.videoObjects,
                    currentTime = currentTime,
                    onSeek = { events.navigate(it) },
                    isResetting = isResetting,
                    modifier = Modifier.height(dimensionResource(R.dimen.dim_150))
                )
            }
        }

        if(state.isLoading) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
            ) {
                LoadingView()
            }
        }
    }
}

@Composable
fun BottomTrimmerControl(
    timeSeconds: Long,
    totalWidth: Dp,
    dpPerSecond: Float,
    videoList: List<TrimmedVideo>,
    currentTime: State<Long>,
    onSeek: (Long) -> Unit,
    isResetting: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .background(Color.White)
        .padding(top = dimensionResource(R.dimen.dim_24))
        .width(totalWidth)
        .horizontalScroll(rememberScrollState())) {
        Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dim_8))) {
            Timeline(totalWidth, timeSeconds)
            TrimmedVideos(totalWidth, dpPerSecond, videoList, isResetting = isResetting)
        }

        Box(modifier = Modifier
            .padding(
                start = dimensionResource(R.dimen.dim_13),
                top = dimensionResource(R.dimen.dim_24),
                end = dimensionResource(R.dimen.dim_13)
            )
            .width(totalWidth)
        ) {
            ProgressBar(currentTime, dpPerSecond)
            Box(modifier = Modifier
                .height(dimensionResource(R.dimen.dim_12))
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        onSeek(((offset.x.toDp() / dpPerSecond).value * 1000).toLong())
                    }
                }
            )
        }
    }
}

@Composable
fun TrimmedVideos(
    totalWidth: Dp,
    dpPerSecond: Float,
    videoList: List<TrimmedVideo>,
    isResetting: Boolean,
) {
    var selectedVideo by remember { mutableIntStateOf(-1) }
    LazyRow(
        contentPadding = PaddingValues(start = dimensionResource(R.dimen.dim_8)),
        userScrollEnabled = false,
        modifier = Modifier
            .padding(start = dimensionResource(R.dimen.dim_8))
            .height(dimensionResource(R.dimen.dim_64))
            .width(totalWidth)
    ) {
        items(
            items = videoList,
            key = { item -> item.id }
        ) { item ->
            TrimmedVideo(
                dpPerSecond = dpPerSecond,
                video = item,
                isSelected = selectedVideo == item.id,
                onClick = { selectedVideo = it },
                isResetting = isResetting
            )
        }
    }
}

@Composable
fun TrimmedVideo(
    dpPerSecond: Float,
    video: TrimmedVideo,
    isSelected: Boolean,
    onClick: (Int) -> Unit,
    isResetting: Boolean
) {
    val widthDerivedFromLength = (video.initialDuration / 1000 * dpPerSecond).dp
    var maximumElementWidth by remember { mutableStateOf(widthDerivedFromLength) }
    var elementWidth by remember { mutableStateOf(maximumElementWidth) }

    if (maximumElementWidth != widthDerivedFromLength) {
        maximumElementWidth = widthDerivedFromLength
        elementWidth = widthDerivedFromLength
    }
    var startOffset by remember { mutableStateOf(0.dp) }
    var endOffset by remember { mutableStateOf(0.dp) }

    LaunchedEffect(isResetting) {
        maximumElementWidth = (video.initialDuration / 1000 * dpPerSecond).dp
        elementWidth = maximumElementWidth
        startOffset = 0.dp
        endOffset = 0.dp
    }

    Box(
        Modifier
            .fillMaxHeight()
            .width(elementWidth + endOffset)
            .padding(horizontal = dimensionResource(R.dimen.dim_4))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.dim_8)))
            .clickable { onClick(video.id) }
    ) {
        Row(Modifier.fillMaxSize()) {
            val frameWidth = 30
            val numberOfFramesDisplayed =
                ceil(((video.initialDuration / 1000) * dpPerSecond) / frameWidth).toInt()
            val indexDiffBetweenFrames = (video.frames.size / numberOfFramesDisplayed)

            val frames = video.frames.toList()
            var i = 0
            repeat(numberOfFramesDisplayed) { index ->
                val currentFrameOffset = startOffset - (index * frameWidth).dp
                val width = if (currentFrameOffset < 0.dp) frameWidth.dp else
                    frameWidth.dp - currentFrameOffset
                Image(
                    bitmap = frames[i].asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(width)
                        .fillMaxHeight()
                        .background(Color.Blue)
                )

                i += indexDiffBetweenFrames
            }
        }
        if (isSelected) {
            Box(modifier = Modifier
                .fillMaxHeight()
                .width(dimensionResource(R.dimen.dim_17))
                .background(trimmerFilmSelectEdge)
                .align(Alignment.CenterStart)
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDragEnd = {
                            video.startTrimming(
                                startOffset.value / dpPerSecond,
                                endOffset.value / dpPerSecond
                            )
                        }
                    ) { change, _ ->
                        val offsetX = change.positionChange().x.toDp()
                        val elementWidthWithOffset = elementWidth - offsetX

                        if (elementWidthWithOffset < maximumElementWidth
                            && elementWidthWithOffset > 10.dp
                        ) {
                            elementWidth = elementWidthWithOffset
                            startOffset += offsetX
                            video.previewTrimming(startOffset.value / dpPerSecond, true)
                        }
                    }
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.trim_left_arrow),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dimensionResource(R.dimen.dim_17))
                    .background(trimmerFilmSelectEdge)
                    .align(Alignment.CenterEnd)
                    .pointerInput(Unit) {
                        detectDragGesturesAfterLongPress(
                            onDragEnd = {
                                video.startTrimming(
                                    startOffset.value / dpPerSecond,
                                    endOffset.value / dpPerSecond
                                )
                            }
                        ) { change, _ ->
                            val offsetX = change.positionChange().x.toDp()
                            if (endOffset + offsetX < 0.dp
                                && endOffset + elementWidth + offsetX > 10.dp
                            ) {
                                endOffset += offsetX
                                video.previewTrimming(endOffset.value / dpPerSecond, false)
                            }
                        }
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.trim_right_arrow),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }


        }


    }
}

@Composable
fun ProgressBar(currentTime: State<Long>, dpPerSecond: Float) {
    val currentOffset = (currentTime.value / 1000) * dpPerSecond
    Box(Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .offset(x = currentOffset.dp)
                .width(dimensionResource(R.dimen.dim_2))
                .height(dimensionResource(R.dimen.dim_84))
                .background(Color.Black)

        )
    }
}

@Composable
fun Timeline(totalWidth: Dp, timeSeconds: Long) {
    val numberOfLines = (totalWidth / 13).value.toInt()
    val numberOfUnits = numberOfLines / 13
    val scale = timeSeconds / numberOfUnits
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dim_8)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dim_12))) {
            var i = 0
            var currentTime = 0L
            repeat(numberOfLines - 3 * numberOfUnits) {
                if (i % 10 == 0) {
                    val minutes = (currentTime / 60).toInt()
                    val seconds = currentTime % 60
                    Text(
                        text = "$minutes:${if (seconds < 10) "0$seconds" else seconds}",
                        modifier = Modifier.width(dimensionResource(R.dimen.dim_27)),
                        fontFamily = Raleway,
                        fontWeight = FontWeight.Medium,
                        fontSize = dimensionResource(id = R.dimen.fon_12).value.sp,
                        color = trimmerTimelineText,
                        textAlign = TextAlign.Center,
                        softWrap = false
                    )
                    currentTime += scale
                } else {
                    Box(Modifier.width(dimensionResource(R.dimen.dim_1)))
                }
                i++
            }

        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dim_12)),
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.dim_13))
        ) {
            var i = 0
            repeat(numberOfLines) {
                //Draw measure line
                var resourceId = R.drawable.timeline_line_short
                if (i % 2 == 0) {
                    resourceId = R.drawable.timeline_line_long
                }
                Icon(painter = painterResource(id = resourceId), contentDescription = null)
                i++
            }
        }


    }
}