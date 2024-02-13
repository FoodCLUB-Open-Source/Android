package android.kotlin.foodclub.viewModels.home.messaging

import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.kotlin.foodclub.views.home.messagingView.MessagingSingleUser
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
): ViewModel() {

    private val _state = MutableStateFlow(MessagingViewState.default())
    val state: StateFlow<MessagingViewState>
        get() = _state

    init {
        _state.update { it.copy(
            userId = sessionCache.getActiveSession()?.sessionUser?.userId!!,
            userMessages = createDummyDataList()
        ) }
    }

    private fun createDummyDataList(): List<MessagingSingleUser> {
        val dummyDataList = mutableListOf<MessagingSingleUser>()

        for (i in 0 until 20) {
            val isMessageSeen = i % 2 == 0
            val unSeenMessageCount = if (!isMessageSeen) 0L else i.toLong()

            val dummyData = MessagingSingleUser(
                id = i,
                name = if (i == 0) "ChefAi" else "User $i",
                lastMessage = if (i == 0) "Yum, I'm sold on the to?..." else "Last message $i",
                isMessageSeen = isMessageSeen,
                lastMessageTime = "13:01",
                unSeenMessageCount = unSeenMessageCount
            )
            dummyDataList.add(dummyData)
        }

        return dummyDataList
    }
}