package android.kotlin.foodclub.utils.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun CameraPermissionErrorBox(
    onGoToSettingsClicked: () -> Unit,
    onAcknowledgeClicked: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = {  },
        title = { Text(text = "Camera Permission Required") },
        text = { Text(text = "Please allow camera permission to use this feature") },
        confirmButton = {
            TextButton(
                onClick = {
                    onGoToSettingsClicked.invoke()
                }
            ) {
                Text(text = "Go to settings")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onAcknowledgeClicked.invoke()
                }
            ) {
                Text(text = "Acknowledge")
            }
        },
    )
}
