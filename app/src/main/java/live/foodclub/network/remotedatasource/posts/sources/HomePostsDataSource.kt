package live.foodclub.network.remotedatasource.posts.sources

import android.util.Log
import live.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse
import live.foodclub.network.retrofit.services.PostsService
import retrofit2.Response
import javax.inject.Inject

class HomePostsDataSource @Inject constructor(
    private val api: PostsService
): PostsRemoteDataSource {
    override suspend fun getPosts(
        userId: Long,
        pageSize: Int?,
        pageNo: Int?
    ): Response<RetrievePostsListResponse> {
        return api.getHomepagePosts(pageSize, pageNo)
    }
}