package android.kotlin.foodclub.network.retrofit.services

import android.kotlin.foodclub.network.retrofit.dtoModels.profile.UserDetailsDto
import android.kotlin.foodclub.network.retrofit.dtoModels.settings.ChangePasswordDto
import android.kotlin.foodclub.network.retrofit.responses.general.SingleMessageResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveUserDetailsResponse
import android.kotlin.foodclub.network.retrofit.responses.settings.UpdateUserDetailsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface SettingsService {
    @POST("login/change_password")
    suspend fun changePassword(
        @Body changePasswordProfile: ChangePasswordDto
    ): Response<SingleMessageResponse>

    @GET("profile/details")
    suspend fun retrieveUserDetails(): Response<RetrieveUserDetailsResponse>

    @PUT("profile/profile_details")
    suspend fun updateUserDetails(
        @Body model: UserDetailsDto
    ): Response<UpdateUserDetailsResponse>

}