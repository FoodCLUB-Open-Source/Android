package android.kotlin.foodclub.viewmodels.authentication

import android.kotlin.foodclub.domain.models.session.Session
import android.kotlin.foodclub.domain.enums.ApiCallStatus
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import android.kotlin.foodclub.navigation.auth.AuthScreen
import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.repositories.AuthRepository
import android.kotlin.foodclub.network.retrofit.utils.auth.JWTManager
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupVerificationViewModel @Inject constructor(
    val repository: AuthRepository,
    private val sessionCache: SessionCache
) : ViewModel() {
    private val _status = MutableStateFlow(ApiCallStatus.DONE)
    val status: StateFlow<ApiCallStatus> get() = _status

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> get() = _message

    private val _errorOccurred = MutableStateFlow(false)
    val errorOccurred: StateFlow<Boolean> get() = _errorOccurred

    private val _username = MutableStateFlow<String?>(null)
    private val _password = MutableStateFlow<String?>(null)

    private val _navController = MutableStateFlow<NavHostController?>(null)
    val navController: StateFlow<NavHostController?> get() = _navController


    fun sendVerificationCode() {
        if(_username.value == null) {
            _errorOccurred.value = true
            _message.value = "Unknown error occurred. Try again later"
            _navController.value?.navigate(Graph.AUTHENTICATION)
            return
        }

        viewModelScope.launch {
            when(
                val resource = repository.resendAccountVerificationCode(_username.value!!)
            ) {
                is Resource.Success -> {
                    _errorOccurred.value = false
                    _message.value = resource.data!!.message.replaceFirstChar(Char::uppercase)
                }
                is Resource.Error -> {
                    _errorOccurred.value = true
                    _message.value = resource.message!!
                }
            }
        }

    }

    fun verifyCode(code: String) {
        if(_username.value == null) {
            _errorOccurred.value = true
            _message.value = "Unknown error occurred. Try again later"
            _navController.value?.navigate(Graph.AUTHENTICATION)
            return
        }

        viewModelScope.launch {
            when(
                val resource = repository.verifyAccount(_username.value!!, code)
            ) {
                is Resource.Success -> {
                    _errorOccurred.value = false
                    _message.value = ""
                    logInUser()
                }
                is Resource.Error -> {
                    _errorOccurred.value = true
                    _message.value = resource.message!!
                }
            }
        }
    }

    private fun logInUser() {
        if (_password.value == null) {
            _navController.value?.navigate(AuthScreen.Login.route)
            return
        }

        viewModelScope.launch() {
            when(val resource = repository.signIn(_username.value!!, _password.value!!)) {
                is Resource.Success -> {
                    _errorOccurred.value = false
                    _message.value = ""
                    setSession(resource.data!!.id.toLong())
                    _navController.value?.navigate(Graph.HOME) {
                        popUpTo(Graph.AUTHENTICATION) { inclusive = true }
                    }
                }
                is Resource.Error -> {
                    _errorOccurred.value = true
                    _message.value = resource.message!!
                    _navController.value?.navigate(AuthScreen.Login.route)
                }
            }
        }
    }

    private fun setSession(userId: Long) {
        if(sessionCache.getActiveSession() != null) sessionCache.clearSession()
        sessionCache.saveSession(Session(JWTManager.createJWT(userId)!!))
        Log.d("AccountVerification", "Logged in user: $userId")
    }

    fun setData(navController: NavHostController, username: String?, password: String?) {
        _navController.value = navController
        _username.value = username
        _password.value = password
    }
}