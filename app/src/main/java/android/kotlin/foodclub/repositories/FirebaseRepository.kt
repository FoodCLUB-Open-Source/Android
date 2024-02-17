package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.network.retrofit.services.FcmService
import android.kotlin.foodclub.utils.helpers.firebase.NotificationService
import android.util.Log
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

    fun subscribeToTopic(topic: String, onCompleteListener: (Boolean) -> Unit) {
        firebaseMessaging.subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Subscribed to $topic")
                    onCompleteListener.invoke(true)
                } else {
                    Log.w(TAG, "Subscribe failed", task.exception)
                    onCompleteListener.invoke(false)
                }
            }
    }

    fun unsubscribeFromTopic(topic: String, onCompleteListener: (Boolean) -> Unit) {
        firebaseMessaging.unsubscribeFromTopic(topic)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Unsubscribed from $topic")
                    onCompleteListener.invoke(true)
                } else {
                    Log.w(TAG, "Unsubscribe failed", task.exception)
                    onCompleteListener.invoke(false)
                }
            }
    }

    // New method for sending a chat message
    fun sendChatMessage(userId: String, message: String, onCompleteListener: (Boolean) -> Unit) {
        // Implement the logic to send a chat message to the backend
    }

    // New method for receiving chat messages
    fun receiveChatMessages(userId: String, onMessageReceived: (String) -> Unit) {
        // Implement the logic to listen for incoming chat messages from the backend
    }

    fun sendNotification(message: RemoteMessage.Notification){
        notificationService.sendNotification(message)
    }

}
