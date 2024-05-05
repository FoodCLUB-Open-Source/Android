package live.foodclub.utils.helpers

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri

class FramesRetriever {
    fun retrieveFramesFromUri(uri: Uri): LinkedHashSet<Bitmap> {
        val framesList = LinkedHashSet<Bitmap>()
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(uri.toString())

        val duration = (retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            ?.toLong()?.times(1000))
            ?: return framesList

        var currentDuration = 0L
        while(currentDuration < duration) {
            retriever.getFrameAtTime(currentDuration, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
                ?.let { framesList.add(it) }
            currentDuration += 1000000
        }
        retriever.release()
        return framesList
    }
}