package android.kotlin.foodclub.utils.helpers.firebase

import com.google.firebase.messaging.RemoteMessage

interface NotificationService {
    fun sendNotification(message: RemoteMessage.Notification)
}
