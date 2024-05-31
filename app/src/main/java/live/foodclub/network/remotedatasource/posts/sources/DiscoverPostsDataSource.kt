package live.foodclub.network.remotedatasource.posts.sources

import live.foodclub.domain.enums.Category
import live.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse
import live.foodclub.network.retrofit.services.PostsService
import retrofit2.Response
import javax.inject.Inject

class DiscoverPostsDataSource @Inject constructor(
    private val api: PostsService
): PostsRemoteDataSource {
    var category: Category = Category.KETO
    override suspend fun getPosts(
        userId: Long,
        pageSize: Int?,
        pageNo: Int?,
    ): Response<RetrievePostsListResponse> {
        return api.getPostByWorldCategory(
            categoryId = category.displayName,
            pageSize = pageSize,
            pageNo = pageNo
        )
    }
}