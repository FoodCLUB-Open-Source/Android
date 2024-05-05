package live.foodclub.network.remotedatasource.profile_remote_datasource

import live.foodclub.network.retrofit.responses.profile.FollowUnfollowResponse
import live.foodclub.network.retrofit.responses.profile.RetrieveFollowerListResponse
import live.foodclub.network.retrofit.responses.profile.RetrieveFollowingListResponse
import live.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse
import live.foodclub.network.retrofit.responses.profile.RetrieveProfileResponse
import live.foodclub.network.retrofit.responses.profile.UpdateUserProfileImageResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface ProfileRemoteDataSource {
    suspend fun retrieveProfileData(
        userId: Long,
        pageNo: Int? = null,
        pageSize: Int? = null
    ): Response<RetrieveProfileResponse>

    suspend fun updateUserProfileImage(
        imagePart: MultipartBody.Part
    ): Response<UpdateUserProfileImageResponse>

    suspend fun getBookmarkedPosts(
        userId: Long,
        pageSize: Int? = null,
        pageNo: Int? = null
    ): Response<RetrievePostsListResponse>

    suspend fun retrieveProfileFollowers(
        userId: Long,
        pageNo: Int? = null,
        pageSize: Int? = null
    ): Response<RetrieveFollowerListResponse>

    suspend fun retrieveProfileFollowing(
        userId: Long,
        pageNo: Int? = null,
        pageSize: Int? = null
    ): Response<RetrieveFollowingListResponse>

    suspend fun followUser(
        userId: Long
    ): Response<FollowUnfollowResponse>

    suspend fun unfollowUser(
        userId: Long
    ): Response<FollowUnfollowResponse>

}