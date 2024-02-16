package android.kotlin.foodclub.viewModels.home.messaging

import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.kotlin.foodclub.views.home.messagingView.MessagingViewData
import android.kotlin.foodclub.views.home.messagingView.MessagingViewState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MessagingViewModel @Inject constructor(
    private val sessionCache: SessionCache
): ViewModel(), MessagingViewEvents {

    private val _state = MutableStateFlow(MessagingViewState.default())
    val state: StateFlow<MessagingViewState>
        get() = _state

    init {
        _state.update { it.copy(
            userId = sessionCache.getActiveSession()?.sessionUser?.userId!!,
            userMessagesHistory = createDummyDataList()
        ) }
    }

    private fun createDummyDataList(): List<MessagingViewData> {
        val dummyDataList = mutableListOf<MessagingViewData>()

        for (i in 0 until 20) {
            val isMessageSeen = i % 2 == 0
            val unSeenMessageCount = if (!isMessageSeen) 0L else i.toLong()

            val dummyData = MessagingViewData(
                id = i,
                name = if (i == 0) "ChefAi" else "User $i",
                lastMessage = if (i == 0) "Yum, I'm sold on to it?..." else "Last message $i",
                isMessageSeen = isMessageSeen,
                lastMessageTime = "13:01",
                unSeenMessageCount = unSeenMessageCount
            )
            dummyDataList.add(dummyData)
        }

        return dummyDataList
    }

    override fun setSearchText(searchText: String) {
        _state.update { it.copy(messagingViewSearchText = searchText) }
        filterMessages(searchText)
    }

    override fun filterMessages(searchText: String) {
        val messages = state.value.userMessagesHistory
        val searchedList = messages.filter { people ->
            people.name.contains(searchText, ignoreCase = true)
        }

        _state.update {
            it.copy(
                messagingViewSearchText = searchText,
                userSearchResult = searchedList.toList()
            )
        }
    }
}