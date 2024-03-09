package android.kotlin.foodclub.network.retrofit.utils.auth

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ResponsesInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val responseBody = originalResponse.peekBody(Long.MAX_VALUE)
        Log.d("Interceptor", responseBody.string())
        if (originalResponse.code == 404
            && responseBody.string().contains("Token expired")) {
            return originalResponse.newBuilder().code(401).build()
        }
        return originalResponse
    }
}