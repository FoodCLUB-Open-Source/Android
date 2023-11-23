package android.kotlin.foodclub.views.home

import android.Manifest
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.utils.composables.engine.createVideoCaptureUseCase
import android.kotlin.foodclub.utils.composables.engine.startRecordingVideo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import okio.ByteString.Companion.encodeUtf8
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
        ), label = ""
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(80.dp)
    ) {
        CircularProgressIndicator(
            progress = 1f,
            strokeWidth = 5.dp,
            color = Color.White,
            modifier = Modifier.size(80.dp)
        )
        CircularProgressIndicator(
            progress = progress,
            strokeWidth = 5.dp,
            color = foodClubGreen,
            modifier = Modifier.size(80.dp)
        )
        Canvas(modifier = Modifier.size(60.dp)) {
            drawCircle(color = Color(0xFFCACBCB))
        }
        // Record button
    }

}

@Composable
fun RecordingClipsButton(
    isRecording: Boolean,
    removeClip: Boolean = false,
    removeUpdate: (Boolean) -> Unit = {},
    addClip: Boolean = false,
    clipUpdate: (Boolean) -> Unit = {}
) {

    val (rememberProgress, progressUpdate) = remember {
        mutableFloatStateOf(0f)
    }

    val clipArcs = remember {
        mutableListOf<Float>()
    }

    if (removeClip) {
        if (clipArcs.size > 1) {
            clipArcs.removeAt(clipArcs.lastIndex)
            progressUpdate(clipArcs[clipArcs.lastIndex])
        } else if (clipArcs.isNotEmpty()) {
            clipArcs.removeAt(clipArcs.lastIndex)
            progressUpdate(0f)
        }

        removeUpdate(false)
    }

    val progress by animateFloatAsState(
        targetValue = if (isRecording) 1f else rememberProgress,
        animationSpec = if (isRecording) {infiniteRepeatable(
            animation = tween(
                durationMillis = 20000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )}
        else
        {
            snap(delayMillis = 500)
        }
       , label = ""
    )

    if (!isRecording) {
        if (progress != 0f && addClip) {
            clipArcs.add(progress) //Constantly adding to clip Arcs should only add at one point
            progressUpdate(progress)
            clipUpdate(false)
        }

    }



    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(80.dp)
    ) {

        Canvas(modifier = Modifier.fillMaxSize())
        {
            drawArc(
                color = Color(0x55FFFFFF),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 25f)
            )
        }

        CircularProgressIndicator(
            progress = progress,
            strokeWidth = 5.dp,
            color = foodClubGreen,
            modifier = Modifier.size(80.dp)
        )

        Canvas(modifier = Modifier.fillMaxSize())
        {
            clipArcs.forEach {
                drawArc(
                    color = Color.White,
                    startAngle = (360f * it) - 90f,
                    sweepAngle = 3f,
                    useCenter = false,
                    style = Stroke(width = 25f)
                )
            }

        }

        Canvas(modifier = Modifier.size(60.dp)) {
            drawCircle(color = Color(0xFFCACBCB))
        }
        // Record button
    }

}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraView(
    navController: NavController,
    stateEncoded: String
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var state = ""

    if (stateEncoded.contains(GalleryState.STORY.state)) {
        state = GalleryState.STORY.state
    }
    if (stateEncoded.contains(GalleryState.RECIPE.state)) {
        state = GalleryState.RECIPE.state
    }

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO
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
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uri ->
            val uriEncoded = URLEncoder.encode(
                uri.toString(),
                StandardCharsets.UTF_8.toString()
            )
            Log.i("CameraView", uri.toString())
            //navController.navigate("CAMERA_PREVIEW_VIEW/${uriEncoded}")
        }
    )
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp + 10.dp

    val interactionSource = remember { MutableInteractionSource() }

    val uris = remember {
        mutableStateListOf<String>()
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
        mutableStateOf(true)
    }

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

    LaunchedEffect(canDelete)
    {
        delay(500)
        canDelete = true
    }

    LaunchedEffect(canAdd)
    {
        delay(1000)
        canAdd = true
    }

    PermissionsRequired(
        multiplePermissionsState = permissionState,
        permissionsNotGrantedContent = { /* ... */ },
        permissionsNotAvailableContent = { /* ... */ }
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
                    .clip(RoundedCornerShape(20.dp))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight)
                    .padding(start = 20.dp, top = 50.dp, end = 20.dp, bottom = 20.dp)
            ) {

                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Black.copy(alpha = 0.9f))
                        .clickable {
                            navController.popBackStack()
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = stringResource(id = R.string.story),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                            .align(Alignment.Center)
                    )
                }
                if (!recordingStarted.value) {

                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White)
                            .align(Alignment.TopEnd)
                            .clickable {
                                holdOrPress = !holdOrPress
                            }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                            contentDescription = stringResource(id = R.string.story),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
                val isPressed by interactionSource.collectIsPressedAsState()
                IconButton(

                    onClick = {

                        if(!holdOrPress) {
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

                                                val uriEncoded = URLEncoder.encode(
                                                    uri.toString(),
                                                    StandardCharsets.UTF_8.toString()
                                                )

                                                navController.navigate("CAMERA_PREVIEW_VIEW/${uriEncoded}/${state.encodeUtf8()}")
                                                clipUpdate(true)
                                                uris.add(uriEncoded)
                                            }
                                        }
                                    }
                                }
                            } else {
                                recordingStarted.value = false
                                recording?.stop()
                            }
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .size(80.dp),
                    interactionSource = interactionSource
                ) {

                    if (holdOrPress) {
                        if (isPressed) {
                            Log.d("Recording Start","Preparing recording")
                            if (!recordingStarted.value && canAdd) {
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

                                                val uriEncoded = URLEncoder.encode(
                                                    uri.toString(),
                                                    StandardCharsets.UTF_8.toString()
                                                )

                                                clipUpdate(true)
                                                uris.add(uriEncoded)
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            Log.d("Recording Start","Preparing to end recording")
                            if (recordingStarted.value) {
                                recordingStarted.value = false
                                recording?.stop()
                                canAdd = false
                            }
                        }
                    }

                    RecordingClipsButton(
                        isRecording = recordingStarted.value,
                        removeClip = removeClip,
                        removeUpdate = removeUpdate,
                        addClip = addClip,
                        clipUpdate = clipUpdate
                    )

                }
                if (!recordingStarted.value) {
                    val bitmapCheck = loadCurrentThumbnail(context = context)
                    val bitmap: ImageBitmap
                    if (bitmapCheck != null) {
                        bitmap = bitmapCheck.asImageBitmap()
                        Image(
                            bitmap = bitmap,
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .clip(RoundedCornerShape(5.dp))
                                .then(
                                    Modifier
                                        .size(64.dp)
                                        .border(2.dp, Color.White, RoundedCornerShape(5.dp))
                                )
                                .clickable {
                                    navController.navigate("GALLERY_VIEW/${state.encodeUtf8()}")
                                }
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .clip(RoundedCornerShape(5.dp))
                                .then(
                                    Modifier
                                        .size(64.dp)
                                        .border(2.dp, Color.White, RoundedCornerShape(5.dp))
                                )
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .height(80.dp)
                        .fillMaxWidth(0.3f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    if (uris.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(38.dp)
                                .background(Color.Gray, shape = RoundedCornerShape(10.dp))
                                .clip(
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable {
                                    if (canDelete) {
                                        uris.removeAt(uris.lastIndex)
                                        removeUpdate(true)
                                        canDelete = false
                                    }

                                },
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.delete_left_fill_1),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .height(25.dp)
                                    .width(25.dp)
                                    .align(Alignment.Center)
                            )
                        }

                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(foodClubGreen),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.height(38.dp)
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


fun loadCurrentThumbnail(context: Context): Bitmap? {
    val imageProjection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DATE_TAKEN
    )

    val videoProjection = arrayOf(
        MediaStore.Video.Media._ID,
        MediaStore.Video.Media.DATE_TAKEN
    )

    var uri: Uri = Uri.EMPTY
    var timeSinceEpoch = "0"

    val selectionImageArgs = Bundle()
    selectionImageArgs.putInt(ContentResolver.QUERY_ARG_LIMIT, 1)
    val sortArgs = arrayOf(MediaStore.Images.ImageColumns.DATE_TAKEN)
    selectionImageArgs.putStringArray(ContentResolver.QUERY_ARG_SORT_COLUMNS, sortArgs)
    selectionImageArgs.putInt(
        ContentResolver.QUERY_ARG_SORT_DIRECTION,
        ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
    )

    val cursorImage = context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        imageProjection,
        selectionImageArgs,
        null,
    )

    cursorImage.use {
        it?.let {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dateColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val date = it.getString(dateColumn)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                uri = contentUri
                timeSinceEpoch = date
            }
        } ?: run {
            Log.e("LoadCurrentThumbnail", "Cursor is null!")
        }
    }

    val selectionVideoArgs = Bundle()
    selectionVideoArgs.putInt(ContentResolver.QUERY_ARG_LIMIT, 1)
    val sortVideoArgs = arrayOf(MediaStore.Video.VideoColumns.DATE_TAKEN)
    selectionVideoArgs.putStringArray(ContentResolver.QUERY_ARG_SORT_COLUMNS, sortVideoArgs)
    selectionVideoArgs.putInt(
        ContentResolver.QUERY_ARG_SORT_DIRECTION,
        ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
    )

    val cursor = context.contentResolver.query(
        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
        videoProjection,
        selectionVideoArgs,
        null,
    )

    cursor.use {
        it?.let {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dateColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val date = it.getString(dateColumn)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                if (timeSinceEpoch < date) {
                    uri = contentUri
                }
            }
        } ?: run {
            Log.e("LoadCurrentThumbnail", "Cursor is null!")
        }
    }

    return if (uri.toString().isEmpty()) null else context.contentResolver.loadThumbnail(
        uri,
        Size(240, 240),
        null
    )
}
