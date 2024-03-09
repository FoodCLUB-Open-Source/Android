package android.kotlin.foodclub.network.retrofit.services

import android.kotlin.foodclub.network.retrofit.responses.bookmarks.BookmarksStatusResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface BookmarksService {

    @POST("bookmarks/post/bookmark/{post_id}")
    suspend fun updatePostBookmark(
        @Path("post_id") postId: Long
    ): Response<BookmarksStatusResponse>

    @DELETE("bookmarks/profile/bookmark/{post_id}")
    suspend fun deletePostBookmark(
        @Path("post_id") postId: Long
    ): Response<BookmarksStatusResponse>

}