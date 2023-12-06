package android.kotlin.foodclub.viewModels.home

import android.kotlin.foodclub.domain.models.profile.UserDetailsModel
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.kotlin.foodclub.repositories.SettingsRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.views.settings.SettingsState
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository,
    val sessionCache: SessionCache
) : ViewModel() {
    companion object {
        private val TAG = SettingsViewModel::class.java.simpleName
    }

    private val _state = MutableStateFlow(SettingsState.default())
    val state: StateFlow<SettingsState>
        get() = _state

    init {
        val id = sessionCache.getActiveSession()!!.sessionUser.userId
        getUserDetails(id)
    }

    fun logout() {
        sessionCache.clearSession()
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        if (oldPassword == newPassword) {
            _state.update { it.copy(error = "Old and New passwords must be different.") }
            return
        }

        viewModelScope.launch {
            when (repository.changePassword(oldPassword, newPassword)) {
                is Resource.Success -> {
                    _state.update { it.copy(error = "Password changed successfully") }
                }

                is Resource.Error -> {
                    _state.update { it.copy(error = "Bad request. Please review your old password") }
                }
            }
        }
    }

    private fun getUserDetails(id: Long) {
        viewModelScope.launch {
            when (val resource = repository.retrieveUserDetails(id)) {
                is Resource.Success -> {
                    _state.update { it.copy(user = resource.data) }
                    Log.i(TAG, "getUserDetails success settings screen: ${resource.data}")
                }

                is Resource.Error -> {
                    Log.i(TAG, "getUserDetails failed: ${resource.message}")
                }
            }
        }
    }

    fun updateUserDetails(userId: Long, model: UserDetailsModel) {
        viewModelScope.launch {
            when (val resource = repository.updateUserDetails(userId, model)) {
                is Resource.Success -> {
                    Log.i(TAG, "USER UPDATE SUCCESS ${resource.data}")
                }

                is Resource.Error -> {
                    Log.e(TAG, "USER UPDATE FAILED ${resource.message}")

                }
            }
        }
        _state.update { it.copy(user = model) }
    }
}