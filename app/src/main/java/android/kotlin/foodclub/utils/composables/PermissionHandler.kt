import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState

class PermissionHandler(private val activity: Activity) {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun requestPermissions(
        onAllPermissionsGranted: () -> Unit,
        onPermissionsDenied: () -> Unit,
        onShowRationale: () -> Unit
    ) {
        val permissionState = rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
//fix these bugs
//        val requestPermissionLauncher =
//            activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
//                if (permissions.all { it.value }) {
//                    // All permissions granted
//                    onAllPermissionsGranted.invoke()
//                } else {
//                    // Permissions denied
//                    onPermissionsDenied.invoke()
//                }
        val requestPermissionLauncher =
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissions ->
                if (permissions) {
                    // All permissions granted
                    onAllPermissionsGranted.invoke()
                } else {
                    // Permissions denied
                    onPermissionsDenied.invoke()
                }
            }

        when {
            permissionState.allPermissionsGranted -> {
                // All required permissions are granted
                onAllPermissionsGranted.invoke()
            }
            permissionState.shouldShowRationale -> {
                // Show rationale for permission request
                onShowRationale.invoke()

                requestPermissionLauncher.launch(permissionState.permissions.toTypedArray())
            }
            else -> {
                // Permissions are denied
                onPermissionsDenied.invoke()
            }
        }
    }
}
