package android.kotlin.foodclub.views.home.camera

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
import android.kotlin.foodclub.views.home.GalleryState
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
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

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CameraView(
    viewModel: CameraViewModel,
    navController: NavController,
    stateEncoded: String,
    state: CameraState
) {

    var seconds = (0)
    var minutes = (0)


    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var stateString = ""

    if (stateEncoded.contains(GalleryState.STORY.state)) {
        stateString = GalleryState.STORY.state
    }
    if (stateEncoded.contains(GalleryState.RECIPE.state)) {
        stateString = GalleryState.RECIPE.state
    }

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO
        )
    )

    var recording: Recording? by remember { mutableStateOf<Recording?>(null) }
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
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp + dimensionResource(id = R.dimen.dim_10)

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

    var confirmDeletion by remember{
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(StopWatchEvent.onReset)
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
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_20)))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight)
                    .padding(start = dimensionResource(id = R.dimen.dim_20), top = dimensionResource(id = R.dimen.dim_50), end = dimensionResource(id = R.dimen.dim_20), bottom = dimensionResource(id = R.dimen.dim_20))
            ) {

                Box(
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.dim_40))
                        .height(dimensionResource(id = R.dimen.dim_40))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)))
                        .background(Color.Black.copy(alpha = 0.9f))
                        .clickable {
                            // Do something when the box is clicked
                            viewModel.onEvent(StopWatchEvent.onReset)
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
                if (!recordingStarted.value) {

                    Box(
                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.dim_60))
                            .height(dimensionResource(id = R.dimen.dim_40))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)))
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
                                .width(dimensionResource(id = R.dimen.dim_20))
                                .height(dimensionResource(id = R.dimen.dim_20))
                                .align(Alignment.Center)
                        )
                    }
                }


                Column(modifier = Modifier.align(Alignment.BottomCenter), horizontalAlignment = Alignment.CenterHorizontally)
                {
                    if (confirmDeletion)
                    {
                        AlertDialog(onDismissRequest = { confirmDeletion = !confirmDeletion },
                            modifier = Modifier.background(Color(0x55FFBBBB), RoundedCornerShape(dimensionResource(id = R.dimen.dim_5))).padding(dimensionResource(id = R.dimen.dim_5)),) {

                            Column(horizontalAlignment = Alignment.CenterHorizontally){
                                Text(text = "Do you want to delete the last clip made?")

                                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                                    Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                        onClick = {
                                        confirmDeletion = !confirmDeletion

                                    }) {
                                        Text(text="Cancel")
                                    }

                                    Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                        onClick = {
                                        uris.removeAt(uris.lastIndex)
                                        viewModel.onEvent(StopWatchEvent.onRecall)
                                        removeUpdate(true)
                                        canDelete = false
                                        confirmDeletion = !confirmDeletion
                                    }) {
                                        Text(text="Confirm")
                                    }
                                }
                            }

                        }
                    }
                    
                    //val stopWatch = Builder(Stopwatch)
                    //val sdf = SimpleDateFormat("mm:ss")

                    // on below line we are creating a variable for
                    // current date and time and calling a simple
                    // date format in it.
                    //val currentDateAndTime = sdf.format(Date())
                    var time = if (state.minutes < 10)
                    {
                        "0${state.minutes}"
                    }
                    else
                    {
                        state.minutes.toString()
                    }

                    time += if (state.seconds < 10)
                    {
                        ":0${state.seconds}"
                    }
                    else
                    {
                        ":${state.seconds}"
                    }

                    Text(text = time, modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_10)), color = Color.White)
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
                                    recording?.stop() //Need the object refs to be consistent
                                }
                            }
                            //navController.navigate("GALLERY_VIEW")
                        },
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.dim_80)),
                        interactionSource = interactionSource,
                        enabled = state.minutes < 1
                    ) {

                        if(state.minutes >= 1)
                        {
                            if (recordingStarted.value)
                            {
                                recordingStarted.value = false
                                viewModel.onEvent(StopWatchEvent.onStop)
                                recording?.stop()
                            }
                        }

                        if (holdOrPress) {
                            if (isPressed && state.minutes < 1) {
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
                            clipUpdate = clipUpdate,
                            state = state
                        )


                        /*Icon(
                            painter = painterResource(if (recordingStarted.value) R.drawable.story_user else R.drawable.save),
                            contentDescription = "",
                            modifier = Modifier.size(dimensionResource(id = R.dimen.dim_64))
                        )*/
                    }
                }

                Row(modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dim_80))
                    .align(Alignment.BottomStart), verticalAlignment = Alignment.CenterVertically)
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
                                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_5)))
                                    .then(
                                        Modifier
                                            .size(dimensionResource(id = R.dimen.dim_64))
                                            .border(dimensionResource(id = R.dimen.dim_2), Color.White, RoundedCornerShape(dimensionResource(id = R.dimen.dim_5)))
                                    )
                                    .clickable {
                                        navController.navigate("GALLERY_VIEW/${stateString.encodeUtf8()}")
                                    }
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dim_5)))
                                    .then(
                                        Modifier
                                            .size(dimensionResource(id = R.dimen.dim_64))
                                            .border(dimensionResource(id = R.dimen.dim_2), Color.White, RoundedCornerShape(dimensionResource(id = R.dimen.dim_5)))
                                    )
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
                )
                {
                    if (uris.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .width(dimensionResource(id = R.dimen.dim_40))
                                .height( dimensionResource(id = R.dimen.dim_38))
                                .background(Color.Gray, shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_10)))
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
                            onClick = { /*TODO*/ },
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
