package android.kotlin.foodclub.domain.models.others

import android.content.Context
import android.graphics.Bitmap
import android.kotlin.foodclub.utils.helpers.FramesRetriever
import android.net.Uri
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.util.UnstableApi
import androidx.media3.transformer.Composition
import androidx.media3.transformer.ExportException
import androidx.media3.transformer.ExportResult
import androidx.media3.transformer.Transformer
import java.io.File

class TrimmedVideo(
    private val player: Player,
    val id: Int, private val uri: Uri,
    private val onInitialiseCompleted: (TrimmedVideo) -> Unit
) {
    private val framesRetriever = FramesRetriever()

    var initialDuration: Long = 0L
        private set

    var duration: Long = 0L
        private set

    var startTime: Long = 0L

    var frames: LinkedHashSet<Bitmap> = linkedSetOf()
        private set

    var durationSet = false
        private set

    private var initialised = false

    var savedFilePath: String? = null
        private set


    init {
        addListeners()
        player.addMediaItem(id, MediaItem.fromUri(uri))
    }

    private fun addListeners() {
        player.addListener(
            object : Player.Listener {
                override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                    updateTimeline()
                }
            }
        )
    }

    private fun updateTimeline() {
        val currentDuration = player.currentTimeline.getWindow(id, Timeline.Window()).durationMs

        if(currentDuration > 0 && !durationSet) {
            durationSet = true
            duration = currentDuration
            Log.d("TrimmedVideo","New duration: $duration")
        }

        if(durationSet && !initialised) {
            initialised = true
            initialDuration = duration
            frames = if(frames.isEmpty()) framesRetriever.retrieveFramesFromUri(uri) else frames
            onInitialiseCompleted(this)
        }
    }

    private fun trimVideo(startMs: Long?, endMs: Long?) {
        player.pause()

        val oldItem = player.getMediaItemAt(id)
        val newItemBuilder = oldItem.clippingConfiguration.buildUpon()

        val oldStartMs = oldItem.clippingConfiguration.startPositionMs

        if(startMs != null) newItemBuilder.setStartPositionMs(startMs)
        if(endMs != null) newItemBuilder.setEndPositionMs(endMs)

        val newClipping = newItemBuilder.build()
        val newStartMs = newClipping.startPositionMs

        durationSet = false
        player.replaceMediaItem(id, oldItem.buildUpon().setClippingConfiguration(newClipping).build())

        if(oldStartMs == newStartMs && endMs != null) player.seekTo(id, endMs) else
            player.seekTo(id, 0)
        player.play()
    }

    fun startTrimming(startOffset: Float, endOffset: Float) {
        val startMs = if(startOffset != 0f) (startOffset * 1000).toLong() else null
        val endMs = if(endOffset != 0f) duration + (endOffset * 1000).toLong() else null
        trimVideo(startMs, endMs)
    }

    fun previewTrimming(offsetX: Float, draggingLeftSide: Boolean) {
        val time: Long = if(draggingLeftSide) (offsetX * 1000).toLong() else
            duration + (offsetX * 1000).toLong()

        player.pause()
        player.seekTo(id, time)
    }

    @UnstableApi
    fun saveVideo(context: Context, onSaveListener: () -> Unit) {
        File.createTempFile("trimmed_video$id", ".mp4", context.cacheDir)
        val cacheFile = File(context.cacheDir, "trimmed_video$id.mp4")
        val filePath: String = cacheFile.absolutePath

        val transformerListener: Transformer.Listener =
            object : Transformer.Listener {
                override fun onCompleted(composition: Composition, result: ExportResult) {
                    savedFilePath = filePath
                    onSaveListener()
                }

                override fun onError(composition: Composition, result: ExportResult,
                                     exception: ExportException
                ) {

                }
            }

        val item = player.getMediaItemAt(id)
        val transformer = Transformer.Builder(context)
//            .setVideoMimeType(MimeTypes.VIDEO_H264)
            .addListener(transformerListener)
            .build()
        transformer.start(item, filePath)
    }
}