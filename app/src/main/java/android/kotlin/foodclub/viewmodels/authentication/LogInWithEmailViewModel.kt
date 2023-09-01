package android.kotlin.foodclub.viewmodels.authentication

import android.kotlin.foodclub.api.authentication.UserCredentials
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.UnknownHostException


object LoginErrorCodes {
    const val EMPTY_CREDENTIALS = -2
    const val CONNECTIVITY_ISSUES = -1
    const val WRONG_CREDENTIALS = -4
    const val ACCOUNT_NOT_FOUND = -5
    const val UNKNOWN_ERROR = -3
}

class LogInWithEmailViewModel : ViewModel() {

    private val _loginStatus = MutableLiveData<Int?>()
    val loginStatus: LiveData<Int?> get() = _loginStatus

    fun logInUser(userEmail: String?, userPassword: String?) {
        when {
            userEmail == null || userPassword == null ||
                    userEmail.isBlank() || userPassword.isBlank() -> {
                _loginStatus.postValue(LoginErrorCodes.EMPTY_CREDENTIALS)
                return
            }
            else -> {
                viewModelScope.launch {
                    try {
                        val response = RetrofitInstance.retrofitApi.loginUser(
                            UserCredentials(userEmail, userPassword)
                        )

                        when {
                            response.isSuccessful -> _loginStatus.postValue(200)
                            response.code() == 401 -> _loginStatus.postValue(LoginErrorCodes.WRONG_CREDENTIALS)
                            response.code() == 404 -> _loginStatus.postValue(LoginErrorCodes.ACCOUNT_NOT_FOUND)
                            else -> _loginStatus.postValue(response.code()) // Still post other HTTP error codes for further handling
                        }
                    } catch (e: UnknownHostException) {
                        _loginStatus.postValue(LoginErrorCodes.CONNECTIVITY_ISSUES)
                    } catch (e: Exception) {
                        _loginStatus.postValue(LoginErrorCodes.UNKNOWN_ERROR)
                    }
                }
            }
        }
    }

    fun resetPassword() {
        // TODO: Implement
    }
}
