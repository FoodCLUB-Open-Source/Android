package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.network.retrofit.responses.bookmarks.BookmarksStatusResponse
import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.network.retrofit.services.BookmarksService
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.utils.helpers.Resource

class BookmarkRepository(val api: BookmarksService) {

    suspend fun updateBookmarkStatus(
        postId: Long,
        isBookmarked: Boolean
    ): Resource<BookmarksStatusResponse, DefaultErrorResponse> {

        val resource = when (isBookmarked) {
            true -> {
                apiRequestFlow<BookmarksStatusResponse, DefaultErrorResponse> {
                    api.updatePostBookmark(postId = postId)
                }
            }
            false -> {
                apiRequestFlow<BookmarksStatusResponse, DefaultErrorResponse> {
                    api.deletePostBookmark(postId = postId)
                }
            }
        }

        return when (resource) {
            is Resource.Success -> {
                Resource.Success(resource.data!!.body()!!)
            }
            is Resource.Error -> {
                Resource.Error(resource.message!!.toString())
            }
        }
    }
}