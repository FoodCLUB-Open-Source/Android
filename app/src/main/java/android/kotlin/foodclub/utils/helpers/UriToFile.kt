package android.kotlin.foodclub.utils.helpers

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import java.io.File
import java.io.FileOutputStream

fun uriToFile(uri: Uri, context: Context): File? {

    val contentResolver = context.contentResolver
    // Retrieve the display name from the URI.
    val displayName = getDisplayNameFromUri(uri, contentResolver)

    // Provide a default name if the display name is null or empty.
    val defaultFileName = "default_file_name.jpg"
    val fileName = displayName.ifEmpty { defaultFileName }

    // Create a file in the app's cache directory with the file name.
    val file = File(context.cacheDir, fileName)

    try {
        val inputStream = contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)

        if (inputStream != null) {
            inputStream.copyTo(outputStream)

            inputStream.close()
            outputStream.close()
            return file
        }
    } catch (e: Exception) {
        Log.e("MYTAG", "Error while converting URI to File", e)
        Log.e("MYTAG", "Error while converting URI to File", e.cause)
        Log.e("MYTAG", "Error while converting URI to File", Throwable(e.message))
    }
    return null
}

private fun getDisplayNameFromUri(uri: Uri, contentResolver: ContentResolver): String {
    var displayName = ""
    val cursor = contentResolver.query(uri, null, null, null, null)

    cursor?.use {
        if (it.moveToFirst()) {
            val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (displayNameIndex >= 0) {
                displayName = it.getString(displayNameIndex)
            }
        }
    }

    return displayName
}
