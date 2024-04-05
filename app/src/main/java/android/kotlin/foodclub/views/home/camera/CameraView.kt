package android.kotlin.foodclub.views.home.camera

import android.Manifest
import android.content.Intent
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.confirmScreenColor
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.navigation.CreateRecipeScreen
import android.kotlin.foodclub.utils.composables.PermissionErrorBox
import android.kotlin.foodclub.utils.composables.engine.createVideoCaptureUseCase
import android.kotlin.foodclub.utils.composables.engine.startRecordingVideo
import android.kotlin.foodclub.viewModels.home.camera.CameraEvents
import android.kotlin.foodclub.viewModels.home.camera.StopWatchEvent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import java.io.File

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraView(
    events: CameraEvents,
    navController: NavController,
    state: CameraState
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_VIDEO)

    var recording: Recording? by remember { mutableStateOf(null) }
    val previewView: PreviewView = remember { PreviewView(context) }
    val videoCapture: MutableState<VideoCapture<Recorder>?> = remember { mutableStateOf(null) }
    val recordingStarted: MutableState<Boolean> = remember { mutableStateOf(false) }

    val audioEnabled: MutableState<Boolean> = remember { mutableStateOf(false) }
    val cameraSelector: MutableState<CameraSelector> = remember {
        mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA)
    }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp + dimensionResource(id = R.dimen.dim_10)

    val interactionSource = remember { MutableInteractionSource() }

    val uris = remember {
        mutableStateListOf<Uri>()
    }

    val (addClip, clipUpdate) = rememberSaveable {
        mutableStateOf(false)
    }

    val (removeClip, removeUpdate) = rememberSaveable {
        mutableStateOf(false)
    }

    var canDelete by remember {
        mutableStateOf(false)
    }

    var canAdd by remember {
        mutableStateOf(true)
    }

    var holdOrPress by remember{
        mutableStateOf(false)
    }

    var confirmDeletion by remember{
        mutableStateOf(false)
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) {
            it?.let { uri ->
                context.contentResolver
                    .takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                uris.clear()
                uris.add(uri)
            }
        }

    LaunchedEffect(previewView) {
        videoCapture.value = context.createVideoCaptureUseCase(
            lifecycleOwner = lifecycleOwner,
            cameraSelector = cameraSelector.value,
            previewView = previewView
        )
    }

    LaunchedEffect(canDelete) {
        delay(500)
        canDelete = true
    }

    LaunchedEffect(canAdd) {
        delay(1000)
        canAdd = true
    }

    PermissionErrorBox(
        permissions = permissions,
        title = stringResource(id = R.string.Video_permission_require_message_title),
        rational = stringResource(id = R.string.Video_permission_require_rational),
        navController = navController,
        context = context
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight)
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_20)))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight)
                    .padding(
                        start = dimensionResource(id = R.dimen.dim_20),
                        top = dimensionResource(id = R.dimen.dim_50),
                        end = dimensionResource(id = R.dimen.dim_20),
                        bottom = dimensionResource(id = R.dimen.dim_20)
                    )
            ) {

                Box(
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.dim_40))
                        .height(dimensionResource(id = R.dimen.dim_40))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)))
                        .background(Color.Black.copy(alpha = 0.9f))
                        .clickable {
                            // Do something when the box is clicked
                            events.onEvent(StopWatchEvent.onReset)
                            navController.popBackStack()
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                        contentDescription = stringResource(id = R.string.story),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.dim_25))
                            .height(dimensionResource(id = R.dimen.dim_25))
                            .align(Alignment.Center)
                    )
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (confirmDeletion) {
                        AlertDialog(
                            onDismissRequest = { confirmDeletion = !confirmDeletion },
                            modifier = Modifier
                                .background(
                                    Color.White,
                                    RoundedCornerShape(dimensionResource(id = R.dimen.dim_8))
                                )
                                .padding(dimensionResource(id = R.dimen.dim_5)),) {

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_10))
                            ){
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    text = stringResource(R.string.Delete_Clip_Message)
                                )
                                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_10)))

                                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.LightGray
                                        ),
                                        onClick = {
                                            confirmDeletion = !confirmDeletion
                                        }
                                    ) {
                                        Text(
                                            text= stringResource(id = R.string.Cancel),
                                            fontWeight = FontWeight(600),
                                            fontFamily = Montserrat,
                                            color = Color.White,
                                            fontSize = dimensionResource(id = R.dimen.fon_14).value.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_10)))

                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Red
                                        ),
                                        onClick = {
                                            uris.removeAt(uris.lastIndex)
                                            events.onEvent(StopWatchEvent.onRecall)
                                            removeUpdate(true)
                                            canDelete = false
                                            confirmDeletion = !confirmDeletion
                                        }
                                    ) {
                                        Text(
                                            text= stringResource(id = R.string.Confirm),
                                            fontWeight = FontWeight(600),
                                            fontFamily = Montserrat,
                                            color = Color.White,
                                            fontSize = dimensionResource(id = R.dimen.fon_14).value.sp
                                        )
                                    }
                                }
                            }

                        }
                    }
                    
                    var time = if (state.minutes < 10) {
                        "0${state.minutes}"
                    } else {
                        state.minutes.toString()
                    }

                    time += if (state.seconds < 10) {
                        ":0${state.seconds}"
                    } else {
                        ":${state.seconds}"
                    }

                    Text(text = time, modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_10)), color = Color.White)
                    IconButton(
                        onClick = {
                            if(!holdOrPress) {
                                if (!recordingStarted.value) {
                                    videoCapture.value?.let { videoCapture ->
                                        recordingStarted.value = true
                                        events.onEvent(StopWatchEvent.onStart)
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
                                            outputDirectory = if (mediaDir != null && mediaDir.exists())
                                                mediaDir
                                            else
                                                context.filesDir,
                                            executor = context.mainExecutor,
                                            audioEnabled = audioEnabled.value
                                        ) { event ->
                                            if (event is VideoRecordEvent.Finalize) {
                                                val uri = event.outputResults.outputUri
                                                if (uri != Uri.EMPTY) {
                                                    clipUpdate(true)
                                                    uris.add(uri)
                                                }
                                            }
                                        }

                                    }
                                } else {
                                    recordingStarted.value = false
                                    events.onEvent(StopWatchEvent.onStop)
                                    recording?.stop()
                                }
                            }
                        },
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.dim_80)),
                        interactionSource = interactionSource,
                        enabled = state.minutes < 1
                    ) {

                        if(state.minutes >= 1) {
                            if (recordingStarted.value) {
                                recordingStarted.value = false
                                events.onEvent(StopWatchEvent.onStop)
                                recording?.stop()
                            }
                        }
                        RecordingClipsButton(
                            isRecording = recordingStarted.value,
                            removeClip = removeClip,
                            removeUpdate = removeUpdate,
                            addClip = addClip,
                            clipUpdate = clipUpdate,
                            state = state
                        )
                    }
                }

                if (!recordingStarted.value){
                    Row(modifier = Modifier
                        .height(dimensionResource(id = R.dimen.dim_80))
                        .align(Alignment.BottomStart), verticalAlignment = Alignment.CenterVertically)
                    {
                        IconButton(
                            modifier = Modifier.size(dimensionResource(id = R.dimen.dim_64)),
                            onClick = { galleryLauncher.launch(arrayOf("video/*")) }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.select_from_gallery),
                                contentDescription = "gallery launcher",
                                tint = Color.White
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .height(dimensionResource(id = R.dimen.dim_80))
                        .fillMaxWidth(0.3f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (uris.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .width(dimensionResource(id = R.dimen.dim_40))
                                .height(dimensionResource(id = R.dimen.dim_38))
                                .background(
                                    Color.Gray,
                                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10))
                                )
                                .clip(
                                    RoundedCornerShape(dimensionResource(id = R.dimen.dim_10))
                                )
                                .clickable {
                                    if (canDelete) {
                                        confirmDeletion = true
                                    }
                                },
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.delete_left_fill_1),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .height(dimensionResource(id = R.dimen.dim_25))
                                    .width(dimensionResource(id = R.dimen.dim_25))
                                    .align(Alignment.Center)
                            )
                        }

                        Button(
                            onClick = {
                                val mutableUriMap: MutableMap<Int, Uri> = mutableMapOf()

                                uris.forEachIndexed { index, uri ->
                                    mutableUriMap[index] = uri
                                }
                                events.onEvent(StopWatchEvent.onReset)
                                navController.currentBackStackEntry?.savedStateHandle?.set("videoUris", mutableUriMap)
                                navController.navigate(CreateRecipeScreen.VideoEditor.route)
                            },
                            colors = ButtonDefaults.buttonColors(foodClubGreen),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)),
                            contentPadding = PaddingValues(dimensionResource(id = R.dimen.dim_0)),
                            modifier = Modifier.height( dimensionResource(id = R.dimen.dim_38))
                        ) {
                            Text(
                                text = stringResource(id = R.string.continue_text),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}