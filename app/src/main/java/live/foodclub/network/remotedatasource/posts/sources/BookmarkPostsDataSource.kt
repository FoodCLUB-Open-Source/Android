package live.foodclub.network.remotedatasource.posts.sources

import live.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse
import live.foodclub.network.retrofit.services.ProfileService
import retrofit2.Response
import javax.inject.Inject

class BookmarkPostsDataSource @Inject constructor(
    private val api: ProfileService
): PostsRemoteDataSource {
    override suspend fun getPosts(
        userId: Long,
        pageSize: Int?,
        pageNo: Int?
    ): Response<RetrievePostsListResponse> {
        return api.getBookmarkedPosts(
            userId = userId,
            pageSize = pageSize,
            pageNo = pageNo
        )
    }
}