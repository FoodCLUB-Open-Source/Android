package android.kotlin.foodclub.domain.models.auth

data class ConversationModel(
    val conversationName: String,
    val messages: List<MessageModel>,
    val lastMessage: MessageModel?,
    val participants: List<Int>
)
