package live.foodclub.utils.helpers.firebase

import android.graphics.Bitmap
import com.google.firebase.messaging.RemoteMessage

interface NotificationService {
    fun sendNotification(message: RemoteMessage.Notification, bitmap: Bitmap?)
}
