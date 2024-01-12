package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.localdatasource.room.entity.UserDetailsModel
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.UserDetailsMapper
import android.kotlin.foodclub.network.retrofit.dtoModels.settings.ChangePasswordDto
import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.network.retrofit.responses.general.SingleMessageResponse
import android.kotlin.foodclub.network.retrofit.responses.settings.UpdateUserDetailsResponse
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.localdatasource.localdatasource.user_details_local_datasource.UserDetailsLocalDataSource
import android.kotlin.foodclub.network.remotedatasource.settings_remote_datasource.SettingsRemoteDataSource
import android.kotlin.foodclub.utils.helpers.ConnectivityUtils
import android.kotlin.foodclub.utils.helpers.Resource
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(
    private val settingsRemoteDataSource: SettingsRemoteDataSource,
    private val userDetailsMapper: UserDetailsMapper,
    private val userDetailsLocalDataSource: UserDetailsLocalDataSource,
    private val connectivityUtils: ConnectivityUtils
) {
    companion object {
        private val TAG = SettingsRepository::class.java.simpleName
    }

    suspend fun changePassword(
        oldPassword: String, newPassword: String
    ): Resource<SingleMessageResponse, DefaultErrorResponse> {
        return when (
            val resource = apiRequestFlow<SingleMessageResponse, DefaultErrorResponse> {
                settingsRemoteDataSource.changePassword(ChangePasswordDto(oldPassword, newPassword))
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
        return userDetailsLocalDataSource.getLocalUserDetails(userId)
            .map<UserDetailsModel?, Resource<UserDetailsModel, DefaultErrorResponse>> { userDetails ->
                if (userDetails != null) {
                    Resource.Success(userDetails)
                } else {
                    Resource.Error("User details not found in database")
                }
            }.also {
                if (connectivityUtils.isNetworkAvailable()){
                    retrieveUserFromService(userId)
                }
            }
    }

    private suspend fun retrieveUserFromService(userId: Long) {
        val userFromService = settingsRemoteDataSource.retrieveUserDetails(userId)

        if (userFromService.isSuccessful) {
            val userDetailsDto = userFromService.body()?.data
            if (userDetailsDto != null) {
                val userDetailsModel = userDetailsMapper.mapToDomainModel(userDetailsDto)
                userDetailsLocalDataSource.insertLocalUserDetails(userDetailsModel)
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
                settingsRemoteDataSource.updateUserDetails(
                    userId,
                    userDetailsMapper.mapFromDomainModel(userDetailsModel)
                )
            }
        ) {
            is Resource.Success -> {
                userDetailsLocalDataSource.insertLocalUserDetails(userDetailsModel)
                Log.i(TAG, "USER UPDATE SUCCESS ${resource.data}")
                Resource.Success(resource.data!!.body()!!)
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }
}