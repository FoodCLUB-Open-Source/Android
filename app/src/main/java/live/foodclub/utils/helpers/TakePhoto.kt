package live.foodclub.utils.helpers

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import java.io.File

/**
 * This function uses LifecycleCameraController to take photos
 * saves the captured image to new directory
 * creates a Uri from the saved file
 * passes it back to onPhotoTaken lambda function
 * **/
fun takePhoto(
    controller: LifecycleCameraController,
    onPhotoTaken: (Uri) -> Unit,
    context: Context
) {
    val TAG = "TakePhoto"
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
                    Log.i(TAG, "Couldn't save photo: ", exception)
                }
            }
        )
    } else {
        Log.i(TAG, "Failed to obtain output directory")
    }
}
