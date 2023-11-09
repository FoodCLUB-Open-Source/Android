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
import retrofit2.http.Path

interface SettingsService {
    @POST("login/change_password")
    suspend fun changePassword(
        @Body changePasswordProfile: ChangePasswordDto
    ): Response<SingleMessageResponse>

    @GET("profile/{user_id}/details")
    suspend fun retrieveUserDetails(
        @Path("user_id") userId: Long
    ): Response<RetrieveUserDetailsResponse>

    @PUT("profile/profile_details/{user_id}")
    suspend fun updateUserDetails(
        @Path("user_id") userId: Long,
        @Body model: UserDetailsDto
    ): Response<UpdateUserDetailsResponse>

}