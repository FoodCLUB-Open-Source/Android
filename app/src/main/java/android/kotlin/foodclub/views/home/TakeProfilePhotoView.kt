package android.kotlin.foodclub.views.home

import android.kotlin.foodclub.utils.composables.PhotoTakenPreview
import android.kotlin.foodclub.utils.composables.TakePhotoPreview
import android.kotlin.foodclub.utils.helpers.takePhoto
import android.kotlin.foodclub.utils.helpers.uriToFile
import android.kotlin.foodclub.viewModels.home.ProfileViewModel
import android.net.Uri
import android.util.Log
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun TakeProfilePhotoView(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    navController: NavController
){
    val context = LocalContext.current
    val dataStore = viewModel.storeData
    val scope = rememberCoroutineScope()
    val TAG = "TakeProfilePhotoView"

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
                    val file = uriToFile(photoUri!!, context)
                    if (file != null){
                        viewModel.updateUserProfileImage(
                            viewModel.myUserId.value,
                            file,
                            photoUri!!
                        )

                        scope.launch {
                            dataStore.storeImage(photoUri!!.toString())
                        }

                        navController.popBackStack()
                    }
                    else {
                        Log.i(TAG,"EMPTY FILE")
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