package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.domain.models.auth.ForgotChangePassword
import android.kotlin.foodclub.domain.models.auth.SignInUser
import android.kotlin.foodclub.domain.models.auth.SignUpUser
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.FirebaseUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.ForgotChangePasswordMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.SignInUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.SignUpUserMapper
import android.kotlin.foodclub.network.retrofit.dtoModels.auth.ResendVerificationCodeDto
import android.kotlin.foodclub.network.retrofit.dtoModels.auth.SignInUserCredentialsDto
import android.kotlin.foodclub.network.retrofit.dtoModels.auth.VerificationCodeDto
import android.kotlin.foodclub.network.retrofit.responses.auth.LoginResponse
import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.network.retrofit.responses.general.SingleMessageResponse
import android.kotlin.foodclub.network.retrofit.services.AuthenticationService
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.utils.helpers.Resource
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await


class AuthRepository(
    private val api: AuthenticationService,
    private val signInMapper: SignInUserMapper,
    private val forgotChangePasswordMapper: ForgotChangePasswordMapper,
    private val signUpUserMapper: SignUpUserMapper,
    private val firebaseUserRepository: FirebaseUserRepository,
    private val firebaseUserMapper: FirebaseUserMapper,
    private val firebaseMessaging: FirebaseMessaging
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
                val signInUser = signInMapper.mapToDomainModel(resource.data!!.body()!!)
                val fcmToken = firebaseMessaging.token.await()
                val newFirebaseUser = firebaseUserMapper.mapFromDomainModel(signInUser)
                newFirebaseUser.fcmToken = fcmToken
                firebaseUserRepository.saveUserToFirestore(newFirebaseUser)
                Resource.Success(
                    data = signInUser
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
        username: String, password: String, code: String
    ): Resource<LoginResponse, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<LoginResponse, DefaultErrorResponse> {
                api.verifyUserAccount(VerificationCodeDto(username, password, code))
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