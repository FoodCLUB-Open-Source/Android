package live.foodclub.presentation.ui.home.gallery

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap

data class GalleryState(
    val title: String,
    val uris: List<Uri>,
    val resourceIds: List<Pair<Uri, String>>,
    val resourceDrawables: List<Uri>,
    val resourceUri: List<Uri>,
    val imageThumbNails: List<ImageBitmap>,
    val videoThumbNails: List<ImageBitmap>
) {
    companion object {
        fun default() = GalleryState(
            title = "",
            uris = emptyList(),
            resourceIds = emptyList(),
            resourceDrawables = emptyList(),
            resourceUri = emptyList(),
            imageThumbNails = emptyList(),
            videoThumbNails = emptyList()
        )
    }
}
