package android.kotlin.foodclub.viewmodels.authentication

import android.kotlin.foodclub.api.authentication.VerificationCodeRequestData
import android.kotlin.foodclub.api.authentication.VerificationCodeResendData
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.kotlin.foodclub.utils.enums.ApiCallStatus
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.foodclub.navigation.graphs.AuthScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.concurrent.TimeUnit

class SignupVerificationViewModel : ViewModel() {
    private val _status = MutableLiveData<ApiCallStatus>()
    val status: LiveData<ApiCallStatus> get() = _status

    private val _message = MutableStateFlow("We’ve sent an SMS with an activation code to your phone +44 7503759410")
    val message: StateFlow<String> get() = _message


    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _navController = MutableLiveData<NavHostController>()
    val navController: LiveData<NavHostController> get() = _navController


    fun sendVerificationCode() {
        if(_username.value == null) {
            _message.value = "Unknown error occurred. Try again later"
            //Here it would be best to take user to previous (signup) view
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.retrofitApi.resendCode(VerificationCodeResendData(_username.value.toString()))
                if (response.body() != null) {
                    _message.value = response.body()!!.message
                }
                Log.d("SignUpVerificationViewModel", "verify code header: ${response.headers()}")
                Log.d("SignUpVerificationViewModel", "verify code response: ${response.raw()}")
                Log.d("SignUpVerificationViewModel", "verify code status: ${response.code()}")
            } catch (e: Exception) {
                _message.value = "Cannot resend verification code. Check your Internet connection and try again."
            }
        }

    }

    fun verifyCode(code: String) {
        if(_username.value == null) {
            _message.value = "Unknown error occurred. Try again later"
            //Here it would be best to take user to previous (signup) view
            return
        }

        viewModelScope.launch {
            _status.value = ApiCallStatus.LOADING
            try {
                val response = RetrofitInstance.retrofitApi
                    .verifyCode(VerificationCodeRequestData(_username.value.toString(), code))
                _message.value = response.body()?.message ?: ""
                Log.d("SignUpVerificationViewModel", "verify code response: $response")
                _status.value = ApiCallStatus.DONE

                if(response.isSuccessful) {
                    navController.value?.navigate(AuthScreen.Login.route)
                }
            } catch (e: Exception) {
                _status.value = ApiCallStatus.ERROR
                _message.value = "Unknown error occured. Check your Internet connection and try again."
            }
        }
    }

    fun setData(navController: NavHostController, username: String) {
        _navController.value = navController
        _username.value = username
    }
}