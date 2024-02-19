package android.kotlin.foodclub.repositories

import android.graphics.Bitmap
import android.kotlin.foodclub.network.retrofit.services.FcmService
import android.kotlin.foodclub.utils.helpers.firebase.NotificationService
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class FirebaseRepository @Inject constructor(
    private val firebaseMessaging: FirebaseMessaging,
    private val fcmService: FcmService,
    private val notificationService: NotificationService
) {

    companion object {
        private val TAG = FirebaseRepository::class.java.simpleName
    }

    suspend fun subscribeToTopic(topic: String, onCompleteListener: (Boolean) -> Unit) {
        fcmService.subscribeToTopic(topic) }

    suspend fun unsubscribeFromTopic(topic: String, onCompleteListener: (Boolean) -> Unit) {
        fcmService.unsubscribeFromTopic(topic)
    }

    // New method for sending a chat message
    suspend fun sendChatMessage(userId: String, message: String, onCompleteListener: (Boolean) -> Unit) {
        fcmService.sendChatMessage(userId, message)
    }

    // New method for receiving chat messages
    suspend fun receiveChatMessages(userId: String, onMessageReceived: (String) -> Unit) {
        fcmService.receiveChatMessages(userId)
    }

    fun sendNotification(message: RemoteMessage.Notification, bitmap: Bitmap?){
        notificationService.sendNotification(message, bitmap)
    }

}
