package live.foodclub.network.retrofit.services

import live.foodclub.network.retrofit.responses.profile.FollowUnfollowResponse
import live.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse
import live.foodclub.network.retrofit.responses.profile.RetrieveFollowerListResponse
import live.foodclub.network.retrofit.responses.profile.RetrieveFollowingListResponse
import live.foodclub.network.retrofit.responses.profile.RetrieveProfileResponse
import live.foodclub.network.retrofit.responses.profile.UpdateUserProfileImageResponse
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
    @GET("profile/{userId}")
    suspend fun retrieveProfileData(
        @Path("userId") userId: Long?,
        @Query("page_number") pageNo: Int?,
        @Query("page_size") pageSize: Int?
    ): Response<RetrieveProfileResponse>

    @PUT("profile/profile_picture}")
    @Multipart
    suspend fun updateUserProfileImage(
        @Part imagePart: MultipartBody.Part
    ): Response<UpdateUserProfileImageResponse>

    @GET("bookmarks/{userId}")
    suspend fun getBookmarkedPosts(
        @Path("userId") userId: Long,
        @Query("page_size") pageSize: Int?,
        @Query("page_number") pageNo: Int?
    ): Response<RetrievePostsListResponse>

    @GET("profile/followers/{Id}")
    suspend fun retrieveProfileFollowers(
        @Path("Id") userId: Long,
        @Query("page_number") pageNo: Int?,
        @Query("page_size") pageSize: Int?
    ): Response<RetrieveFollowerListResponse>

    @GET("profile/following/{Id}")
    suspend fun retrieveProfileFollowing(
        @Path("Id") userId: Long,
        @Query("page_number") pageNo: Int?,
        @Query("page_size") pageSize: Int?
    ): Response<RetrieveFollowingListResponse>

    @POST("profile/follow/user/following/{userId}")
    suspend fun followUser(
        @Path("userId") userId: Long
    ): Response<FollowUnfollowResponse>

    @DELETE("profile/unfollow/user/following/{userId}")
    suspend fun unfollowUser(
        @Path("userId") userId: Long
    ): Response<FollowUnfollowResponse>
}