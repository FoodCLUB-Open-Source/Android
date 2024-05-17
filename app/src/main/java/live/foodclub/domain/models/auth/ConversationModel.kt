package live.foodclub.domain.models.auth

import live.foodclub.utils.helpers.DateTimeHelper


data class ConversationModel(
    val conversationId: String = "",
    var lastMessage: MessageModel? = null,
    val participants: List<FirebaseUserModel> = listOf(),
    val messages: List<MessageModel> = listOf(),
    val participantIds: List<Int> = listOf(),
    val lastUpdated: String = "",
) {
    fun mapToContact(
        currentUserId: Int,
    ): Contact {
        val recipientUser = participants.first { it.userID != currentUserId }

        val formattedLastMessageTime = DateTimeHelper.formatMessageTime(lastMessage?.timestamp)
        return Contact(
            conversationId = conversationId,
            recipientId = recipientUser.userID,
            recipientFullName = recipientUser.fullName,
            lastMessage = lastMessage?.content ?: "",
            lastMessageTime = formattedLastMessageTime,
            isMessageSeen = lastMessage?.read ?: false,
            recipientProfileImage = recipientUser.profileImageUrl,
            unSeenMessageCount = messages.count {
                it.senderId != currentUserId && !it.read
            },
            recipientUsername = recipientUser.username
        )
    }
}

data class Contact(
    val conversationId: String = "",
    val recipientId: Int = 1,
    val recipientFullName: String = "",
    val recipientUsername: String = "",
    val recipientProfileImage: String = "",
    val lastMessage: String = "",
    val lastMessageTime: String = "",
    val isMessageSeen: Boolean = false,
    val unSeenMessageCount: Int = 0,
)

data class Message(
    val senderId: Int,
    val content: String,
    val timestamp: String
)