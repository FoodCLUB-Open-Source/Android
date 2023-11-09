package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.domain.models.profile.UserDetailsModel
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.UserDetailsMapper
import android.kotlin.foodclub.network.retrofit.dtoModels.settings.ChangePasswordDto
import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.network.retrofit.responses.general.SingleMessageResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveUserDetailsResponse
import android.kotlin.foodclub.network.retrofit.responses.settings.UpdateUserDetailsResponse
import android.kotlin.foodclub.network.retrofit.services.SettingsService
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.utils.helpers.Resource
import android.util.Log

class SettingsRepository(
    private val api: SettingsService,
    private val userDetailsMapper: UserDetailsMapper
) {
    suspend fun changePassword(
        oldPassword: String, newPassword: String
    ): Resource<SingleMessageResponse, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<SingleMessageResponse, DefaultErrorResponse> {
                api.changePassword(ChangePasswordDto(oldPassword, newPassword))
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
    suspend fun retrieveUserDetails(userId: Long): Resource<UserDetailsModel, DefaultErrorResponse> {
        return when (val resource = apiRequestFlow<RetrieveUserDetailsResponse, DefaultErrorResponse> {
            api.retrieveUserDetails(userId)
        }) {
            is Resource.Success -> {
                val userDetailsDto = resource.data?.body()?.data
                if (userDetailsDto != null) {
                    val userDetailsModel = userDetailsMapper.mapToDomainModel(userDetailsDto)
                    Resource.Success(userDetailsModel)
                } else {
                    Log.i("MYTAG","Response body or data is null")
                    Resource.Error("Response body or data is null")
                }
            }
            is Resource.Error -> {
                Log.i("MYTAG","${resource.message}")
                Resource.Error(resource.message ?: "Unknown error")
            }
        }
    }

    suspend fun updateUserDetails(
        userId: Long,
        userDetailsModel: UserDetailsModel
    ): Resource<UpdateUserDetailsResponse, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<UpdateUserDetailsResponse, DefaultErrorResponse> {
                api.updateUserDetails(userId, userDetailsMapper.mapFromDomainModel(userDetailsModel))
            }
        ){
            is Resource.Success -> {
                Resource.Success(resource.data!!.body()!!)
            }
            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }
}