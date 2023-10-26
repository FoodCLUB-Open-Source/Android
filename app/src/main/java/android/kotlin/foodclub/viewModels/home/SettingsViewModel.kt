package android.kotlin.foodclub.viewModels.home

import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.kotlin.foodclub.repositories.SettingsRepository
import android.kotlin.foodclub.utils.helpers.Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val repository: SettingsRepository,
    val sessionCache: SessionCache
) : ViewModel() {
    private val _errorType = MutableStateFlow<String?>(null)
    val errorType: StateFlow<String?> get() = _errorType

    fun logout() {
        sessionCache.clearSession()
    }
    fun changePassword(oldPassword: String, newPassword: String) {
        if(oldPassword == newPassword) {
            _errorType.value = "Old and New passwords must be different."
            return
        }

        viewModelScope.launch {
            when(val resource = repository.changePassword(oldPassword, newPassword)) {
                is Resource.Success -> {
                    _errorType.value = "Password changed successfully"
                }
                is Resource.Error -> {
                    _errorType.value = "Bad request. Please review your old password"
                }
            }
        }
    }
}