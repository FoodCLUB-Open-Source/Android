package android.kotlin.foodclub.viewmodels.authentication

import android.kotlin.foodclub.api.authentication.UserCredentials
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.kotlin.foodclub.api.retrofit.RetrofitInstance.retrofitApi
import android.util.Log
import androidx.camera.core.ImageProcessor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.IOException
import java.net.UnknownHostException


class LogInWithEmailViewModel : ViewModel() {

    private val _loginStatus = MutableLiveData<Int?>()
    val loginStatus: LiveData<Int?> get() = _loginStatus

    fun logInUser(userEmail: String, userPassword: String) {
        if (userEmail.isBlank() || userPassword.isBlank()) {
            _loginStatus.postValue(-2) // -2 for null or empty credentials
            return
        }

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.retrofitApi.loginUser(UserCredentials(userEmail, userPassword))
                Log.d("LoginViewModel", "Response code: ${response.code()}")

                if (response.isSuccessful) {
                    _loginStatus.postValue(200)
                } else {
                    _loginStatus.postValue(response.code()) // This will post the HTTP error code
                }
            } catch (e: UnknownHostException) {
                Log.e("LoginViewModel", "No internet connection or server is down.", e)
                _loginStatus.postValue(-1) // -1 for connectivity issues
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Unexpected error.", e)
                _loginStatus.postValue(-3) // -3 for general unknown error
            }
        }
    }

    fun resetPassword() {
        // TODO: Implement
    }
}
