package live.foodclub.network.remotedatasource.posts.sources

import live.foodclub.network.retrofit.dtoModels.profile.toRetrievePostsListResponse
import live.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse
import live.foodclub.network.retrofit.responses.profile.RetrieveProfileResponse
import live.foodclub.network.retrofit.services.PostsService
import live.foodclub.network.retrofit.services.ProfileService
import retrofit2.Response
import javax.inject.Inject

class ProfilePostsDataSource @Inject constructor(
    private val api: ProfileService
): PostsRemoteDataSource {
    override suspend fun getPosts(
        userId: Long,
        pageSize: Int?,
        pageNo: Int?
    ): Response<RetrievePostsListResponse> {
        val response = api.retrieveProfileData(
            userId = userId,
            pageSize = pageSize,
            pageNo = pageNo
        )

        return if (response.isSuccessful) {
            Response.success(response.code(), response.body()?.data?.toRetrievePostsListResponse())
        } else {
            Response.error(response.errorBody()!!, response.raw())
        }
    }
}