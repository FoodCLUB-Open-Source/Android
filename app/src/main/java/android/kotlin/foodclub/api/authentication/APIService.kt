package android.kotlin.foodclub.api.authentication

import android.kotlin.foodclub.api.responses.LoginResponse
import android.kotlin.foodclub.api.responses.RetrieveFollowerListResponse
import android.kotlin.foodclub.api.responses.RetrieveFollowingListResponse
import android.kotlin.foodclub.api.responses.RetrieveProfileResponse
import android.kotlin.foodclub.data.models.SignUpError
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


data class UserSignUpInformation(
    val username: String,
    val email: String,
    val password: String,
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
        @Query("page_number") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): Response<RetrieveProfileResponse>

    @GET("profile/{Id}/following")
    suspend fun retrieveProfileFollowing(
        @Path("Id") userId: Long,
        @Query("page_number") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): Response<RetrieveFollowingListResponse>

    @GET("profile/{Id}/followers")
    suspend fun retrieveProfileFollowers(
        @Path("Id") userId: Long,
        @Query("page_number") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): Response<RetrieveFollowerListResponse>
}
