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
import android.kotlin.foodclub.viewModels.home.CameraViewModel
import android.kotlin.foodclub.viewModels.home.StopWatchEvent
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
import androidx.compose.foundation.layout.Column
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
        animationSpec = if (isRecording) {infiniteRepeatable<Float>(
            animation = tween<Float>( 60000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )}
        else
        {
            snap(500)
        }
       , label = ""
    )

    if (!isRecording) {
        if (progress != 0f && addClip) {
            clipArcs.add(progress) //Constantly adding to clip Arcs should only add at one point
            progressUpdate(progress)
            clipUpdate(false)
        }
        //progressUpdate(progress)
    }



    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(80.dp)
    ) {
        // Background circle
        /*
        CircularProgressIndicator(
            progress = 1f,
            strokeWidth = 5.dp,
            color = Color.White,
            modifier = Modifier.size(80.dp)
        )
         */

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

        // Animated circle
        CircularProgressIndicator(
            progress = progress,
            strokeWidth = 5.dp,
            color = foodClubGreen,
            modifier = Modifier.size(80.dp)
        )

        Canvas(modifier = Modifier.fillMaxSize())
        {
            val offset: Float = 0.005f
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

        /*
        Canvas(modifier = Modifier.fillMaxSize())
        {
            clipArcs.forEach {
                drawArc(color = Color.White, startAngle = (it + 270f), sweepAngle = 3f, useCenter = false)
            }
        }

         */

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
    viewModel: CameraViewModel,
    navController: NavController,
    stateEncoded: String
) {

    var seconds = (0)
    var minutes = (0)



    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var state = ""

    if (stateEncoded.contains("story")) {
        state = "story"
    }
    if (stateEncoded.contains("recipe")) {
        state = "recipe"
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
        mutableStateOf(false)
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
                        //.blur(radius = 20.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                        .clickable {
                            // Do something when the box is clicked
                            navController.popBackStack()
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                        contentDescription = "Story",
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
                            //.blur(radius = 20.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                            .align(Alignment.TopEnd)
                            .clickable {
                                // Do something when the box is clicked
                                holdOrPress = !holdOrPress
                            }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                            contentDescription = "Story",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                Column(modifier = Modifier.align(Alignment.BottomCenter), horizontalAlignment = Alignment.CenterHorizontally)
                {
                    //val stopWatch = Builder(Stopwatch)
                    //val sdf = SimpleDateFormat("mm:ss")

                    // on below line we are creating a variable for
                    // current date and time and calling a simple
                    // date format in it.
                    //val currentDateAndTime = sdf.format(Date())
                    var time = if (viewModel.minutes.value < 10)
                    {
                        "0${viewModel.minutes.value}"
                    }
                    else
                    {
                        viewModel.minutes.value.toString()
                    }

                    time += if (viewModel.seconds.value < 10)
                    {
                        ":0${viewModel.seconds.value}"
                    }
                    else
                    {
                        ":${viewModel.seconds.value}"
                    }

                    Text(text = time, modifier = Modifier.padding(10.dp), color = Color.White)
                    val isPressed by interactionSource.collectIsPressedAsState()
                    IconButton(

                        onClick = {

                            //Temporarily ignore recording code

                            if(!holdOrPress) {
                                if (!recordingStarted.value) {
                                    videoCapture.value?.let { videoCapture ->
                                        recordingStarted.value = true
                                        viewModel.onEvent(StopWatchEvent.onStart)
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

                                                    //navController.navigate("CAMERA_PREVIEW_VIEW/${uriEncoded}/${state.encodeUtf8()}")
                                                    //navController.navigate("GALLERY_VIEW/${uriEncoded}")
                                                    clipUpdate(true)
                                                    uris.add(uriEncoded)
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    recordingStarted.value = false
                                    viewModel.onEvent(StopWatchEvent.onStop)
                                    recording?.stop()
                                }
                            }

                            //navController.navigate("GALLERY_VIEW")
                        },
                        modifier = Modifier
                            //.align(Alignment.BottomCenter)
                            .size(80.dp),
                        interactionSource = interactionSource,
                        enabled = viewModel.minutes.value < 1
                    ) {

                        if(viewModel.minutes.value >= 1)
                        {
                            if (recordingStarted.value)
                            {
                                recordingStarted.value = false
                                viewModel.onEvent(StopWatchEvent.onStop)
                                recording?.stop()
                            }
                        }

                        if (holdOrPress) {
                            if (isPressed && viewModel.minutes.value < 1) {
                                Log.d("Recording Start","Preparing recording")
                                if (!recordingStarted.value && canAdd) {
                                    videoCapture.value?.let { videoCapture ->
                                        recordingStarted.value = true
                                        viewModel.onEvent(StopWatchEvent.onStart)
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

                                                    //navController.navigate("CAMERA_PREVIEW_VIEW/${uriEncoded}/${state.encodeUtf8()}")
                                                    //navController.navigate("GALLERY_VIEW/${uriEncoded}")
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
                                    viewModel.onEvent(StopWatchEvent.onStop)
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


                        /*Icon(
                            painter = painterResource(if (recordingStarted.value) R.drawable.story_user else R.drawable.save),
                            contentDescription = "",
                            modifier = Modifier.size(64.dp)
                        )*/
                    }
                }

                Row(modifier = Modifier.height(80.dp).align(Alignment.BottomStart), verticalAlignment = Alignment.CenterVertically)
                {
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
                                    //.align(Alignment.BottomStart)
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
                                    //.align(Alignment.BottomStart)
                                    .clip(RoundedCornerShape(5.dp))
                                    .then(
                                        Modifier
                                            .size(64.dp)
                                            .border(2.dp, Color.White, RoundedCornerShape(5.dp))
                                    )
                            )
                        }
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
                                    if (canDelete)
                                    {
                                        uris.removeAt(uris.lastIndex)
                                        viewModel.onEvent(StopWatchEvent.onRecall)
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
                            onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                                foodClubGreen
                            ),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.height(38.dp)
                        ) {
                            Text(text = "continue", color = Color.White)
                        }

                    }

                    /*
                    Image(
                        painter = painterResource(id = R.drawable.baseline_cameraswitch_24),
                        contentDescription = "Story",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            //.align(Alignment.BottomEnd)
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

                     */
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
        //imageSortOrder
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
                timeSinceEpoch = date //can't parse as Int
            }
        } ?: run {
            Log.e("TAG", "Cursor is null!")
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
        //imageSortOrder
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
            Log.e("TAG", "Cursor is null!")
        }
    }

    return if (uri.toString().isEmpty()) null else context.contentResolver.loadThumbnail(
        uri,
        Size(240, 240),
        null
    )
}
