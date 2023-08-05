package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.R
import android.Manifest
import android.kotlin.foodclub.utils.composables.createVideoCaptureUseCase
import android.kotlin.foodclub.utils.composables.startRecordingVideo
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import java.io.File
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun RecordingButton(isRecording: Boolean) {
    val progress by animateFloatAsState(
        targetValue = if (isRecording) 1f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(80.dp)
    ) {
        // Background circle
        CircularProgressIndicator(
            progress = 1f,
            strokeWidth = 5.dp,
            color = Color.White,
            modifier = Modifier.size(80.dp)
        )
        // Animated circle
        CircularProgressIndicator(
            progress = progress,
            strokeWidth = 5.dp,
            color = Color(android.graphics.Color.parseColor("#7EC60B")),
            modifier = Modifier.size(80.dp)
        )
        Canvas(modifier = Modifier.size(60.dp)) {
            drawCircle(color = Color(android.graphics.Color.parseColor("#CACBCB")))
        }
        // Record button
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraView(
    navController: NavController
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    )

    var recording: Recording? = remember { null }
    val previewView: PreviewView = remember { PreviewView(context) }
    val videoCapture: MutableState<VideoCapture<Recorder>?> = remember { mutableStateOf(null) }
    val recordingStarted: MutableState<Boolean> = remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()

    val audioEnabled: MutableState<Boolean> = remember { mutableStateOf(false) }
    val cameraSelector: MutableState<CameraSelector> = remember {
        mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA)
    }
    val getContent = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            val uriEncoded = URLEncoder.encode(
                uri.toString(),
                StandardCharsets.UTF_8.toString()
            )
            navController.navigate("CAMERA_PREVIEW_VIEW/${uriEncoded}")
        }
    )
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp + 10.dp

    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    SideEffect {
        systemUiController.setNavigationBarColor(
            color = Color.Black
        )
    }
    LaunchedEffect(previewView) {
        videoCapture.value = context.createVideoCaptureUseCase(
            lifecycleOwner = lifecycleOwner,
            cameraSelector = cameraSelector.value,
            previewView = previewView
        )
    }
    PermissionsRequired(
        multiplePermissionsState = permissionState,
        permissionsNotGrantedContent = { /* ... */ },
        permissionsNotAvailableContent = { /* ... */ }
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black)
        ) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxWidth().height(screenHeight).clip(RoundedCornerShape(20.dp))
            )
            Box(
                modifier = Modifier.fillMaxWidth().height(screenHeight).padding(start = 20.dp, top = 50.dp, end = 20.dp, bottom = 20.dp)
            ) {

                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.Black.copy(alpha = 0.9f))
                            .blur(radius = 20.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                            .clickable {
                                // Do something when the box is clicked
                            }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = "Story",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(25.dp)
                                .height(25.dp).align(Alignment.Center)
                        )
                    }
                if (!recordingStarted.value) {

                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White)
                            .blur(radius = 20.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                            .align(Alignment.TopEnd)
                            .clickable {
                                // Do something when the box is clicked
                            }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                            contentDescription = "Story",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp).align(Alignment.Center)
                        )
                    }
                }
                IconButton(
                    onClick = {
                        if (!recordingStarted.value) {
                            videoCapture.value?.let { videoCapture ->
                                recordingStarted.value = true
                                val mediaDir = context.externalCacheDirs.firstOrNull()?.let {
                                    File(
                                        it,
                                        context.getString(R.string.app_name)
                                    ).apply { mkdirs() }
                                }

                                recording = startRecordingVideo(
                                    context = context,
                                    filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                                    videoCapture = videoCapture,
                                    outputDirectory = if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir,
                                    executor = context.mainExecutor,
                                    audioEnabled = audioEnabled.value
                                ) { event ->
                                    if (event is VideoRecordEvent.Finalize) {
                                        val uri = event.outputResults.outputUri
                                        if (uri != Uri.EMPTY) {
                                            val uriEncoded = URLEncoder.encode(
                                                uri.toString(),
                                                StandardCharsets.UTF_8.toString()
                                            )
                                            navController.navigate("CAMERA_PREVIEW_VIEW/${uriEncoded}")
                                        }
                                    }
                                }
                            }
                        } else {
                            recordingStarted.value = false
                            recording?.stop()
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter).size(80.dp)
                ) {
                    RecordingButton(isRecording = recordingStarted.value)
                    /*Icon(
                        painter = painterResource(if (recordingStarted.value) R.drawable.story_user else R.drawable.save),
                        contentDescription = "",
                        modifier = Modifier.size(64.dp)
                    )*/
                }
                if (!recordingStarted.value) {
                    IconButton(
                        onClick = {
                            getContent.launch("video/*")
                            //audioEnabled.value = !audioEnabled.value
                        },
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                    ) {
                        Icon(
                            painter = painterResource(if (audioEnabled.value) R.drawable.story_border_white else R.drawable.story_border),
                            contentDescription = "",
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }
                        Image(
                            painter = painterResource(id = R.drawable.baseline_cameraswitch_24),
                            contentDescription = "Story",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .align(Alignment.BottomEnd)
                                .clickable {
                                    cameraSelector.value =
                                        if (cameraSelector.value == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
                                        else CameraSelector.DEFAULT_BACK_CAMERA
                                    lifecycleOwner.lifecycleScope.launch {
                                        videoCapture.value = context.createVideoCaptureUseCase(
                                            lifecycleOwner = lifecycleOwner,
                                            cameraSelector = cameraSelector.value,
                                            previewView = previewView
                                        )
                                    }
                                }
                        )

            }
        }
    }
}
