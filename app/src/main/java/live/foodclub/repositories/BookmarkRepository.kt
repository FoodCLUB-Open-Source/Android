package live.foodclub.repositories

import live.foodclub.network.retrofit.responses.bookmarks.BookmarksStatusResponse
import live.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import live.foodclub.network.retrofit.services.BookmarksService
import live.foodclub.network.retrofit.utils.apiRequestFlow
import live.foodclub.utils.helpers.Resource

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