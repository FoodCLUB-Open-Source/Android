package android.kotlin.foodclub.network.remotedatasource.profile_remote_datasource

import android.kotlin.foodclub.network.retrofit.responses.profile.FollowUnfollowResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveFollowerListResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveFollowingListResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveProfileResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.UpdateUserProfileImageResponse
import android.kotlin.foodclub.network.retrofit.services.ProfileService
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class ProfileRemoteDataSourceImpl @Inject constructor(
    private val api: ProfileService
): ProfileRemoteDataSource {
    override suspend fun retrieveProfileData(
        userId: Long,
        pageNo: Int?,
        pageSize: Int?
    ): Response<RetrieveProfileResponse> {
        return api.retrieveProfileData(userId, pageNo, pageSize)
    }

    override suspend fun updateUserProfileImage(
        userId: Long,
        imagePart: MultipartBody.Part
    ): Response<UpdateUserProfileImageResponse> {
        return api.updateUserProfileImage(userId, imagePart)
    }

    override suspend fun getBookmarkedPosts(
        userId: Long,
        pageSize: Int?,
        pageNo: Int?
    ): Response<RetrievePostsListResponse> {
        return api.getBookmarkedPosts(userId,pageSize,pageNo)
    }

    override suspend fun retrieveProfileFollowers(
        userId: Long,
        pageNo: Int?,
        pageSize: Int?
    ): Response<RetrieveFollowerListResponse> {
        return api.retrieveProfileFollowers(userId,pageNo,pageSize)
    }

    override suspend fun retrieveProfileFollowing(
        userId: Long,
        pageNo: Int?,
        pageSize: Int?
    ): Response<RetrieveFollowingListResponse> {
        return api.retrieveProfileFollowing(userId,pageNo,pageSize)
    }

    override suspend fun followUser(
        followerId: Long,
        userId: Long
    ): Response<FollowUnfollowResponse> {
        return api.followUser(followerId, userId)
    }

    override suspend fun unfollowUser(
        followerId: Long,
        userId: Long
    ): Response<FollowUnfollowResponse> {
        return api.unfollowUser(followerId, userId)
    }

}