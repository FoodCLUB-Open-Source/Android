package live.foodclub.network.retrofit.utils.auth

import live.foodclub.network.retrofit.responses.general.SingleMessageResponse
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import java.util.zip.GZIPInputStream

class ResponsesInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        val responseBody = try {
            val responseText = if(isGzipped(originalResponse)) {
                GZIPInputStream(
                    originalResponse.peekBody(Long.MAX_VALUE).byteStream()
                ).bufferedReader(Charsets.UTF_8).use { it.readText() }
            } else {
                originalResponse.peekBody(Long.MAX_VALUE).string()
            }

            Gson().fromJson(responseText, SingleMessageResponse::class.java)
        } catch (e: Exception) {
            SingleMessageResponse(message = "${originalResponse.code} ${originalResponse.message}")
        }

        if (originalResponse.code == 404
            && responseBody?.message?.contains("Token expired") == true) {
            val newRequest = originalResponse.request
                .newBuilder().removeHeader("Authorisation").build()
            return originalResponse.newBuilder().request(newRequest).code(401).build()
        }
        return originalResponse
    }

    private fun isGzipped(response: Response): Boolean {
        return response.header("Content-Encoding") != null && response.header("Content-Encoding")
            .equals("gzip")
    }
}