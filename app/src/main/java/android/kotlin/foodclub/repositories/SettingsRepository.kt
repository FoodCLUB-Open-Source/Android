package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.domain.models.profile.UserDetailsModel
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.UserDetailsMapper
import android.kotlin.foodclub.network.retrofit.dtoModels.settings.ChangePasswordDto
import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.network.retrofit.responses.general.SingleMessageResponse
import android.kotlin.foodclub.network.retrofit.responses.settings.UpdateUserDetailsResponse
import android.kotlin.foodclub.network.retrofit.services.SettingsService
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.room.repository.datasource.ProfileDataLocalSource
import android.kotlin.foodclub.utils.helpers.Resource
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(
    private val api: SettingsService,
    private val userDetailsMapper: UserDetailsMapper,
    private val profileDataLocalSource: ProfileDataLocalSource
) {
    companion object {
        private val TAG = SettingsRepository::class.java.simpleName
    }

    suspend fun changePassword(
        oldPassword: String, newPassword: String
    ): Resource<SingleMessageResponse, DefaultErrorResponse> {
        return when (
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

    suspend fun retrieveUserDetails(userId: Long): Flow<Resource<UserDetailsModel, DefaultErrorResponse>> {
        return profileDataLocalSource.getProfile(userId)
            .map<UserDetailsModel?, Resource<UserDetailsModel, DefaultErrorResponse>> { userDetails ->
                if (userDetails != null) {
                    Resource.Success(userDetails)
                } else {
                    Resource.Error("User details not found in database")
                }
            }.also {
                retrieveUserFromService(userId)
            }
    }

    private suspend fun retrieveUserFromService(userId: Long) {
        val userFromService = api.retrieveUserDetails(userId)

        if (userFromService.isSuccessful) {
            val userDetailsDto = userFromService.body()?.data
            if (userDetailsDto != null) {
                val userDetailsModel = userDetailsMapper.mapToDomainModel(userDetailsDto)
                profileDataLocalSource.insertProfile(userDetailsModel)
            } else {
                Log.i(TAG, "Response body or data is null")
            }
        } else {
            Log.e(TAG, userFromService.message())
        }
    }


    suspend fun updateUserDetails(
        userId: Long,
        userDetailsModel: UserDetailsModel
    ): Resource<UpdateUserDetailsResponse, DefaultErrorResponse> {
        return when (
            val resource = apiRequestFlow<UpdateUserDetailsResponse, DefaultErrorResponse> {
                api.updateUserDetails(
                    userId,
                    userDetailsMapper.mapFromDomainModel(userDetailsModel)
                )
            }
        ) {
            is Resource.Success -> {
                profileDataLocalSource.insertProfile(userDetailsModel)
                Log.i(TAG, "USER UPDATE SUCCESS ${resource.data}")
                Resource.Success(resource.data!!.body()!!)
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }
}