package android.kotlin.foodclub.network.retrofit.utils.auth

import android.kotlin.foodclub.data.models.Session
import android.kotlin.foodclub.network.retrofit.apiInterfaces.AuthenticationService
import android.kotlin.foodclub.network.retrofit.dtoModels.RefreshTokenDto
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val sessionCache: SessionCache,
    private val refreshTokenManager: RefreshTokenManager,
    val api: AuthenticationService
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            refreshTokenManager.getActiveToken()?.token
        }
        return runBlocking {
            val newToken = getNewToken(token)

            if (!newToken.isSuccessful || newToken.body() == null) { //Couldn't refresh the token, so restart the login process
                sessionCache.clearSession()
                refreshTokenManager.clearToken()
            }

            newToken.body()?.let {
                sessionCache.saveSession(Session(it.token))
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.token}")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<RefreshTokenDto> {
        return api.refreshToken("Bearer $refreshToken")
    }
}
