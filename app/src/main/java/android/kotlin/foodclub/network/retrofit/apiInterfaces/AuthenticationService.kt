package android.kotlin.foodclub.network.retrofit.apiInterfaces

import android.kotlin.foodclub.network.retrofit.dtoModels.RefreshTokenDto
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenticationService {
    @POST("login/refresh")
    suspend fun refreshToken(
        @Header("Authorization") token: String
    ): Response<RefreshTokenDto>
}