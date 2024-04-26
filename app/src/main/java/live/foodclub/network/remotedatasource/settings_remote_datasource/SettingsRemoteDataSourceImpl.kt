package live.foodclub.network.remotedatasource.settings_remote_datasource

import live.foodclub.network.retrofit.dtoModels.profile.UserDetailsDto
import live.foodclub.network.retrofit.dtoModels.settings.ChangePasswordDto
import live.foodclub.network.retrofit.responses.general.SingleMessageResponse
import live.foodclub.network.retrofit.responses.profile.RetrieveUserDetailsResponse
import live.foodclub.network.retrofit.responses.settings.UpdateUserDetailsResponse
import live.foodclub.network.retrofit.services.SettingsService
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