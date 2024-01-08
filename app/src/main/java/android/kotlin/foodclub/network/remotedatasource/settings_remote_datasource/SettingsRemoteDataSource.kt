package android.kotlin.foodclub.network.remotedatasource.settings_remote_datasource

import android.kotlin.foodclub.network.retrofit.dtoModels.profile.UserDetailsDto
import android.kotlin.foodclub.network.retrofit.dtoModels.settings.ChangePasswordDto
import android.kotlin.foodclub.network.retrofit.responses.general.SingleMessageResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveUserDetailsResponse
import android.kotlin.foodclub.network.retrofit.responses.settings.UpdateUserDetailsResponse
import retrofit2.Response

interface SettingsRemoteDataSource {
    suspend fun changePassword(
        changePasswordProfile: ChangePasswordDto
    ): Response<SingleMessageResponse>

    suspend fun retrieveUserDetails(
        userId: Long
    ): Response<RetrieveUserDetailsResponse>

    suspend fun updateUserDetails(
        userId: Long,
        model: UserDetailsDto
    ): Response<UpdateUserDetailsResponse>
}