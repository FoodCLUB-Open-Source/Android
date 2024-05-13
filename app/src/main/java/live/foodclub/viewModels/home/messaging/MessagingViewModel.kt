package live.foodclub.viewModels.home.messaging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import live.foodclub.domain.models.auth.ConversationModel
import live.foodclub.network.retrofit.utils.SessionCache
import live.foodclub.repositories.FirebaseUserRepository
import live.foodclub.utils.helpers.Resource
import live.foodclub.views.home.messagingView.ChatState
import live.foodclub.views.home.messagingView.ContactsState
import javax.inject.Inject

// TODO get following users to start a conversation with them
// TODO Check all files for more readibility and clean up some repetitive codes if you can find.
@HiltViewModel
class MessagingViewModel @Inject constructor(
    private val sessionCache: SessionCache,
    private val firebaseUserRepository: FirebaseUserRepository,
) : ViewModel(), MessagingViewEvents {

    private val _contactsState = MutableStateFlow(ContactsState.default())
    val contactsState: StateFlow<ContactsState>
        get() = _contactsState

    private val _chatState = MutableStateFlow(ChatState.default())
    val chatState: StateFlow<ChatState>
        get() = _chatState

    private val currentUserId = sessionCache.session.value?.sessionUser?.userId!!.toInt()

    private var conversationListener: ListenerRegistration? = null

    init {
        getConversations()
    }

    private fun getConversations() = viewModelScope.launch {

        val result =
            firebaseUserRepository.getAllConversation(currentUserId)
        when (result) {
            is Resource.Error -> {
                _contactsState.update {
                    it.copy(
                        error = result.message!!
                    )
                }
            }

            is Resource.Success -> {
                conversationListener = result.data!!.addSnapshotListener { querySnapshot, error ->
                    if (querySnapshot != null && querySnapshot.isEmpty.not()) {
                        val contacts =
                            querySnapshot.documents.map { docSnapshot ->
                                docSnapshot.toObject<ConversationModel>()!!
                                    .mapToContact(currentUserId)
                            }
                        _contactsState.update {
                            it.copy(
                                contacts = contacts
                            )
                        }
                    } else {
                        _contactsState.update {
                            it.copy(
                                error = error?.message ?: "Unknown Error"
                            )
                        }
                    }
                }

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        conversationListener?.remove()
    }

    override fun setSearchText(searchText: String) {
        _contactsState.update { it.copy(contactsSearchText = searchText) }
        filterMessages(searchText)
    }

    override fun filterMessages(searchText: String) {
        val messages = contactsState.value.contacts
        val searchResult = messages.filter { people ->
            people.recipientName.contains(searchText, ignoreCase = true)
        }

        _contactsState.update {
            it.copy(
                contactsSearchText = searchText,
                contactsSearchResult = searchResult
            )
        }

    }

    override fun getConversation(documentId: String) {
        viewModelScope.launch {
            firebaseUserRepository.getConversation(documentId) { document, error ->
                if (document != null && document.exists()) {
                    val conversation = document.toObject<ConversationModel>()!!

                    val senderUser =
                        conversation.participants.first { currentUserId == it.userID }.mapToUser()
                    val recipientUser =
                        conversation.participants.first { currentUserId != it.userID }.mapToUser()

                    _chatState.update {
                        it.copy(
                            messages = conversation.messages.map { it.mapToMessage() },
                            senderUser = senderUser,
                            recipientUser = recipientUser,
                            conversationId = conversation.conversationId,
                        )
                    }

                } else {
                    _contactsState.update {
                        it.copy(
                            error = error!!.message!!
                        )
                    }
                }
            }

        }
    }

    override fun deleteConversation(documentId: String) {
        viewModelScope.launch {
            firebaseUserRepository.deleteConversation(documentId = documentId)
        }

        _contactsState.update { currentState ->
            val updatedContacts = currentState.contacts.toMutableList()
            updatedContacts.removeIf { it.conversationId == documentId }
            currentState.copy(
                contacts = updatedContacts
            )
        }
    }

    override fun sendMessage(content: String, conversationId: String) {
        viewModelScope.launch {
            firebaseUserRepository.sendMessage(
                content = content,
                currentUserId = currentUserId,
                conversationId = conversationId
            )
        }
    }

}
