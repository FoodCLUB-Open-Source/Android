package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Raleway
import android.kotlin.foodclub.domain.models.others.TrimmedVideo
import android.kotlin.foodclub.viewModels.home.create.TrimmerViewModel
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil

@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun TrimmerView(viewModel: TrimmerViewModel) {
    val videoItems2 = viewModel.videoObjects.collectAsState()
    val currentTime = remember { mutableLongStateOf(0L) }
    var isPlaying by remember { mutableStateOf(false) }
    val localContext = LocalContext.current

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

    AndroidView(
        factory = { context ->
            PlayerView(context).also {
                it.player = viewModel.player
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
                    viewModel.togglePlay()
                    isPlaying = !isPlaying
                }
            }
    )

    DisposableEffect(viewModel.player) {
        val coroutineScope = CoroutineScope(Dispatchers.Main)

        // Start the coroutine to update progress
        val job = coroutineScope.launch {
            while (true) {
                val timeElapsed = videoItems2.value.filter {
                    it.id < viewModel.player.currentMediaItemIndex }.sumOf { it.duration }
                currentTime.longValue = viewModel.player.currentPosition + timeElapsed
                delay(500) // Update every second (adjust as needed)
            }
        }

        // Cancel the coroutine when the effect is disposed (e.g., when the composable is removed)
        onDispose {
            job.cancel()
        }
    }

    Box(Modifier.fillMaxSize().padding(bottom = 20.dp)){
        Box(Modifier.align(Alignment.BottomCenter)) {
            Column {
                Button(
                    onClick = { viewModel.createVideo(localContext) },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Save")
                }
                BottomTrimmerControl(
                    timeSeconds = 120,
                    videoList = videoItems2.value,
                    currentTime = currentTime,
                    onSeek = { viewModel.navigate(it) },
                    Modifier.height(150.dp)
                )
            }
        }

    }
}

@Composable
fun BottomTrimmerControl(
    timeSeconds: Long,
    videoList: List<TrimmedVideo>,
    currentTime: State<Long>,
    onSeek: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val totalWidth = (timeSeconds.toInt() * 6 + 26).dp
    val dpPerSecond = (totalWidth.value - 26) / timeSeconds
    Box(modifier = modifier
        .background(Color.White)
        .padding(top = 24.dp)
        .width(totalWidth)
        .horizontalScroll(rememberScrollState())) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Timeline(totalWidth, timeSeconds)
            TrimmedVideos(totalWidth, dpPerSecond, videoList)
        }

        Box(modifier = Modifier
            .padding(start = 13.dp, top = 24.dp, end = 13.dp)
            .width(totalWidth)
        ) {
            ProgressBar(currentTime, dpPerSecond)
            Box(modifier = Modifier.height(12.dp).fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        onSeek(((offset.x.toDp() / dpPerSecond).value * 1000).toLong())
                    }
                })
        }
    }





}

@Composable
fun TrimmedVideos(totalWidth: Dp, dpPerSecond: Float, videoList: List<TrimmedVideo>) {
    var selectedVideo by remember { mutableStateOf(-1) }
    LazyRow(
        contentPadding = PaddingValues(start = 8.dp),
        userScrollEnabled = false,
        modifier = Modifier
            .padding(start = 8.dp)
            .height(64.dp)
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
                onClick = { selectedVideo = it })
        }
    }
}

@Composable
fun TrimmedVideo(dpPerSecond: Float, video: TrimmedVideo, isSelected: Boolean,
                 onClick: (Int) -> Unit
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

    Box(
        Modifier
            .fillMaxHeight()
            .width(elementWidth + endOffset)
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(8.dp))
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
                .width(12.dp)
                .background(Color(0xFF191A18))
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
                    .width(12.dp)
                    .background(Color(0xFF191A18))
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
                .width(2.dp)
                .height(84.dp)
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
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            var i = 0
            var currentTime = 0L
            repeat(numberOfLines - 3 * numberOfUnits) {
                if (i % 10 == 0) {
                    val minutes = (currentTime / 60).toInt()
                    val seconds = currentTime % 60
                    Text(
                        text = "$minutes:${if (seconds < 10) "0$seconds" else seconds}",
                        modifier = Modifier.width(27.dp),
                        fontFamily = Raleway,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = Color(0xFF8A8C86),
                        textAlign = TextAlign.Center,
                        softWrap = false
                    )
                    currentTime += scale
                } else {
                    Box(Modifier.width(1.dp))
                }
                i++
            }

        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(horizontal = 13.dp)
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