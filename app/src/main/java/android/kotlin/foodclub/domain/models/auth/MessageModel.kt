package android.kotlin.foodclub.domain.models.auth

data class MessageModel(
    val messageId: String = "",
    val senderId: Int = 0,
    val recipientId: Int = 0,
    val read: Boolean = false,
    val content: String = "",
    val timeStamp: String = "",
)
