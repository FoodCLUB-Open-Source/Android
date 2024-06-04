package live.foodclub.presentation.ui.home.messaging

import live.foodclub.domain.models.auth.Contact
import live.foodclub.domain.models.auth.FirebaseUserModel
import live.foodclub.domain.models.auth.Message
import live.foodclub.domain.models.profile.SimpleUserModel

data class ContactsState(
    val contacts: List<Contact>,
    val contactsSearchText: String = "",
    val contactsSearchResult: List<Contact>,
    val followings: List<SimpleUserModel>,
    val followingsSearchText: String = "",
    val followingsSearchResult: List<SimpleUserModel>,
    val error: String = "",
) {

    companion object {
        fun default() = ContactsState(
            contacts = listOf(),
            contactsSearchText = "",
            contactsSearchResult = listOf(),
            error = "",
            followings = listOf(),
            followingsSearchResult = listOf(),
            followingsSearchText = ""
        )
    }
}

data class ChatState(
    val senderUser: FirebaseUserModel,
    val recipientUser: FirebaseUserModel,
    val conversationId: String,
    val messages: List<Message>,
    val error: String,
) {
    companion object {
        fun default() = ChatState(
            error = "",
            senderUser = FirebaseUserModel.default(),
            recipientUser = FirebaseUserModel.default(),
            conversationId = "",
            messages = listOf(),
        )
    }
}
