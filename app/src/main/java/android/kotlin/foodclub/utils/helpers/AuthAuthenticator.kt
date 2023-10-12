package android.kotlin.foodclub.utils.helpers

import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.kotlin.foodclub.data.models.Session
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val sessionCache: SessionCache,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            sessionCache.getActiveSession()?.token
        }
        return runBlocking {
            val newToken = getNewToken(token)

            if (!newToken.isSuccessful || newToken.body() == null) { //Couldn't refresh the token, so restart the login process
                sessionCache.clearSession()
            }

            newToken.body()?.let {
                sessionCache.saveSession(Session(it.token))
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.token}")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): Response<LoginResponse> {
        return RetrofitInstance.retrofitApi.refreshToken("Bearer $refreshToken")
    }
}
