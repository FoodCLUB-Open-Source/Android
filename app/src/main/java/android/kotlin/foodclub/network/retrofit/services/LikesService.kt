package android.kotlin.foodclub.network.retrofit.services

import android.kotlin.foodclub.network.retrofit.responses.likes.PostLikesStatusResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface LikesService {
    @POST("likes_views/like/{post_id}/user")
    suspend fun updatePostLikeStatus(
        @Path("post_id") postId: Long
    ): Response<PostLikesStatusResponse>

    @DELETE("likes_views/like/{post_id}/user")
    suspend fun deletePostLikeStatus(
        @Path("post_id") postId: Long
    ): Response<PostLikesStatusResponse>
}