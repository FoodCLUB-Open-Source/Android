package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.domain.models.auth.ForgotChangePassword
import android.kotlin.foodclub.domain.models.auth.SignInUser
import android.kotlin.foodclub.domain.models.auth.SignUpUser
import android.kotlin.foodclub.network.retrofit.services.AuthenticationService
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.ForgotChangePasswordMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.SignInUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.SignUpUserMapper
import android.kotlin.foodclub.network.retrofit.dtoModels.auth.SignInUserCredentialsDto
import android.kotlin.foodclub.network.retrofit.dtoModels.auth.ResendVerificationCodeDto
import android.kotlin.foodclub.network.retrofit.dtoModels.auth.VerificationCodeDto
import android.kotlin.foodclub.network.retrofit.responses.auth.LoginResponse
import android.kotlin.foodclub.network.retrofit.responses.general.SingleMessageResponse
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.utils.helpers.Resource

class AuthRepository(
    private val api: AuthenticationService,
    private val signInMapper: SignInUserMapper,
    private val forgotChangePasswordMapper: ForgotChangePasswordMapper,
    private val signUpUserMapper: SignUpUserMapper
) {
    suspend fun signIn(
        username: String, password: String
    ): Resource<SignInUser, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<LoginResponse, DefaultErrorResponse> {
                api.loginUser(SignInUserCredentialsDto(username, password))
            }
        ) {
            is Resource.Success -> {
                Resource.Success(
                    signInMapper.mapToDomainModel(resource.data!!.body()!!)
                )
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun signUp(
        signUpUser: SignUpUser
    ): Resource<SingleMessageResponse, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<SingleMessageResponse, DefaultErrorResponse> {
                api.signUpUser(signUpUserMapper.mapFromDomainModel(signUpUser))
            }
        ) {
            is Resource.Success -> {
                Resource.Success(resource.data!!.body()!!)
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun verifyAccount(
        username: String, code: String
    ): Resource<SingleMessageResponse, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<SingleMessageResponse, DefaultErrorResponse> {
                api.verifyUserAccount(VerificationCodeDto(username, code))
            }
        ) {
            is Resource.Success -> {
                Resource.Success(resource.data!!.body()!!)
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun resendAccountVerificationCode(
        username: String
    ): Resource<SingleMessageResponse, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<SingleMessageResponse, DefaultErrorResponse> {
                api.resendVerificationCode(ResendVerificationCodeDto(username))
            }
        ) {
            is Resource.Success -> {
                Resource.Success(resource.data!!.body()!!)
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun confirmForgotPasswordChange(
        forgotChangePasswordData: ForgotChangePassword
    ): Resource<SingleMessageResponse, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<SingleMessageResponse, DefaultErrorResponse> {
                api.changePassword(
                    forgotChangePasswordMapper.mapFromDomainModel(forgotChangePasswordData)
                )
            }
        ) {
            is Resource.Success -> {
                Resource.Success(resource.data!!.body()!!)
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun sendForgotPasswordCode(
        email: String
    ): Resource<SingleMessageResponse, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<SingleMessageResponse, DefaultErrorResponse> {
                api.sendForgotPasswordVerificationCode(ResendVerificationCodeDto(email))
            }
        ) {
            is Resource.Success -> {
                Resource.Success(resource.data!!.body()!!)
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }
}