package android.kotlin.foodclub.domain.models.auth

data class ConversationModel(
    val conversationName: String = "",
    val messages: MutableList<MessageModel> = mutableListOf(),
    var lastMessage: MessageModel? = null,
    val participants: List<Int> = listOf(),
)
