package android.kotlin.foodclub.viewmodels.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.kotlin.foodclub.repositories.AuthRepository
import android.kotlin.foodclub.utils.helpers.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    val repository: AuthRepository
) : ViewModel() {
    private val _errorOccurred = MutableStateFlow(false)
    val errorOccurred: StateFlow<Boolean> get() = _errorOccurred

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> get() = _message

    private val _email = MutableStateFlow<String>("")
    val email: StateFlow<String> get() = _email

    fun sendCode(email: String, onSuccess: () -> Unit){
        _email.value = email
        viewModelScope.launch {
            when(
                val resource = repository.sendForgotPasswordCode(email)
            ) {
                is Resource.Success -> {
                    _errorOccurred.value = false
                    _message.value = ""
                    onSuccess()
                }
                is Resource.Error -> {
                    _errorOccurred.value = true
                    _message.value = resource.message!!
                }
            }
        }
    }

    fun changePassword(verificationCode: String, password: String, onSuccess: () -> Unit){
        if(_email.value == "") {
            _errorOccurred.value = true
            _message.value = "Something went wrong. Try again."
            return
        }
        viewModelScope.launch {
            when(
                val resource = repository.confirmForgotPasswordChange(
                    _email.value!!, verificationCode, password
                )
            ) {
                is Resource.Success -> {
                    _errorOccurred.value = false
                    _message.value = ""
                    onSuccess()
                }
                is Resource.Error -> {
                    _errorOccurred.value = true
                    _message.value = resource.message!!
                }
            }
        }
    }

}
