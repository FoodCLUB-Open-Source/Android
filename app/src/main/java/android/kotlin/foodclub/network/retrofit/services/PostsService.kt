package android.kotlin.foodclub.network.retrofit.services

import android.kotlin.foodclub.network.retrofit.responses.posts.DeletePostResponse
import android.kotlin.foodclub.network.retrofit.responses.posts.GetHomepagePostsResponse
import android.kotlin.foodclub.network.retrofit.responses.posts.GetPostResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsService {
    @GET("posts/{postId}/{userId}")
    suspend fun getPost(
        @Path("postId") postId: Long,
        @Path("userId") userId: Long
    ): Response<GetPostResponse>

    @GET("posts/homepage/{userId}")
    suspend fun getHomepagePosts(
        @Path("userId") userId: Long,
        @Query("page_size") pageSize: Int?,
        @Query("page_number") pageNo: Int?
    ):Response<GetHomepagePostsResponse>

    @GET("posts/category/{userId}")
    suspend fun getPostByWorldCategory(
        @Path("userId") categoryId: Long,
        @Query("page_size") pageSize: Int?,
        @Query("page_number") pageNo: Int?
    ):Response<GetHomepagePostsResponse>

    @DELETE("posts/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: Long
    ): Response<DeletePostResponse>
}