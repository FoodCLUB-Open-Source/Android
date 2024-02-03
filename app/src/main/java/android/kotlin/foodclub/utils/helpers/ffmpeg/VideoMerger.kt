package android.kotlin.foodclub.utils.helpers.ffmpeg

import android.content.Context
import android.os.Environment
import android.util.Log
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.FFmpegSession
import com.arthenica.ffmpegkit.ReturnCode
import com.arthenica.ffmpegkit.SessionState
import java.io.File
import java.util.concurrent.atomic.AtomicInteger

class VideoMerger(private val context: Context) {
    val TAG = VideoMerger::class.java.simpleName
    fun mergeVideos(filePaths: List<String>): String? {
        val outputDirectory = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)

        if (outputDirectory != null) {
            try {
                val counter = AtomicInteger(0)
                val outputFile = File(outputDirectory, "output_${System.currentTimeMillis()}_${counter.getAndIncrement()}.mp4")

                val cmd = StringBuilder()
                filePaths.forEach { cmd.append(" -i ").append(it) }
                cmd.append(" -filter_complex \"")
                for (x in filePaths.indices) cmd.append("[").append(x).append(":v] [").append(x).append(":a] ")
                cmd.append("concat=n=").append(filePaths.size).append(":v=1:a=1 [outv] [outa]\"")
                    .append(" -map \"[outv]\" -map \"[outa]\" ").append(outputFile.absolutePath)

                val session: FFmpegSession = FFmpegKit.execute(cmd.toString())

                if (ReturnCode.isSuccess(session.returnCode) && session.state == SessionState.COMPLETED) {
                    return outputFile.absolutePath
                } else {
                    // Handle failure
                    Log.e(TAG, "Video merging failed. FFmpeg return code: ${session.returnCode}")
                }
            } catch (e: Exception) {
                // Handle other exceptions
                Log.e(TAG, "Exception during video merging", e)
            }
        }

        return null
    }

//    private fun getTextFile(context: Context, filePaths: List<String>): File {
//        val fileName = System.currentTimeMillis().toString() + "inputFiles"
//        File.createTempFile(fileName, ".txt", context.cacheDir)
//        val textFile = File(context.cacheDir, "$fileName.txt")
//        val writer = textFile.printWriter()
//
//        val builder = StringBuilder()
//        filePaths.forEach {
//            builder.append("file \'").append(it).append("\'\n")
//        }
//        builder.deleteCharAt(builder.length - 1)
//
//        writer.use { out -> out.print(builder.toString()) }
//        return textFile
//    }
}