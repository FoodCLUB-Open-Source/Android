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

        try {
            val counter = AtomicInteger(0)
            val outputFile = File(
                outputDirectory,
                "output_${System.currentTimeMillis()}_${counter.getAndIncrement()}.mp4"
            )

            val cmd = StringBuilder()
            filePaths.forEach {
                cmd.append(" -i ").append(it)
            }

            cmd.append(" -filter_complex \"")
            for (x in filePaths.indices) cmd
                .append("[")
                .append(x)
                .append(":v] [")
                .append(x)
                .append(":a] ")
            cmd
                .append("concat=n=")
                .append(filePaths.size)
                .append(":v=1:a=1 [outv] [outa]\"")
                .append(" -map \"[outv]\" -map \"[outa]\" ")
                .append("-c:v h264 ")
                .append("-b:v 5M ")
                .append(outputFile.absolutePath)

            val session: FFmpegSession = FFmpegKit.execute(cmd.toString())

            if (ReturnCode.isSuccess(session.returnCode) && session.state == SessionState.COMPLETED) {
                deleteFileIfExists(filePaths)
                return outputFile.absolutePath
            } else {
                deleteFileIfExists(filePaths)
                Log.e(TAG, "Video merging failed. FFmpeg return code: ${session.returnCode}")
                Log.e(TAG, "Video merging failed. ${session.allLogs}")
            }
        } catch (e: Exception) {
            deleteFileIfExists(filePaths)
            Log.e(TAG, "Exception during video merging", e)
        }
        return null
    }

    private fun deleteFileIfExists(filePaths: List<String>) {
        filePaths.forEach { filePath ->
            val file = File(filePath)
            if (file.exists()) {
                file.delete()
            }
        }
    }
}
