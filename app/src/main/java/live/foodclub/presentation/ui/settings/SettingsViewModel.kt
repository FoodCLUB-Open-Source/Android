package live.foodclub.presentation.ui.settings

import live.foodclub.localdatasource.room.entity.UserDetailsModel
import live.foodclub.network.retrofit.utils.SessionCache
import live.foodclub.repositories.SettingsRepository
import live.foodclub.utils.helpers.Resource
import live.foodclub.utils.helpers.StoreData
import android.util.Log
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
    val sessionCache: SessionCache,
    private val dataStore: StoreData
) : ViewModel(), SettingsEvents {
    companion object {
        private val TAG = SettingsViewModel::class.java.simpleName
    }

    private val _state = MutableStateFlow(SettingsState.default())
    val state: StateFlow<SettingsState>
        get() = _state

    init {
        val id = sessionCache.getActiveSession()!!.sessionUser.userId
        _state.update { state->
            state.copy(
                dataStore = dataStore
            )
        }
        getUserDetails(id)
    }

    override fun logout() {
        sessionCache.clearSession()
    }

    override fun changePassword(oldPassword: String, newPassword: String) {
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
            Log.d(TAG, "getUserDetails: $id")
            repository.retrieveUserDetails(id).collect { userResource ->
                when (userResource) {
                    is Resource.Success -> {
                        _state.update { it.copy(user = userResource.data) }
                    }

                    is Resource.Error -> {
                        Log.e(TAG, "getUserDetails failed: ${userResource.message}")
                    }
                }

            }
        }
    }

    override fun updateUserDetails(userId: Long, user: UserDetailsModel) {
        viewModelScope.launch {
            when (val resource = repository.updateUserDetails(userId, user)) {
                is Resource.Success -> {
                    Log.i(TAG, "USER UPDATE SUCCESS ${resource.data}")
                }

                is Resource.Error -> {
                    Log.e(TAG, "USER UPDATE FAILED ${resource.message}")

                }
            }
        }
        _state.update { it.copy(user = user) }
    }
}