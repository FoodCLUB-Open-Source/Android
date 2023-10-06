package android.kotlin.foodclub.viewmodels.home

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


data class MediaImageTest(var id: String) {
    var count: Int = 0
    var cover_ID: Long = 0;
    var cover_name = "";
}


class GalleryViewModel : ViewModel() {
    private val _title = MutableLiveData("GalleryViewModel View")
    val title: LiveData<String> get() = _title

    //Hard coded data for now
    val ResourceIds: MutableList<Pair<Uri, String>> = mutableListOf()
    val tests: MutableList<MediaImageTest> = mutableListOf()
    fun getMediaContent(
        context: Context,
        limitSize: Boolean = false,
        sizeLimit: Int = 10
    ): MutableList<Uri> {
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

        return uris
    }

    fun getVideoMediaContent(
        context: Context,
        limitSize: Boolean = false,
        sizeLimit: Int = 10
    ): MutableList<Uri> {
        val imageProjection = arrayOf(
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DATE_TAKEN,
            MediaStore.Video.Media._ID
        )
        val imageSortOrder = "${MediaStore.Video.VideoColumns.DATE_TAKEN} DESC"

        val selectionArgs: Bundle = Bundle()
        if (limitSize)
        {
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
            //imageSortOrder
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
                    uris.add(contentUri)
                    count++;
                    // generate the thumbnail
                    //val thumbnail = (context).contentResolver.loadThumbnail(contentUri, Size(480, 480), null)

                }
            } ?: kotlin.run {
                Log.e("TAG", "Cursor is null!")
            }
        }

        return uris
    }
}
