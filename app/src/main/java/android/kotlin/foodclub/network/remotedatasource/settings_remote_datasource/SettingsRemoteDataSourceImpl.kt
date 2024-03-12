package android.kotlin.foodclub.network.remotedatasource.settings_remote_datasource

import android.kotlin.foodclub.network.retrofit.dtoModels.profile.UserDetailsDto
import android.kotlin.foodclub.network.retrofit.dtoModels.settings.ChangePasswordDto
import android.kotlin.foodclub.network.retrofit.responses.general.SingleMessageResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveUserDetailsResponse
import android.kotlin.foodclub.network.retrofit.responses.settings.UpdateUserDetailsResponse
import android.kotlin.foodclub.network.retrofit.services.SettingsService
import retrofit2.Response
import javax.inject.Inject

class SettingsRemoteDataSourceImpl @Inject constructor(
    private val settingsApi: SettingsService
) : SettingsRemoteDataSource{
    override suspend fun changePassword(changePasswordProfile: ChangePasswordDto): Response<SingleMessageResponse> {
        return settingsApi.changePassword(changePasswordProfile)
    }

    override suspend fun retrieveUserDetails(): Response<RetrieveUserDetailsResponse> {
        return settingsApi.retrieveUserDetails()
    }

    override suspend fun updateUserDetails(
        model: UserDetailsDto
    ): Response<UpdateUserDetailsResponse> {
        return settingsApi.updateUserDetails(model)
    }
}