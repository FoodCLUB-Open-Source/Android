package android.kotlin.foodclub.utils.composables


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.kotlin.foodclub.R
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController


@Composable
fun CameraPermissionErrorBox(
    permission: String,
    title: String,
    rational: String,
    navController: NavController,
    context: Context
) {

//    val audioPermission by remember {
//        mutableStateOf(
//            PackageManager.PERMISSION_GRANTED ==
//                    ContextCompat.checkSelfPermission(
//                        context,
//                        Manifest.permission.RECORD_AUDIO
//                    )
//        )
//    }

    var showDialog by remember {
        mutableStateOf(true)
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        showDialog = !isGranted
    }

    LaunchedEffect(PackageManager.PERMISSION_GRANTED){
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) -> {
                showDialog = false
            }
            else -> {
                launcher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = ""
                )
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = rational)
            },
            onDismissRequest = {
                navController.popBackStack()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val i = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                        context.startActivity(i)
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = ShapeDefaults.Medium
                        )
                ) {
                    Text(
                        text = stringResource(id = R.string.go_to_setting),
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .background(
                            color = Color.Unspecified,
                            shape = ShapeDefaults.Medium
                        )
                ) {
                    Text(
                        text = stringResource(id = R.string.go_back),
                        fontSize = 16.sp,
                    )
                }
            }
        )
    }
}
