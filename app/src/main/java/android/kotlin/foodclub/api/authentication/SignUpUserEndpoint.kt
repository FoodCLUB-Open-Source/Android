package android.kotlin.foodclub.api.authentication

import android.kotlin.foodclub.data.models.SignUpError
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


data class UserSignUpInformation(
    val username: String,
    val email: String,
    val password: String,
)

data class LoginResponse(
    val id: Int,
    val username: String,
    val profile_picture: String
)

data class ErrorItem(
    val type: String,
    val value: String,
    val msg: String,
    val path: String,
    val location: String
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
}
