package android.kotlin.foodclub.viewModels.home.messaging

import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.kotlin.foodclub.views.home.messagingView.MessagingViewState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MessagingViewModel @Inject constructor(
    private val sessionCache: SessionCache
): ViewModel() {

    private val _state = MutableStateFlow(MessagingViewState.default())
    val state: StateFlow<MessagingViewState>
        get() = _state

}