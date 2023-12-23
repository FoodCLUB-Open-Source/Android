package android.kotlin.foodclub.utils.helpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.FFmpegSession
import com.arthenica.ffmpegkit.ReturnCode
import com.arthenica.ffmpegkit.SessionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * This is class is used to overlay an image on top of a video
 * For testing purposes, you can find the output file in: /data/user/0/packagename/files
 * videoFile type is going to be changed to Uri in the future.
 * Example usage:
 *    val context = LocalContext.current
 *    val ffmpegUtil = FFmpegUtil(context)
 *    val videoFile = ffmpegUtil.createTempVideoFile("daniel_vid2.mp4")
 *    val imageFile = ffmpegUtil.createTempImageFile(R.drawable.creative)
 *    ffmpegUtil.executeFFmpegCommand(
 *        videoFile!!,
 *        imageFile!!,
 *        xCoordinate = 100,
 *        yCoordinate = 100,
 *        startSecond = null,
 *        endSecond = null
 *    )
 * */
class FFmpegUtil(private val context: Context) {

    private val outputFileName = "output.mp4"
    private val outputDirectoryPath = context.filesDir.path
    private val outputPath = "$outputDirectoryPath/$outputFileName"
    private val TAG = FFmpegUtil::class.java.simpleName

    /**
     * Executes an FFmpeg command to overlay an image onto a video and saves the result to a specified output path.
     * The function uses runBlocking to launch a coroutine for executing the FFmpeg command.
     * This allows the function to block the current thread until the coroutine is completed.
     *
     * @param videoFile The input video file.
     * @param imageFile The input image file to overlay onto the video.
     * @param xCoordinate The X-coordinate for overlaying the image on the video.
     * @param yCoordinate The Y-coordinate for overlaying the image on the video.
     * @param startSecond The starting second for the overlay. If null, the overlay begins from the start of the video.
     * @param endSecond The ending second for the overlay. If null, the overlay extends to the end of the video.
     */
    fun executeFFmpegCommand(
        videoFile: File,
        imageFile: File,
        xCoordinate: Int,
        yCoordinate: Int,
        startSecond: Int?,
        endSecond: Int?
    ) = runBlocking {
        launch(Dispatchers.Default) {
            // if start and end times is given, image will be shown at that time range
            // else image will display full-length of the video
            val timeRangeFilter = when {
                startSecond != null && endSecond != null ->
                    "'between(t,$startSecond,$endSecond)'"
                else ->
                    "'1'"
            }

            val command = (
                    "-i $videoFile " +
                    "-i $imageFile " +
                    "-filter_complex \"[0:v][1:v]overlay=$xCoordinate:$yCoordinate:enable=$timeRangeFilter\" " +
                    "-c:a copy " + // Copies the audio codec from the input to the output without re-encoding.
                    "-c:v h264 " +  // Specifies the video codec for the output as H.264
                    "-b:v 5M " + // Sets the target video bitrate to 5 megabits per second
                    "-y " +  // overwrite the output file without confirmation
                    outputPath
                    )
            val session: FFmpegSession = FFmpegKit.execute(command)

            if (ReturnCode.isSuccess(session.returnCode) && session.state == SessionState.COMPLETED) {
                Log.i(TAG, "SUCCESS")
            } else {
                // Handle failure
                Log.i(TAG, "ERROR ${session.logs}")
                Log.i(TAG, "ERROR ${session.output}")
            }
            deleteTemporaryFile(videoFile)
            deleteTemporaryFile(imageFile)
        }
    }

    /**
     * Creates a temporary video file by copying the content of a video file from the assets folder.
     *
     * @param assetFileName The name of the video file in the assets folder.
     * @return A File object representing the temporary video file, or null if an error occurs.
     */
    fun createTempVideoFile(assetFileName: String): File? {
        val assetManager = context.assets

        try {
            // Open the video file from the assets folder
            val inputStream: InputStream = assetManager.open(assetFileName)

            // Create a temporary file
            val tempFile = File.createTempFile("temp_video", ".mp4", context.cacheDir)

            // Copy the content of the video from the assets folder to the temporary file
            val outputStream = FileOutputStream(tempFile)
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }

            // Close streams
            inputStream.close()
            outputStream.close()
            return tempFile
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * Creates a temporary image file by decoding a drawable resource and compressing it into a file.
     */
    fun createTempImageFile(drawableResId: Int): File? {
        // check if the input drawable is xml
        val convertedFile = convertDrawableXMLToPng(drawableResId)

        if (convertedFile != null) {
            return convertedFile
        }

        // Fallback to the original logic if conversion fails
        try {
            val inputStream = context.resources.openRawResource(drawableResId)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            // Create a temporary file in the cache directory
            val tempFile = File.createTempFile("temp_image", ".png", context.cacheDir)

            // Compress the Bitmap into the temporary file using PNG format
            val outputStream = FileOutputStream(tempFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

            inputStream.close()
            outputStream.close()

            return tempFile
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * Converts a drawable resource XML to a PNG file and returns the File object.
     */
    private fun convertDrawableXMLToPng(drawableResId: Int): File? {
        val drawable: Drawable? = ContextCompat.getDrawable(context, drawableResId)

        if (drawable != null) {
            try {
                val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)

                // Create a temporary file in the cache directory
                val tempFile = File.createTempFile("temp_image", ".png", context.cacheDir)

                val outputStream = FileOutputStream(tempFile)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()

                return tempFile
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return null
    }

    /**
     * After FFmpeg command is finished, remove temporary files from storage.
     * */
    private fun deleteTemporaryFile(file: File) {
        if (file.exists()) {
            try {
                val deleted = file.delete()
                if (deleted) {
                    Log.i(TAG, "Temporary file ${file.name} deleted successfully.")
                } else {
                    Log.e(TAG, "Failed to delete temporary file ${file.name}.")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error while deleting file ${file.name}: ${e.localizedMessage}")
            }
        }
    }

}