package android.kotlin.foodclub.utils.composables

import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.navigation.HomeOtherRoutes
import android.kotlin.foodclub.utils.helpers.createGalleryLauncher
import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun TakePhotoPreview(
    modifier: Modifier = Modifier,
    controller: LifecycleCameraController,
    navController: NavController,
    onTakePhoto: (Uri?) -> Unit
) {
    var flashEnabled by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val galleryLauncher = createGalleryLauncher(
        context = context,
        onResult = { onTakePhoto(it) },
    )
    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(
            controller = controller,
            modifier = modifier
                .fillMaxSize()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.dim_15)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.dim_30))
            ) {
                Icon(
                    painterResource(id = R.drawable.close),
                    contentDescription = stringResource(id = R.string.close_camera),
                    modifier.size(dimensionResource(id = R.dimen.dim_30)),
                    tint = Color.White
                )
            }

            IconButton(
                onClick = {
                    flashEnabled = !flashEnabled
                    controller.enableTorch(flashEnabled)
                },
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.dim_30))
            ) {
                Icon(
                    if (flashEnabled) painterResource(id = R.drawable.flash_open_icon)
                    else painterResource(id = R.drawable.flash_icon_closed),
                    contentDescription = stringResource(id = R.string.camera_flash),
                    modifier.size(dimensionResource(id = R.dimen.dim_30)),
                    tint = Color.White
                )
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .align(Alignment.BottomCenter)
                .padding(dimensionResource(id = R.dimen.dim_25)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    galleryLauncher.launch(arrayOf("image/*"))
                }
            ) {
                Icon(
                    painterResource(id = R.drawable.select_from_gallery),
                    contentDescription = stringResource(id = R.string.select_from_gallery),
                    modifier = modifier.size(dimensionResource(id = R.dimen.dim_30)),
                    tint = Color.White,
                )
            }

            CustomCameraButton {
                onTakePhoto(null)
            }

            IconButton(
                onClick = {
                    controller.cameraSelector =
                        if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                            CameraSelector.DEFAULT_FRONT_CAMERA
                        } else CameraSelector.DEFAULT_BACK_CAMERA
                }
            ) {
                Icon(
                    painterResource(id = R.drawable.switch_camera),
                    contentDescription = stringResource(id = R.string.switch_camera),
                    modifier = modifier.size(dimensionResource(id = R.dimen.dim_30)),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun PhotoTakenPreview(
    image: Uri,
    navController: NavController,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
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

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = dimensionResource(id = R.dimen.dim_15))
        ) {
            IconButton(
                onClick = {
                    navController.navigate(route = HomeOtherRoutes.TakeSnapPhotoView.route)
                },
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.dim_30))
            ) {
                Icon(
                    painterResource(id = R.drawable.close),
                    contentDescription = stringResource(id = R.string.close_camera),
                    Modifier.size(dimensionResource(id = R.dimen.dim_30)),
                    tint = Color.White
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .align(Alignment.BottomCenter)
                .padding(
                    start = dimensionResource(id = R.dimen.dim_25),
                    end = dimensionResource(id = R.dimen.dim_25),
                    top = dimensionResource(id = R.dimen.dim_10),
                    bottom = dimensionResource(id = R.dimen.dim_30),
                ),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.dim_20))
                    .clickable {
                        onCancelClick()
                    },
                text = stringResource(id = R.string.camera_redo),
                fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                fontWeight = FontWeight(600),
                lineHeight = dimensionResource(id = R.dimen.dim_40).value.sp,
                fontFamily = Montserrat,
                color = Color.White
            )
            Text(
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.dim_20))
                    .clickable {
                        onSaveClick()
                    },
                text = stringResource(id = R.string.camera_done),
                fontSize = dimensionResource(id = R.dimen.fon_18).value.sp,
                fontWeight = FontWeight(600),
                lineHeight = dimensionResource(id = R.dimen.dim_40).value.sp,
                fontFamily = Montserrat,
                color = Color.White
            )
        }
    }
}

@Composable
fun CustomCameraButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(dimensionResource(id = R.dimen.dim_75))
            .clip(CircleShape)
            .background(Color.White)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dim_60))
                .clip(CircleShape)
                .background(Color.Black)
                .border(BorderStroke(dimensionResource(id = R.dimen.dim_1), Color.Black)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dim_55))
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }
    }
}