package android.kotlin.foodclub.views.home

import android.content.Context
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.utils.composables.CameraPreview
import android.kotlin.foodclub.utils.helpers.uriToFile
import android.kotlin.foodclub.viewModels.home.ProfileViewModel
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun TakeProfilePhotoView(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController
){
    val context = LocalContext.current
    val dataStore = viewModel.storeData
    val scope = rememberCoroutineScope()

    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (photoUri != null){
            PhotoTakenPreview(
                image = photoUri!!,
                onSaveClick = {
                    // convert image taken with camera to File and make a PUT request to backend
                    val file = uriToFile(photoUri!!, context)
                    if (file != null){
                        viewModel.updateUserProfileImage(
                            viewModel.myUserId.value,
                            file,
                            photoUri!!
                        )
                        // save image to DataStore, when we navigate back,
                        // LaunchedEffect on ProfileView will catch this and apply on profile image
                        scope.launch {
                            dataStore.storeImage(photoUri!!.toString())
                        }
                        navController.popBackStack()
                    }else{
                        Log.i("MYTAG","EMPTY FILE")
                    }
                },
                onCancelClick = {
                    photoUri = null
                }
            )
        }else{
            CameraPreview(
                controller = controller,
                modifier = modifier
                    .fillMaxSize()
            )

            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(30.dp)
            ) {
                Icon(
                    painterResource(id = R.drawable.close),
                    contentDescription = "Close camera",
                    modifier.size(30.dp),
                    tint = foodClubGreen
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(30.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(
                    onClick = {
                        controller.cameraSelector =
                            if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                                CameraSelector.DEFAULT_FRONT_CAMERA
                            } else CameraSelector.DEFAULT_BACK_CAMERA
                    }
                ) {
                    Icon(
                        painterResource(id = R.drawable.baseline_cameraswitch_24),
                        contentDescription = "Switch camera",
                        modifier = modifier.size(30.dp),
                        tint = foodClubGreen
                    )
                }
                IconButton(
                    onClick = {
                        takePhoto(
                            controller = controller,
                            onPhotoTaken = { photoUri = it },
                            context
                        )
                    }
                ) {
                    Icon(
                        painterResource(id = R.drawable.take_photo),
                        contentDescription = "Take photo",
                        modifier = modifier.size(30.dp),
                        tint = foodClubGreen,
                    )
                }
            }
        }
    }
}


@Composable
fun PhotoTakenPreview(
    image: Uri,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Card {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = remember { mutableStateOf(image) }.value,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
            )

            Row(
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(bottom = 40.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    onClick = {
                        onCancelClick()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(40.dp))

                IconButton(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(foodClubGreen),
                    onClick = {
                        onSaveClick()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Default.Check,
                        contentDescription = "",
                        tint = Color.White
                    )
                }

            }
        }
    }
}

private fun takePhoto(
    controller: LifecycleCameraController,
    onPhotoTaken: (Uri) -> Unit,
    context: Context
) {
    val outputDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    if (outputDirectory != null) {
        val outputFile = File(outputDirectory, "captured_image.jpg")

        val outputOptions = ImageCapture.OutputFileOptions.Builder(outputFile)
            .build()

        controller.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    onPhotoTaken(Uri.fromFile(outputFile))
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.i("MYTAG", "Couldn't save photo: ", exception)
                }
            }
        )
    } else {
        Log.i("MYTAG", "Failed to obtain output directory")
    }
}
