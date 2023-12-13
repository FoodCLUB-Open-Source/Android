package android.kotlin.foodclub.views.home.gallery

import android.net.Uri

data class GalleryState(
    val title: String,
    val uris: List<Uri>,
    val resourceIds: List<Pair<Uri, String>>,
    val resourceDrawables: List<Uri>,
    val resourceUri: List<Uri>,
) {
    companion object {
        fun default() = GalleryState(
            title = "",
            uris = emptyList(),
            resourceIds = emptyList(),
            resourceDrawables = emptyList(),
            resourceUri = emptyList(),
        )
    }
}
