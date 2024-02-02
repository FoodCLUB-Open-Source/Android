package android.kotlin.foodclub.utils.helpers.ffmpeg

import android.content.Context
import android.os.Environment
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.FFmpegSession
import com.arthenica.ffmpegkit.ReturnCode
import com.arthenica.ffmpegkit.SessionState
import java.io.File

class VideoMerger {
    fun mergeVideos(context: Context, filePaths: List<String>): String? {
        val outputDirectory = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        if (outputDirectory != null) {
            val outputFile = File(outputDirectory, "output${System.currentTimeMillis()}.mp4")
//            val textFilePath = getTextFile(context, filePaths).absolutePath

            val cmd = StringBuilder()
            filePaths.forEach { cmd.append(" -i ").append(it) }
            cmd.append(" -filter_complex \"")
            for (x in filePaths.indices) cmd.append("[").append(x).append(":v] [").append(x).append(":a] ")
            cmd.append("concat=n=").append(filePaths.size).append(":v=1:a=1 [outv] [outa]\"")
                .append(" -map \"[outv]\" -map \"[outa]\" ").append(outputFile.absolutePath)
            val session: FFmpegSession = FFmpegKit.execute(cmd.toString())

            if (ReturnCode.isSuccess(session.returnCode) && session.state == SessionState.COMPLETED) {
                return outputFile.absolutePath
            }
        }
        return null
    }

    private fun getTextFile(context: Context, filePaths: List<String>): File {
        val fileName = System.currentTimeMillis().toString() + "inputFiles"
        File.createTempFile(fileName, ".txt", context.cacheDir)
        val textFile = File(context.cacheDir, "$fileName.txt")
        val writer = textFile.printWriter()

        val builder = StringBuilder()
        filePaths.forEach {
            builder.append("file \'").append(it).append("\'\n")
        }
        builder.deleteCharAt(builder.length - 1)

        writer.use { out -> out.print(builder.toString()) }
        return textFile
    }
}