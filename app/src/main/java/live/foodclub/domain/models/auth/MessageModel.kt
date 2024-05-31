package live.foodclub.domain.models.auth

data class MessageModel(
    val messageId: String = "",
    val senderId: Int = 0,
    val recipientId: Int = 0,
    var read: Boolean = false,
    val content: String = "",
    val timestamp: String = "",
) {
    fun mapToMessage(): Message {
        return Message(
            senderId = senderId,
            content = content,
            timestamp = timestamp
        )
    }
}
