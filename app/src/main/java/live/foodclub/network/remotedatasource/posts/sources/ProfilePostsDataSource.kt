package live.foodclub.network.remotedatasource.posts.sources

import live.foodclub.localdatasource.localdatasource.profile_local_datasource.ProfileLocalDataSource
import live.foodclub.network.retrofit.dtoModels.profile.toProfileEntity
import live.foodclub.network.retrofit.dtoModels.profile.toRetrievePostsListResponse
import live.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse
import live.foodclub.network.retrofit.services.ProfileService
import retrofit2.Response
import javax.inject.Inject

class ProfilePostsDataSource @Inject constructor(
    private val api: ProfileService,
    private val profileLocalDataSource: ProfileLocalDataSource
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
            val profileDto = response.body()?.data
            if (profileDto != null)
                profileLocalDataSource.insertProfileLocalData(profileDto.toProfileEntity())
            Response.success(response.code(), response.body()?.data?.toRetrievePostsListResponse())
        } else {
            Response.error(response.errorBody()!!, response.raw())
        }
    }
}