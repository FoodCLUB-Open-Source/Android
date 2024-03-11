package android.kotlin.foodclub.network.retrofit.utils.auth

import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    val sessionCache: SessionCache
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val activeSession = runBlocking {
            sessionCache.getActiveSession()
        }
        val request = chain.request().newBuilder()
        request.addHeader(
            "Authorisation", "Bearer ${activeSession?.accessToken} ${activeSession?.idToken}"
        )
        return chain.proceed(request.build())
    }

}