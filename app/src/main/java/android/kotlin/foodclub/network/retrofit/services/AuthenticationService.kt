package android.kotlin.foodclub.network.retrofit.services

import android.kotlin.foodclub.network.retrofit.dtoModels.auth.ForgotChangePasswordDto
import android.kotlin.foodclub.network.retrofit.dtoModels.auth.RefreshTokenDto
import android.kotlin.foodclub.network.retrofit.dtoModels.auth.SignInUserCredentialsDto
import android.kotlin.foodclub.network.retrofit.dtoModels.auth.ResendVerificationCodeDto
import android.kotlin.foodclub.network.retrofit.dtoModels.auth.SignUpUserDto
import android.kotlin.foodclub.network.retrofit.dtoModels.auth.VerificationCodeDto
import android.kotlin.foodclub.network.retrofit.responses.auth.LoginResponse
import android.kotlin.foodclub.network.retrofit.responses.general.SingleMessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenticationService {
    @POST("login/signup")
    suspend fun signUpUser(
        @Body signUpInformation: SignUpUserDto
    ): Response<SingleMessageResponse>

    @POST("login/confirm_verification")
    suspend fun verifyUserAccount(
        @Body verificationCodeRequestData: VerificationCodeDto
    ): Response<SingleMessageResponse>

    @POST("login/signin")
    suspend fun loginUser(
        @Body credentials: SignInUserCredentialsDto
    ): Response<LoginResponse>

    @POST("login/resend_verification_code")
    suspend fun resendVerificationCode(
        @Body verificationCodeResendData: ResendVerificationCodeDto
    ): Response<SingleMessageResponse>

    @POST("login/refresh")
    suspend fun refreshToken(
        @Header("Authorization") token: String
    ): Response<RefreshTokenDto>

    @POST("login/forgot_password/verification_code")
    suspend fun sendForgotPasswordVerificationCode(
        @Body resendVerificationCodeDto: ResendVerificationCodeDto
    ):Response<SingleMessageResponse>

    @POST("login/forgot_password_code/new_password")
    suspend fun changePassword(
        @Body changePasswordInformation: ForgotChangePasswordDto
    ):Response<SingleMessageResponse>
}