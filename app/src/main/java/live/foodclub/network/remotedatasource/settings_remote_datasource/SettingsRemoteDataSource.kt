package live.foodclub.network.remotedatasource.settings_remote_datasource

import live.foodclub.network.retrofit.dtoModels.profile.UserDetailsDto
import live.foodclub.network.retrofit.dtoModels.settings.ChangePasswordDto
import live.foodclub.network.retrofit.responses.general.SingleMessageResponse
import live.foodclub.network.retrofit.responses.profile.RetrieveUserDetailsResponse
import live.foodclub.network.retrofit.responses.settings.UpdateUserDetailsResponse
import retrofit2.Response

interface SettingsRemoteDataSource {
    suspend fun changePassword(
        changePasswordProfile: ChangePasswordDto
    ): Response<SingleMessageResponse>

    suspend fun retrieveUserDetails(): Response<RetrieveUserDetailsResponse>

    suspend fun updateUserDetails(
        model: UserDetailsDto
    ): Response<UpdateUserDetailsResponse>
}