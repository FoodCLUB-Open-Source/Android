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

    @POST("login/resend_verification_code")
    suspend fun resendCode(
        @Body verificationCodeResendData: VerificationCodeResendData
    ):Response<SignUpResponseMessage>
}
