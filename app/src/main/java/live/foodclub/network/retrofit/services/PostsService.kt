package live.foodclub.network.retrofit.services

import live.foodclub.network.retrofit.responses.posts.DeletePostResponse
import live.foodclub.network.retrofit.responses.posts.GetHomepagePostsResponse
import live.foodclub.network.retrofit.responses.posts.GetPostResponse
import live.foodclub.network.retrofit.responses.posts.ViewsPostResponse
import live.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsService {
    @GET("posts/{postId}")
    suspend fun getPost(
        @Path("postId") postId: Long
    ): Response<GetPostResponse>

    @GET("posts")
    suspend fun getHomepagePosts(
        @Query("page_size") pageSize: Int?,
        @Query("page_number") pageNo: Int?
    ):Response<RetrievePostsListResponse>

    @GET("posts/category/{categoryId}")
    suspend fun getPostByWorldCategory(
        @Path("categoryId") categoryId: String,
        @Query("page_size") pageSize: Int?,
        @Query("page_number") pageNo: Int?
    ):Response<RetrievePostsListResponse>

    @DELETE("posts/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: Long
    ): Response<DeletePostResponse>

    @POST("likes_views/post/{postId}/view")
    suspend fun viewsPost(
        @Path("postId") postId: Long
    ): Response<ViewsPostResponse>

}