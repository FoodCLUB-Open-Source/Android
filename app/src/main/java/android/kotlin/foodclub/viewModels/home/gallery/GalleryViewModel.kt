package android.kotlin.foodclub.viewModels.home.gallery

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.kotlin.foodclub.views.home.gallery.GalleryState
import android.kotlin.foodclub.views.home.gallery.ItemType
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {
    companion object {
        private val TAG = GalleryViewModel::class.java.simpleName
    }

    private val _state = MutableStateFlow(GalleryState.default())
    val state: StateFlow<GalleryState>
        get() = _state

    init {
        _state.update { it.copy(title = "GalleryViewModel") }
        getMediaContent(context, true, 150)
    }

    private fun getMediaContent(
        context: Context,
        limitSize: Boolean = false,
        sizeLimit: Int = 150
    ) {
        val imageProjection = arrayOf(
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media._ID
        )
        val imageSortOrder = "${MediaStore.Images.ImageColumns.DATE_TAKEN} DESC"

        val selectionArgs: Bundle = Bundle()
        if (limitSize) {
            selectionArgs.putInt(ContentResolver.QUERY_ARG_LIMIT, sizeLimit)
        }
        val sortArgs = arrayOf(MediaStore.Images.ImageColumns.DATE_TAKEN)
        selectionArgs.putStringArray(ContentResolver.QUERY_ARG_SORT_COLUMNS, sortArgs)
        selectionArgs.putInt(
            ContentResolver.QUERY_ARG_SORT_DIRECTION,
            ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
        )

        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            imageProjection,
            selectionArgs,
            null,
        )

        val uris = mutableListOf<Uri>()
        var count = 0

        cursor.use {
            it?.let {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val nameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                val dateColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
                while (it.moveToNext()) {
                    val id = it.getLong(idColumn)
                    val name = it.getString(nameColumn)
                    val size = it.getString(sizeColumn)
                    val date = it.getString(dateColumn)
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    // add the URI to the list
                    /*
                    if (limitSize && count >= sizeLimit)
                    {
                        break
                    }

                     */

                    uris.add(contentUri)
                    count++;
                    // generate the thumbnail
                    val thumbnail =
                        (context).contentResolver.loadThumbnail(contentUri, Size(480, 480), null)
                    val y = 0;
                }
            } ?: kotlin.run {
                Log.e("TAG", "Cursor is null!")
            }
        }

        _state.update { it.copy(uris = uris) }
        getVideoMediaContent(context, true, 150)
    }

    private fun getVideoMediaContent(
        context: Context,
        limitSize: Boolean = false,
        sizeLimit: Int = 150
    ) {
        val imageProjection = arrayOf(
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DATE_TAKEN,
            MediaStore.Video.Media._ID
        )
        val imageSortOrder = "${MediaStore.Video.VideoColumns.DATE_TAKEN} DESC"

        val selectionArgs: Bundle = Bundle()
        if (limitSize) {
            selectionArgs.putInt(ContentResolver.QUERY_ARG_LIMIT, sizeLimit)
        }
        val sortArgs = arrayOf(MediaStore.Images.ImageColumns.DATE_TAKEN)
        selectionArgs.putStringArray(ContentResolver.QUERY_ARG_SORT_COLUMNS, sortArgs)
        selectionArgs.putInt(
            ContentResolver.QUERY_ARG_SORT_DIRECTION,
            ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
        )

        val cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            imageProjection,
            selectionArgs,
            null,

            )

        val videoUris = mutableListOf<Uri>()
        var count = 0

        cursor.use {
            it?.let {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val nameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                val dateColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
                while (it.moveToNext()) {
                    val id = it.getLong(idColumn)
                    val name = it.getString(nameColumn)
                    val size = it.getString(sizeColumn)
                    val date = it.getString(dateColumn)
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    // add the URI to the list
                    /*
                    if (limitSize && count >= sizeLimit)
                    {
                        break
                    }

                     */
                    videoUris.add(contentUri)
                    count++;
                    // generate the thumbnail
                    //val thumbnail = (context).contentResolver.loadThumbnail(contentUri, Size(480, 480), null)

                }
            } ?: kotlin.run {
                Log.e("TAG", "Cursor is null!")
            }
        }

        val currentUris = state.value.uris.toMutableList()
        currentUris.addAll(videoUris)

        _state.update { it.copy(uris = currentUris) }
        setResources(context)
    }

    private fun setResources(context: Context) {
        val resourceIds = mutableListOf<Pair<Uri, String>>()
        val resourceDrawables = mutableListOf<Uri>()
        val resourceUri = mutableListOf<Uri>()

        for (uri in state.value.uris) {
            val type = context.contentResolver.getType(uri)
            val s = type?.let { type.substring(0, it.indexOf("/")) }
            resourceIds.add(Pair(uri, s) as Pair<Uri, String>)
        }

        resourceIds.forEach { (name, type) ->
            when (type) {
                ItemType.IMAGE.type -> {
                    resourceDrawables.add(name)
                }

                ItemType.VIDEO.type -> {
                    resourceUri.add(name)
                }

                else -> {
                    Log.e(TAG, "Unknown type: $type")
                }
            }
        }

        _state.update {
            it.copy(
                resourceIds = resourceIds,
                resourceDrawables = resourceDrawables,
                resourceUri = resourceUri
            )
        }
    }
}
