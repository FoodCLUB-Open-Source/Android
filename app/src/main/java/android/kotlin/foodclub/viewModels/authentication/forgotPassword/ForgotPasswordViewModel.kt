package android.kotlin.foodclub.viewModels.authentication.forgotPassword

import android.kotlin.foodclub.domain.models.auth.ForgotChangePassword
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.kotlin.foodclub.repositories.AuthRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.views.authentication.forgotPassword.forgotPasswordScreen.ForgotPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    val repository: AuthRepository
) : ViewModel(), ForgotPasswordEvents {

    private val _state = MutableStateFlow(ForgotPasswordState.default())
    val state : StateFlow<ForgotPasswordState>
        get() = _state

    override fun sendCode(email: String, onSuccess: () -> Unit){
        viewModelScope.launch {
            when(
                val resource = repository.sendForgotPasswordCode(email)
            ) {
                is Resource.Success -> {
                    _state.update { it.copy(
                        email = email,
                        errorOccurred = false,
                        message = ""
                    ) }
                    onSuccess()
                }
                is Resource.Error -> {
                    _state.update { it.copy(
                        email = email,
                        errorOccurred = true,
                        message = resource.message!!
                    ) }
                }
            }
        }
    }

    override fun changePassword(verificationCode: String, password: String, onSuccess: () -> Unit){
        if(state.value.email == "") {
            _state.update { it.copy(
                errorOccurred = true,
                message = "Something went wrong. Try again."
            ) }
            return
        }
        viewModelScope.launch {
            when(
                val resource = repository.confirmForgotPasswordChange(
                    ForgotChangePassword(state.value.email, verificationCode, password)
                )
            ) {
                is Resource.Success -> {
                    _state.update { it.copy(
                        errorOccurred = false,
                        message = ""
                    ) }
                    onSuccess()
                }
                is Resource.Error -> {
                    _state.update { it.copy(
                        errorOccurred = true,
                        message = resource.message!!
                    ) }
                }
            }
        }
    }

    override fun onEmailChange(email: String){
        _state.update { it.copy(email = email) }
    }

}
