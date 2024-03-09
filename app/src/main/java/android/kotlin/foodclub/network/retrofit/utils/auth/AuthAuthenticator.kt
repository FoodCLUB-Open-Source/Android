package android.kotlin.foodclub.network.retrofit.utils.auth

import android.kotlin.foodclub.domain.models.session.Session
import android.kotlin.foodclub.network.retrofit.services.AuthenticationService
import android.kotlin.foodclub.network.retrofit.dtoModels.auth.RefreshTokenDto
import android.kotlin.foodclub.network.retrofit.dtoModels.auth.RefreshTokenRequestDto
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
        val username = runBlocking {
            refreshTokenManager.getActiveToken()?.username
        }
        return runBlocking {
            val newToken = getNewToken(token, username)

            if (!newToken.isSuccessful || newToken.body() == null) { //Couldn't refresh the token, so restart the login process
                sessionCache.clearSession()
                refreshTokenManager.clearToken()
            }

            newToken.body()?.let {
                sessionCache.saveSession(Session(it.accessToken, it.idToken))
                response.request.newBuilder()
                    .header("Authorisation", "Bearer ${it.accessToken} ${it.idToken}")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(
        refreshToken: String?, username: String?
    ): retrofit2.Response<RefreshTokenDto> {
        return api.refreshToken(
            RefreshTokenRequestDto(username ?: "", refreshToken ?: "")
        )
    }
}
