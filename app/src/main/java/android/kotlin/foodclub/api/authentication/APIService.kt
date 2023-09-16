package android.kotlin.foodclub.api.authentication

import android.kotlin.foodclub.api.responses.DeletePostResponse
import android.kotlin.foodclub.api.responses.FollowUnfollowResponse
import android.kotlin.foodclub.api.responses.GetPostResponse
import android.kotlin.foodclub.api.responses.LoginResponse
import android.kotlin.foodclub.api.responses.RetrieveFollowerListResponse
import android.kotlin.foodclub.api.responses.RetrieveFollowingListResponse
import android.kotlin.foodclub.api.responses.RetrieveProfileResponse
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

data class PostByWorld(
    val id: String,
    val user_id:String,
    val title:String,
    val description:String,
    val created_at:String,
    val updated_at:String,
    val post_id:String,
    val category_name:String,
    val video_url:String,
    val thumbnail_url:String
)

data class GetPostByWorld(
    val posts:List<PostByWorld>
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

data class PostByUserId(
    val id: Int,
    val title:String,
    val description:String,
    val created_at:String,
    val video_url:String,
    val thumbnail_url:String,
)

data class GetPostByUserId(
    val posts:List<PostByUserId>
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


    @GET("posts/category/{Id}")
    suspend fun getPostByWorldCategory(
        @Path("Id") categoryId: Long,
    ):Response<GetPostByWorld>

    @GET("posts/{Id}")
    suspend fun getPostById(
        @Path("Id") postId: Long,
    ):Response<GetPostById>

    @GET("posts/homepage/{userId}")
    suspend fun getPostsByUserId(
        @Path("userId") userId: Long,
        @Query("page_size") pageSize: Int?,
        @Query("page_number") pageNo: Int?,
    ):Response<GetPostByUserId>




}
