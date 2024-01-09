package android.kotlin.foodclub.network.retrofit.services

import android.kotlin.foodclub.network.retrofit.responses.profile.FollowUnfollowResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveFollowerListResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveFollowingListResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveProfileResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveUserDetailsResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.UpdateUserProfileImageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileService {
    @GET("profile/{Id}")
    suspend fun retrieveProfileData(
        @Path("Id") userId: Long,
        @Query("page_number") pageNo: Int?,
        @Query("page_size") pageSize: Int?
    ): Response<RetrieveProfileResponse>

    @PUT("profile/profile_picture/{user_id}")
    @Multipart
    suspend fun updateUserProfileImage(
        @Path("user_id") userId: Long,
        @Part imagePart: MultipartBody.Part
    ): Response<UpdateUserProfileImageResponse>

    @GET("bookmarks/{userId}")
    suspend fun getBookmarkedPosts(
        @Path("userId") userId: Long,
        @Query("page_size") pageSize: Int?,
        @Query("page_number") pageNo: Int?
    ): Response<RetrievePostsListResponse>

    @GET("profile/{Id}/followers")
    suspend fun retrieveProfileFollowers(
        @Path("Id") userId: Long,
        @Query("page_number") pageNo: Int?,
        @Query("page_size") pageSize: Int?
    ): Response<RetrieveFollowerListResponse>

    @GET("profile/{Id}/following")
    suspend fun retrieveProfileFollowing(
        @Path("Id") userId: Long,
        @Query("page_number") pageNo: Int?,
        @Query("page_size") pageSize: Int?
    ): Response<RetrieveFollowingListResponse>

    @POST("profile/follow/user/{followerId}/following/{userId}")
    suspend fun followUser(
        @Path("followerId") followerId: Long,
        @Path("userId") userId: Long
    ): Response<FollowUnfollowResponse>

    @DELETE("profile/unfollow/user/{followerId}/following/{userId}")
    suspend fun unfollowUser(
        @Path("followerId") followerId: Long,
        @Path("userId") userId: Long
    ): Response<FollowUnfollowResponse>
}