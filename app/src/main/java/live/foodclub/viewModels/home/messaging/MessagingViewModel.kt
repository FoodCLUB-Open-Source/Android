package live.foodclub.viewModels.home.messaging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import live.foodclub.domain.models.auth.ConversationModel
import live.foodclub.network.retrofit.utils.SessionCache
import live.foodclub.repositories.FirebaseUserRepository
import live.foodclub.repositories.ProfileRepository
import live.foodclub.utils.helpers.Resource
import live.foodclub.views.home.messagingView.ChatState
import live.foodclub.views.home.messagingView.ContactsState
import javax.inject.Inject

@HiltViewModel
class MessagingViewModel @Inject constructor(
    private val sessionCache: SessionCache,
    private val firebaseUserRepository: FirebaseUserRepository,
    private val profileRepository: ProfileRepository,
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
        getFollowers()
    }

    private fun getFollowers() = viewModelScope.launch(Dispatchers.IO) {
        val result = profileRepository.retrieveProfileFollowing(currentUserId.toLong())

        when (result) {
            is Resource.Success -> {
                val followings = result.data!!
                viewModelScope.launch(Dispatchers.Main) {
                    _contactsState.update {
                        it.copy(
                            followings = followings,
                        )
                    }
                }
            }

            is Resource.Error -> {
                viewModelScope.launch(Dispatchers.Main) {
                    _contactsState.update {
                        it.copy(
                            error = result.message!!
                        )
                    }
                }
            }
        }
    }


    private fun getConversations() = viewModelScope.launch(Dispatchers.IO) {

        val result =
            firebaseUserRepository.getAllConversation(currentUserId)
        when (result) {
            is Resource.Error -> {
                viewModelScope.launch(Dispatchers.Main) {
                    _contactsState.update {
                        it.copy(
                            error = result.message!!
                        )
                    }
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
                        viewModelScope.launch(Dispatchers.Main) {
                            _contactsState.update {
                                it.copy(
                                    contacts = contacts
                                )
                            }
                        }
                    } else {
                        viewModelScope.launch(Dispatchers.Main) {
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
    }

    override fun onCleared() {
        super.onCleared()
        conversationListener?.remove()
    }

    override fun setFollowingsSearchText(searchText: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _contactsState.update { it.copy(followingsSearchText = searchText) }
        }
        filterFollowings(searchText)
    }

    override fun filterFollowings(searchText: String) {
        val followings = contactsState.value.followings
        val searchResult = followings.filter { following ->
            following.username.contains(searchText, ignoreCase = true)
        }
        _contactsState.update {
            it.copy(
                followingsSearchResult = searchResult,
                followingsSearchText = searchText
            )
        }
    }

    override fun setSearchText(searchText: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _contactsState.update { it.copy(contactsSearchText = searchText) }
            filterContacts(searchText)
        }
    }

    override fun filterContacts(searchText: String) {
        val contacts = contactsState.value.contacts
        val searchResult = contacts.filter { contact ->
            contact.recipientFullName.contains(searchText, ignoreCase = true)
        }
        viewModelScope.launch(Dispatchers.Main) {
            _contactsState.update {
                it.copy(
                    contactsSearchText = searchText,
                    contactsSearchResult = searchResult
                )
            }
        }

    }


    override fun createConversation(recipientUserId: Int) {
        // this should shifted to IO thread
        viewModelScope.launch(Dispatchers.IO) {
            val result = firebaseUserRepository
                .createConversation(senderId = currentUserId, recipientId = recipientUserId)

            when (result) {
                is Resource.Success -> {
                    val newConversation = result.data!!
                    val senderUser =
                        newConversation.participants.first { currentUserId == it.userID }
                            .mapToSimpleUserModel()
                    val recipientUser =
                        newConversation.participants.first { currentUserId != it.userID }
                            .mapToSimpleUserModel()
                    // this should shifted to main thread
                    viewModelScope.launch(Dispatchers.Main) {
                        _chatState.update { currentState ->
                            currentState.copy(
                                messages = newConversation.messages.map { it.mapToMessage() },
                                senderUser = senderUser,
                                recipientUser = recipientUser,
                                conversationId = newConversation.conversationId,
                                error = ""
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    viewModelScope.launch(Dispatchers.Main) {
                        _chatState.update {
                            it.copy(
                                error = result.message!!
                            )
                        }
                    }
                }
            }

        }
    }

    override fun getConversation(documentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseUserRepository.getConversation(documentId) { document, error ->
                if (document != null && document.exists()) {
                    val conversation = document.toObject<ConversationModel>()!!

                    val senderUser =
                        conversation.participants.first { currentUserId == it.userID }
                            .mapToSimpleUserModel()
                    val recipientUser =
                        conversation.participants.first { currentUserId != it.userID }
                            .mapToSimpleUserModel()
                    viewModelScope.launch(Dispatchers.Main) {
                        _chatState.update { currentState ->
                            currentState.copy(
                                messages = conversation.messages.map { it.mapToMessage() },
                                senderUser = senderUser,
                                recipientUser = recipientUser,
                                conversationId = conversation.conversationId,
                            )
                        }
                    }

                } else {
                    viewModelScope.launch(Dispatchers.Main) {
                        _chatState.update {
                            it.copy(
                                error = error?.message ?: "An error occurred"
                            )
                        }
                    }
                }
            }

        }
    }

    override fun deleteConversation(documentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseUserRepository.deleteConversation(documentId = documentId)
        }
        viewModelScope.launch(Dispatchers.Main) {
            _contactsState.update { currentState ->
                val updatedContacts = currentState.contacts.toMutableList()
                updatedContacts.removeIf { it.conversationId == documentId }
                currentState.copy(
                    contacts = updatedContacts
                )
            }
        }
    }

    override fun sendMessage(content: String, conversationId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseUserRepository.sendMessage(
                content = content,
                currentUserId = currentUserId,
                conversationId = conversationId
            )
        }
    }

}
