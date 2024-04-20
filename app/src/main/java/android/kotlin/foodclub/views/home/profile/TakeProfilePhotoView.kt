package android.kotlin.foodclub.views.home.profile

import android.Manifest
import android.kotlin.foodclub.R
import android.kotlin.foodclub.utils.composables.PermissionErrorBox
import android.kotlin.foodclub.utils.composables.PhotoTakenPreview
import android.kotlin.foodclub.utils.composables.TakePhotoPreview
import android.kotlin.foodclub.utils.helpers.takePhoto
import android.kotlin.foodclub.utils.helpers.uriToFile
import android.kotlin.foodclub.viewModels.home.profile.ProfileEvents
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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun TakeProfilePhotoView(
    modifier: Modifier = Modifier,
    events: ProfileEvents,
    state: ProfileState,
    navController: NavController
) {
    val context = LocalContext.current
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

    PermissionErrorBox(
        permissions = arrayOf(Manifest.permission.CAMERA),
        title = stringResource(id = R.string.Camera_permission_require_message_title),
        rational = stringResource(id = R.string.Camera_permission_require_rational),
        navController = navController,
        context = context
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            if (photoUri != null) {
                PhotoTakenPreview(
                    image = photoUri!!,
                    onSaveClick = {
                        val file = uriToFile(photoUri!!, context)
                        if (file != null) {
                            events.updateUserProfileImage(
                                file,
                                photoUri!!
                            )

                            scope.launch {
                                state.dataStore?.storeImage(photoUri!!.toString())
                            }

                            navController.popBackStack()
                        } else {
                            Log.i(TAG, "EMPTY FILE")
                        }
                    },
                    onCancelClick = {
                        photoUri = null
                    }
                )
            } else {
                TakePhotoPreview(
                    controller = controller,
                    navController = navController,
                    onTakePhoto = { previewUri ->
                        photoUri = previewUri
                        takePhoto(
                            controller,
                            { photoUri = it },
                            context
                        )
                    }
                )
            }
        }
    }
}