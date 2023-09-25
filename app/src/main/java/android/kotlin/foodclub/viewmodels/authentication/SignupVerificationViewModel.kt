package android.kotlin.foodclub.viewmodels.authentication

import android.kotlin.foodclub.api.authentication.SignUpResponseMessage
import android.kotlin.foodclub.api.authentication.VerificationCodeRequestData
import android.kotlin.foodclub.api.authentication.VerificationCodeResendData
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.kotlin.foodclub.data.models.Session
import android.kotlin.foodclub.utils.enums.ApiCallStatus
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import android.kotlin.foodclub.navigation.graphs.AuthScreen
import android.kotlin.foodclub.navigation.graphs.Graph
import android.kotlin.foodclub.repositories.AuthRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.utils.helpers.SessionCache
import android.kotlin.foodclub.utils.helpers.ValueParser
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SignupVerificationViewModel @Inject constructor(
    val repository: AuthRepository,
    private val sessionCache: SessionCache
) : ViewModel() {
    private val _status = MutableStateFlow<ApiCallStatus>(ApiCallStatus.DONE)
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
            try {
                val response = RetrofitInstance.retrofitApi.resendCode(VerificationCodeResendData(_username.value.toString()))

                if(response.isSuccessful) {
                    _errorOccurred.value = false
                    _message.value = response.body()?.message?.replaceFirstChar(Char::uppercase).toString()
                } else {
                    _errorOccurred.value = true
                    _message.value = ValueParser.errorResponseToMessage(response)
                }
            } catch (e: IOException) {
                _errorOccurred.value = true
                _message.value = "Cannot resend verification code. Check your Internet connection and try again."
            } catch (e: Exception) {
                _errorOccurred.value = true
                _message.value = "Unknown error occurred."
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
            _status.value = ApiCallStatus.LOADING
            try {
                val response = RetrofitInstance.retrofitApi
                    .verifyCode(VerificationCodeRequestData(_username.value.toString(), code))
                Log.d("SignUpVerificationViewModel", "verify code response: $response")
                _status.value = ApiCallStatus.DONE

                if(response.isSuccessful) {
                    _errorOccurred.value = false
                    _message.value = ""
                    logInUser()
                } else {
                    _errorOccurred.value = true
                    _message.value = ValueParser.errorResponseToMessage(response)
                }
            } catch(e: IOException) {
                _errorOccurred.value = true
                _message.value = "Cannot send verification code. Check your Internet connection and try again."
            } catch (e: Exception) {
                _status.value = ApiCallStatus.ERROR
                _errorOccurred.value = true
                _message.value = "Unknown error occured."
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
                    _navController.value?.navigate(Graph.HOME) { popUpTo(Graph.AUTHENTICATION) { inclusive = true } }
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
//        if(sessionCache == null) return
        if(sessionCache.getActiveSession() != null) sessionCache.clearSession()
        sessionCache.saveSession(Session(userId))
        Log.d("AccountVerification", "Logged in user: $userId")
    }

    fun setData(navController: NavHostController, username: String?, password: String?) {
        _navController.value = navController
        _username.value = username
        _password.value = password
    }
}