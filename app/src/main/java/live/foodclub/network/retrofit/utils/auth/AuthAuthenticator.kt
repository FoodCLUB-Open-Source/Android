package live.foodclub.network.retrofit.utils.auth

import live.foodclub.domain.models.session.Session
import live.foodclub.network.retrofit.services.AuthenticationService
import live.foodclub.network.retrofit.dtoModels.auth.RefreshTokenDto
import live.foodclub.network.retrofit.dtoModels.auth.RefreshTokenRequestDto
import live.foodclub.network.retrofit.utils.SessionCache
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val sessionCache: SessionCache,
    private val refreshTokenManager: RefreshTokenManager,
    val api: AuthenticationService
) : Authenticator {
    private var tokenRefreshInProgress: AtomicBoolean = AtomicBoolean(false)

    override fun authenticate(route: Route?, response: Response): Request? {
        if (tokenRefreshInProgress.get()) {
            return runBlocking { waitForRefresh(response) }
        }
        tokenRefreshInProgress.set(true)
        val refreshToken = runBlocking { refreshTokenManager.getActiveToken() }

        return runBlocking {
            val newToken = getNewToken(refreshToken?.token, refreshToken?.username)
            val userId = sessionCache.getActiveSession()?.sessionUser?.userId ?: 0

            if (!newToken.isSuccessful || newToken.body() == null) { //Couldn't refresh the token, so restart the login process
                sessionCache.clearSession()
                refreshTokenManager.clearToken()
            }

            newToken.body()?.let {
                sessionCache.saveSession(Session(it.accessToken, it.idToken, userId))
                tokenRefreshInProgress.set(false)
                response.request.newBuilder()
                    .header("Authorisation", "Bearer ${it.accessToken} ${it.idToken}")
                    .build()
            }
        }
    }

    private suspend fun waitForRefresh(response: Response): Request {
        while (tokenRefreshInProgress.get()) {
            delay(100)
        }
        val newSession = sessionCache.getActiveSession()
        return response.request.newBuilder()
            .header(
                "Authorisation",
                "Bearer ${newSession?.accessToken} ${newSession?.idToken}"
            )
            .build()
    }

    private suspend fun getNewToken(
        refreshToken: String?, username: String?
    ): retrofit2.Response<RefreshTokenDto> {
        return api.refreshToken(
            RefreshTokenRequestDto(username ?: "", refreshToken ?: "")
        )
    }
}
