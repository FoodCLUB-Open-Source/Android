package com.example.foodclub.views.home

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.kotlin.foodclub.R
import android.kotlin.foodclub.viewmodels.home.CreateViewModel
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
//import com.google.android.exoplayer2.ExoPlayer
//import com.google.android.exoplayer2.MediaItem
//import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun CreateView() {
//    val viewModel: CreateViewModel = viewModel()
//    val title = viewModel.title.value ?: "Loading..."
//    Text(text = title)

    var trimStartMs: Long = 1000
    var trimEndMs: Long = 2000

    var activity = LocalContext.current as Activity

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Box {
            val context = LocalContext.current
            var playWhenReady by remember { mutableStateOf(true) }
            val exoPlayer = remember {
                ExoPlayer.Builder(context).build().apply {
                    setMediaItem(MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-1/mp4/portrait_avc_aac.mp4"))
                    repeatMode = ExoPlayer.REPEAT_MODE_ALL
                    playWhenReady = playWhenReady
                    prepare()
                    play()
                }
            }

            DisposableEffect(
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = {
                        PlayerView(context).apply {
                            player = exoPlayer
                            useController = false
                            FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                        }
                    }
                )
            ) {
                onDispose {
                    exoPlayer.release()
                }
            }
            Box(modifier = Modifier
                .align(Alignment.TopStart)
                .padding(20.dp)) {
                Button(
                    modifier = Modifier.size(50.dp),
                    onClick = {

                    },
                    contentPadding = PaddingValues(1.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF292929))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.close_icon),
                        contentDescription = "Image",
                        modifier = Modifier
                            .height(15.dp)
                            .width(15.dp)

                    )
                }
            }
            Box(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)) {
                Column(horizontalAlignment = Alignment.End) {
                    val mainViewModel: CreateViewModel = viewModel()

                    Button(
                        modifier = Modifier.height(50.dp).width(80.dp),
                        onClick = {
                            mainViewModel.setApplicationData( activity,trimStartMs,trimEndMs)
                            mainViewModel.startExport()
                        },
                        contentPadding = PaddingValues(1.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.next_icon),
                            contentDescription = "Image",
                            modifier = Modifier
                                .height(15.dp)
                                .width(15.dp)

                        )
                    }
                    var sliderPosition by remember { mutableStateOf(1f..2f) }
                    var valueRange : ClosedFloatingPointRange<Float> = 0.00f.rangeTo(3.00f)

                    RangeSlider(
                        value = sliderPosition,
                        steps = 3,
                        onValueChange = { range -> sliderPosition = range },
                        valueRange = valueRange,
                        onValueChangeFinished = {
                            trimStartMs = sliderPosition.start.toLong() * 1000
                            trimEndMs = sliderPosition.endInclusive.toLong() * 1000
                        },
                    )
                }
            }

        }
    }



}

//@Composable
//fun VideoPlayerScreen() {
//    val context = LocalContext.current
//    var playWhenReady by remember { mutableStateOf(true) }
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            setMediaItem(MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-1/mp4/portrait_avc_aac.mp4"))
//            repeatMode = ExoPlayer.REPEAT_MODE_ALL
//            playWhenReady = playWhenReady
//            prepare()
//            play()
//        }
//    }
//
//    DisposableEffect(
//        AndroidView(
//            modifier = Modifier.fillMaxSize(),
//            factory = {
//                PlayerView(context).apply {
//                    player = exoPlayer
//                    useController = false
//                    FrameLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT
//                    )
//                }
//            }
//        )
//    ) {
//        onDispose {
//            exoPlayer.release()
//        }
//    }
//}
//
//@Composable
//fun CloseButton() {
//    Button(
//        modifier = Modifier.size(50.dp),
//        onClick = {
//
//        },
//        contentPadding = PaddingValues(1.dp),
//        shape = RoundedCornerShape(10.dp),
//        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF292929))
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.close_icon),
//            contentDescription = "Image",
//            modifier = Modifier
//                .height(15.dp)
//                .width(15.dp)
//
//        )
//    }
//}
//
//@Composable
//fun ProceedButton(application: Application, activity: Activity) {
//    val mainViewModel: CreateViewModel = viewModel()
//
//    Button(
//        modifier = Modifier.height(50.dp).width(80.dp),
//        onClick = {
//            mainViewModel.setApplicationData(activity,trimStartMs,trimEndMs)
//            mainViewModel.startExport()
//        },
//        contentPadding = PaddingValues(1.dp),
//        shape = RoundedCornerShape(10.dp),
//        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0))
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.next_icon),
//            contentDescription = "Image",
//            modifier = Modifier
//                .height(15.dp)
//                .width(15.dp)
//
//        )
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun RangeSliderExample() {
//    var sliderPosition by remember { mutableStateOf(1f..2f) }
//    var valueRange : ClosedFloatingPointRange<Float> = 0.00f.rangeTo(3.00f)
//
//    RangeSlider(
//        value = sliderPosition,
//        steps = 3,
//        onValueChange = { range -> sliderPosition = range },
//        valueRange = valueRange,
//        onValueChangeFinished = {
//            trimStartMs = sliderPosition.start.toLong() * 1000
//            trimEndMs = sliderPosition.endInclusive.toLong() * 1000
//        },
//    )
//}
