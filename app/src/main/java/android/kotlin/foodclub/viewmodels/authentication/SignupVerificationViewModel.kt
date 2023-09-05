package android.kotlin.foodclub.viewmodels.authentication

import android.kotlin.foodclub.api.authentication.SignUpResponseMessage
import android.kotlin.foodclub.api.authentication.VerificationCodeRequestData
import android.kotlin.foodclub.api.authentication.VerificationCodeResendData
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.kotlin.foodclub.utils.enums.ApiCallStatus
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.foodclub.navigation.graphs.AuthScreen
import com.example.foodclub.navigation.graphs.Graph
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

class SignupVerificationViewModel : ViewModel() {
    private val _status = MutableStateFlow<ApiCallStatus>(ApiCallStatus.DONE)
    val status: StateFlow<ApiCallStatus> get() = _status

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> get() = _message

    private val _errorOccurred = MutableStateFlow(false)
    val errorOccurred: StateFlow<Boolean> get() = _errorOccurred

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> get() = _username

    private val _navController = MutableStateFlow<NavHostController?>(null)
    val navController: StateFlow<NavHostController?> get() = _navController


    fun sendVerificationCode() {
        if(_username.value == null) {
            _errorOccurred.value = true
            _message.value = "Unknown error occurred. Try again later"
            _navController.value?.navigate(Graph.AUTHENTICATION)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.retrofitApi.resendCode(VerificationCodeResendData(_username.value.toString()))

                if(response.isSuccessful) {
                    _errorOccurred.value = false
                    _message.value = response.body()?.message?.replaceFirstChar(Char::uppercase).toString()
                } else {
                    _errorOccurred.value = true
                    checkError(response)
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
                    navController.value?.navigate(AuthScreen.Login.route)
                } else {
                    _errorOccurred.value = true
                    checkError(response)
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

    private fun checkError(response: Response<SignUpResponseMessage>) {
        if(response.errorBody() == null) {
            _message.value = "Unknown error occurred."
            return
        }
        val errorResponse = Gson().fromJson(response.errorBody()?.string(), SignUpResponseMessage::class.java)

        _message.value = errorResponse.message

        if(_message.value.isEmpty()){
            if(errorResponse.errors.isNotEmpty()) {
                _message.value = "Input data are invalid. Check mistakes and try again."
            } else {
                _message.value = "Unknown error occurred."
            }
        }
    }

    fun setData(navController: NavHostController, username: String?) {
        _navController.value = navController
        _username.value = username
    }
}