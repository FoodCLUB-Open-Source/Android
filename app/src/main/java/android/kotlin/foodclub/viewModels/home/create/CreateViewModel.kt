package android.kotlin.foodclub.viewModels.home.create

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.media3.common.C
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.transformer.Composition
import androidx.media3.transformer.EditedMediaItem
import androidx.media3.transformer.EditedMediaItemSequence
import androidx.media3.transformer.ExportException
import androidx.media3.transformer.ExportResult
import androidx.media3.transformer.ProgressHolder
import androidx.media3.transformer.TransformationRequest
import androidx.media3.transformer.Transformer
import java.io.File
import java.io.IOException
import java.util.UUID


/**
 * DO NOT USE THIS VIEW MODEL - memory leaks!!!
 * */
class CreateViewModel : ViewModel() {

    private val TAG = "MainActivity"

    var activity: Activity? = null
    var trimStartMs: Long = 0
    var trimEndMs: Long = 0

    fun setApplicationData(activity: Activity, trimStartMs : Long, trimEndMs:Long){
        this.activity = activity
        this.trimStartMs = trimStartMs
        this.trimEndMs = trimEndMs
    }

    @OptIn(markerClass = arrayOf(UnstableApi::class))
    public fun startExport() {
        var externalCacheFile: File? = null
        val transformer: Transformer? = null

        requestReadVideoPermission()
        try {
            externalCacheFile = createExternalCacheFile(UUID.randomUUID().toString() + "transformer-output.mp4")
        } catch (e: IOException) {
            throw IllegalStateException(e)
        }
        val filePath: String = externalCacheFile!!.getAbsolutePath()
        val mediaItem: androidx.media3.common.MediaItem = createMediaItem( Uri.parse("https://storage.googleapis.com/exoplayer-test-media-1/mp4/portrait_avc_aac.mp4"))
        try {
            val transformer: Transformer = createTransformer(Uri.parse("https://storage.googleapis.com/exoplayer-test-media-1/mp4/portrait_avc_aac.mp4"), filePath)
            val composition: Composition = createComposition(mediaItem)
            transformer.start(composition, filePath)
        } catch (e: PackageManager.NameNotFoundException) {
            throw IllegalStateException(e)
        }
        val mainHandler: Handler = Handler(Looper.getMainLooper())
        val progressHolder = ProgressHolder()
        mainHandler.post(
            object : Runnable {
                override fun run() {
                    if (transformer != null && transformer.getProgress(progressHolder) != Transformer.PROGRESS_STATE_NOT_STARTED) {
                        mainHandler.postDelayed( /* r= */this,  /* delayMillis= */500)
                    }
                }
            })
    }

    @OptIn(markerClass = arrayOf(UnstableApi::class))
    public  fun requestReadVideoPermission() {
        val permission =
            if (Util.SDK_INT >= 33) Manifest.permission.READ_MEDIA_VIDEO else Manifest.permission.READ_EXTERNAL_STORAGE
        if (ActivityCompat.checkSelfPermission(activity?.applicationContext!!, permission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            activity?.let { ActivityCompat.requestPermissions(it as Activity, arrayOf(permission),  /* requestCode= */0) }
        }
    }
    @Throws(IOException::class)
    public  fun createExternalCacheFile(fileName: String): File? {
        val file: File = File(activity?.applicationContext!!.getExternalCacheDir(), fileName)
        check(!(file.exists() && !file.delete())) { "Could not delete the previous export output file" }
        check(file.createNewFile()) { "Could not create the export output file" }
        return file
    }

    public  fun createMediaItem(uri: Uri): androidx.media3.common.MediaItem {
        val mediaItemBuilder = androidx.media3.common.MediaItem.Builder().setUri(uri)
        if (trimStartMs != C.TIME_UNSET && trimEndMs != C.TIME_UNSET) {
            mediaItemBuilder.setClippingConfiguration(
                androidx.media3.common.MediaItem.ClippingConfiguration.Builder()
                    .setStartPositionMs(trimStartMs)
                    .setEndPositionMs(trimEndMs)
                    .build()
            )
        }
        return mediaItemBuilder.build()
    }


    @OptIn(markerClass = arrayOf(UnstableApi::class))
    public  fun createTransformer(inputUri: Uri, filePath: String): Transformer {
        val transformerBuilder = Transformer.Builder(activity?.applicationContext!!)
        val requestBuilder = TransformationRequest.Builder()
        requestBuilder.setVideoMimeType(MimeTypes.VIDEO_H264)
        requestBuilder.setAudioMimeType(MimeTypes.AUDIO_AAC)
        transformerBuilder.setTransformationRequest(requestBuilder.build())
        return transformerBuilder
            .addListener(
                object : Transformer.Listener {
                    override fun onCompleted(composition: Composition, exportResult: ExportResult) {
                        Log.d(
                            TAG,
                            "Output file path: file://$filePath"
                        )
                        Toast.makeText(activity?.applicationContext!!, "Video trimmed successfully", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(
                        composition: Composition,
                        exportResult: ExportResult,
                        exportException: ExportException
                    ) {
                        Log.e(TAG, "Export error", exportException)
                        Toast.makeText(activity?.applicationContext!!, "Error", Toast.LENGTH_LONG).show()
                    }
                })
            .build()
    }

    @Throws(PackageManager.NameNotFoundException::class)
    @OptIn(markerClass = arrayOf(UnstableApi::class))
    public  fun createComposition(
        mediaItem: androidx.media3.common.MediaItem,
    ): Composition {
        val editedMediaItemBuilder = EditedMediaItem.Builder(mediaItem)
        editedMediaItemBuilder.setDurationUs(5000000).setFrameRate(30)
        val forceAudioTrack = false
        editedMediaItemBuilder
            .setRemoveAudio(false)
            .setRemoveVideo(false)
        val editedMediaItems: MutableList<EditedMediaItem> = ArrayList()
        editedMediaItems.add(editedMediaItemBuilder.build())
        val sequences: MutableList<EditedMediaItemSequence> = ArrayList()
        sequences.add(EditedMediaItemSequence(editedMediaItems))
        return Composition.Builder(sequences)
            .experimentalSetForceAudioTrack(forceAudioTrack)
            .build()
    }

}
