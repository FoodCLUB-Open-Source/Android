package android.kotlin.foodclub.viewModels.home

import android.kotlin.foodclub.domain.models.profile.UserDetailsModel
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.kotlin.foodclub.repositories.SettingsRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository,
    val sessionCache: SessionCache
) : ViewModel() {

    var user by mutableStateOf<UserDetailsModel?>(null)

    private val _errorType = MutableStateFlow<String?>(null)
    val errorType: StateFlow<String?> get() = _errorType

    private var _userDetails = MutableStateFlow<UserDetailsModel?>(null)
    val userDetails: StateFlow<UserDetailsModel?> get() = _userDetails

    init {
        val id = sessionCache.getActiveSession()!!.sessionUser.userId
        getUserDetails(id)
    }

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

    private fun getUserDetails(id: Long){
        viewModelScope.launch {
            when(val resource = repository.retrieveUserDetails(id)){
                is Resource.Success -> {
                    _userDetails.value = resource.data
                    Log.i("MYTAG","getUserDetails success settings screen: ${resource.data}")
                }
                is Resource.Error -> {
                    Log.i("MYTAG","getUserDetails failed: ${resource.message}")
                }
            }
        }
    }

    fun updateUserDetails(userId: Long, model: UserDetailsModel){
        viewModelScope.launch {
            when(val resource = repository.updateUserDetails(userId, model)){
                is Resource.Success -> {
                    Log.i("MYTAG","USER UPDATE SUCCESS ${resource.data}")
                }
                is Resource.Error -> {
                    Log.e("MYTAG","USER UPDATE FAILED ${resource.message}")

                }
            }
        }
        _userDetails.value = model
    }
}