package android.kotlin.foodclub.utils.composables


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

/**
 * PermissionErrorBox composable will ask the user for permission before run content
 *  Input:
 *      @permissions: is an array of permissions string
 *      @title: title of the dialog
 *      @rational: reason for the user why we need these permissions
 *      @navController: when user click go back it will return back to previous screen
 *      @context: LocalContext.current
 *      @content: Composable content will work if permissions are allowed
 *
 *  Output:
 *      Request permissions if the permissions are not allowed then
 *      diplay a dialog to ask the user go to setting to allow permissions
 **/
@Composable
fun PermissionErrorBox(
    permissions: Array<String>,
    title: String,
    rational: String,
    navController: NavController,
    context: Context,
    content: @Composable () -> Unit
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { isGrantedList: Map<String, @JvmSuppressWildcards Boolean> ->
        showDialog = false
        for (isGranted in isGrantedList) {
            if (!isGranted.value) {
                showDialog = true
                break
            }
        }
    }

    for (permission in permissions) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) -> {
                showDialog = false
            }

            else -> {
                showDialog = true
                break
            }
        }
    }

    LaunchedEffect(showDialog) {
        when {
            showDialog -> {
                launcher.launch(permissions)
            }
        }
    }

    PermissionRequestDialog(
        visibleState = showDialog,
        title = title,
        text = rational,
        onDismissRequest = { navController.popBackStack() },
        onConfirmRequest = {
            val i = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", context.packageName, null)
            )
            context.startActivity(i)
            navController.popBackStack()
        }
    )

    if (!showDialog) {
        content()
    }
}


@Composable
fun PermissionRequestDialog(
    visibleState: Boolean,
    title: String,
    text: String,
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
) {
    if (visibleState) {
        AlertDialog(
            icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = ""
                )
            },
            title = { Text(text = title) },
            text = { Text(text = text) },
            onDismissRequest = { onDismissRequest() },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmRequest()
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
                        onDismissRequest()
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