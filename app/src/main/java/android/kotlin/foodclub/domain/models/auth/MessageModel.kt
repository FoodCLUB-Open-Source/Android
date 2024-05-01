package android.kotlin.foodclub.domain.models.auth

data class MessageModel(
    val messageId: String,
    val senderId: Int,
    val recipientId: Int,
    val read: Boolean,
    val content: String,
    val timeStamp: String,
)
