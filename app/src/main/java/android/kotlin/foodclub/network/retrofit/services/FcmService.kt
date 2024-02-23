package android.kotlin.foodclub.network.retrofit.services

import android.graphics.Bitmap
import com.google.firebase.messaging.RemoteMessage
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

//TODO waiting for backend to prepare endpoints
// Return types are empty for now
// This Interface will be used for V1
interface FcmService {

    @POST("subscribe")
    suspend fun subscribeToTopic(
        @Query("topic") topic: String
    )

    @POST("unsubscribe")
    suspend fun unsubscribeFromTopic(
        @Query("topic") topic: String
    )

    @POST("sendChatMessage")
    suspend fun sendChatMessage(
        @Query("userId") userId: String,
        @Query("message") message: String
    )

    @GET("receiveChatMessages")
    suspend fun receiveChatMessages(
        @Query("userId") userId: String
    )

    @POST("sendNotification")
    suspend fun sendNotification(
        @Body message: RemoteMessage.Notification,
        @Body bitmap: Bitmap?
    )
}