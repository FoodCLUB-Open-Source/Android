package android.kotlin.foodclub.network.retrofit.services

import android.kotlin.foodclub.network.retrofit.responses.bookmarks.BookmarksStatusResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface BookmarksService {

    @POST("bookmarks/post/{user_id}/bookmark/{post_id}")
    suspend fun updatePostBookmark(
        @Path("user_id") userId: Long,
        @Path("post_id") postId: Long,
    ): Response<BookmarksStatusResponse>

    @DELETE("bookmarks/profile/{user_id}/bookmark/{post_id}")
    suspend fun deletePostBookmark(
        @Path("user_id") userId: Long,
        @Path("post_id") postId: Long,
    ): Response<BookmarksStatusResponse>

}