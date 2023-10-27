package android.kotlin.foodclub.network.retrofit.services

import android.kotlin.foodclub.network.retrofit.dtoModels.settings.ChangePasswordDto
import android.kotlin.foodclub.network.retrofit.responses.general.SingleMessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SettingsService {
    @POST("login/change_password")
    suspend fun changePassword(
        @Body changePasswordProfile: ChangePasswordDto
    ): Response<SingleMessageResponse>
}