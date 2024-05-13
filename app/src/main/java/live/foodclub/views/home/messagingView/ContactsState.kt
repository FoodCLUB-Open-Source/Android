package live.foodclub.views.home.messagingView

import live.foodclub.domain.models.auth.Contact
import live.foodclub.domain.models.auth.Message
import live.foodclub.domain.models.auth.User

data class ContactsState(
    val contacts: List<Contact>,
    val contactsSearchText: String = "",
    val contactsSearchResult: List<Contact>,
    val error: String = "",
) {

    companion object {
        fun default() = ContactsState(
            contacts = listOf(),
            contactsSearchText = "",
            contactsSearchResult = listOf(),
            error = "",
        )
    }
}

data class ChatState(
    val senderUser: User,
    val recipientUser: User,
    val conversationId: String,
    val messages: List<Message>,
    val error: String,
) {
    companion object {
        fun default() = ChatState(
            error = "",
            senderUser = User.default(),
            recipientUser = User.default(),
            conversationId = "",
            messages = listOf(),
        )
    }
}

