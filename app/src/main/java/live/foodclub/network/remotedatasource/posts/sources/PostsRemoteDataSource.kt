package live.foodclub.network.remotedatasource.posts.sources

import live.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse
import retrofit2.Response

interface PostsRemoteDataSource {
    suspend fun getPosts(
        userId: Long = 0,
        pageSize: Int? = null,
        pageNo: Int? = null
    ): Response<RetrievePostsListResponse>
}