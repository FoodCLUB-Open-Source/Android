package android.kotlin.foodclub.api.authentication

import android.kotlin.foodclub.api.responses.DeletePostResponse
import android.kotlin.foodclub.api.responses.FollowUnfollowResponse
import android.kotlin.foodclub.api.responses.GetPostResponse
import android.kotlin.foodclub.api.responses.LoginResponse
import android.kotlin.foodclub.api.responses.RetrieveBookmarkedPostsResponse
import android.kotlin.foodclub.api.responses.RetrieveFollowerListResponse
import android.kotlin.foodclub.api.responses.RetrieveFollowingListResponse
import android.kotlin.foodclub.api.responses.RetrieveHomepagePostList
import android.kotlin.foodclub.api.responses.RetrieveProfileResponse
import android.kotlin.foodclub.api.responses.RetrieveUserFriendsStoriesResponse
import android.kotlin.foodclub.api.responses.RetrieveWorldCategoryPostList
import android.kotlin.foodclub.data.models.SignUpError
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


data class UserSignUpInformation(
    val username: String,
    val email: String,
    val password: String,

    @SerializedName("fullname")
    val name: String
)

data class ErrorResponse(
    val errors: List<Map<String, String>>
)

data class UserCredentials (
    val username: String,
    val password: String
)

data class SignUpResponseMessage(
    val message: String = "",
    val errors: List<SignUpError> = listOf()
)

data class VerificationCodeRequestData(
    val username: String,

    @SerializedName("verification_code")
    val code: String
)

data class VerificationCodeResendData(val username: String)
data class VerificationCodeForPasswordData(val message: String)

data class ChangePasswordInformation(
    val username: String,
    val verification_code: String,
    val new_password: String,
)

data class PostById(
    val id: Int,
    val title:String,
    val description:String,
    val username:String,
    val profile_picture:String,
    val video_url:String,
    val thumbnail_url:String,
    val total_likes:Int,
    val total_views:Int
)

data class GetPostById(
    val data:List<PostById>
)


interface API {

    @POST("login/signup")
    suspend fun postUser(
       @Body signUpInformation: UserSignUpInformation
    ):Response<SignUpResponseMessage>

    @POST("login/confirm_verification")
    suspend fun verifyCode(
        @Body verificationCodeRequestData: VerificationCodeRequestData
    ):Response<SignUpResponseMessage>

    @POST("login/signin")
    suspend fun loginUser(
        @Body credentials: UserCredentials
    ): Response<LoginResponse>

    @POST("login/resend_verification_code")
    suspend fun resendCode(
        @Body verificationCodeResendData: VerificationCodeResendData
    ):Response<SignUpResponseMessage>

    //Retrieve Profile Page Details
    @GET("profile/{Id}")
    suspend fun retrieveProfileData(
        @Path("Id") userId: Long,
        @Query("page_number") pageNo: Int?,
        @Query("page_size") pageSize: Int?
    ): Response<RetrieveProfileResponse>

    @GET("profile/{Id}/following")
    suspend fun retrieveProfileFollowing(
        @Path("Id") userId: Long,
        @Query("page_number") pageNo: Int?,
        @Query("page_size") pageSize: Int?
    ): Response<RetrieveFollowingListResponse>

    @GET("profile/{Id}/followers")
    suspend fun retrieveProfileFollowers(
        @Path("Id") userId: Long,
        @Query("page_number") pageNo: Int?,
        @Query("page_size") pageSize: Int?
    ): Response<RetrieveFollowerListResponse>

    @DELETE("profile/unfollow/user/{followerId}/following/{userId}")
    suspend fun unfollowUser(
        @Path("followerId") followerId: Long,
        @Path("userId") userId: Long
    ): Response<FollowUnfollowResponse>

    @POST("profile/follow/user/{followerId}/following/{userId}")
    suspend fun followUser(
        @Path("followerId") followerId: Long,
        @Path("userId") userId: Long
    ): Response<FollowUnfollowResponse>
  
    @POST("login/forgot_password/verification_code")
    suspend fun sendVerificationCodePassword(
        @Body VerificationCodeResendData: VerificationCodeResendData
    ):Response<VerificationCodeForPasswordData>

    @POST("login/forgot_password_code/new_password")
    suspend fun changePassword(
        @Body changePasswordInformation: ChangePasswordInformation
    ):Response<VerificationCodeForPasswordData>

    @GET("posts/{postId}")
    suspend fun getPost(
        @Path("postId") postId: Long
    ): Response<GetPostResponse>

    @DELETE("posts/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: Long
    ): Response<DeletePostResponse>


    @GET("posts/category/{userId}")
    suspend fun getPostByWorldCategory(
        @Path("userId") categoryId: Long,
        @Query("page_size") pageSize: Int?,
        @Query("page_number") pageNo: Int?
    ):Response<RetrieveWorldCategoryPostList>

    @GET("posts/{Id}")
    suspend fun getPostById(
        @Path("Id") postId: Long,
    ):Response<GetPostById>

    @GET("posts/homepage/{userId}")
    suspend fun getHomepagePosts(
        @Path("userId") userId: Long,
        @Query("page_size") pageSize: Int?,
        @Query("page_number") pageNo: Int?
    ):Response<RetrieveHomepagePostList>

    @GET("bookmarks/{userId}")
    suspend fun getBookmarkedPosts(
        @Path("userId") userId: Long,
        @Query("page_size") pageSize: Int?,
        @Query("page_number") pageNo: Int?
    ): Response<RetrieveBookmarkedPostsResponse>

    @GET("stories/{userId}/following_stories")
    suspend fun getUserFriendStories(
        @Path("userId") userId: Long,
        ): Response<RetrieveUserFriendsStoriesResponse>
}
