package android.kotlin.foodclub.views.home.home.foodSNAPS

import android.kotlin.foodclub.utils.composables.PhotoTakenPreview
import android.kotlin.foodclub.utils.composables.TakePhotoPreview
import android.kotlin.foodclub.utils.helpers.takePhoto
import android.kotlin.foodclub.utils.helpers.uriToFile
import android.kotlin.foodclub.viewModels.home.home.HomeEvents
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun TakeSnapView(
    modifier: Modifier = Modifier,
    events: HomeEvents,
    navController: NavController
){
    val context = LocalContext.current
    BackHandler {
        navController.popBackStack()
    }
    val TAG = "TakeSnapPhotoView"
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }

    Box(modifier= modifier.fillMaxSize()) {
        if (photoUri != null){
            PhotoTakenPreview(
                image = photoUri!!,
                navController = navController,
                onSaveClick = {
                    val file = uriToFile(photoUri!!, context)
                    if (file != null){
                        events.postSnap(file)
                        navController.popBackStack()
                    }
                    else {
                        Log.e(TAG,"EMPTY FILE")
                    }
                },
                onCancelClick = {
                    photoUri = null
                }
            )
        }else{
            TakePhotoPreview(
                controller = controller,
                navController = navController,
                onTakePhoto = {
                    takePhoto(
                        controller,
                        { photoUri = it},
                        context
                    )
                }
            )
        }
    }
}