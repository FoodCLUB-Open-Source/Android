package android.kotlin.foodclub.viewmodels.authentication

import android.kotlin.foodclub.api.authentication.ErrorResponse
import android.kotlin.foodclub.api.authentication.UserCredentials
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.UnknownHostException

object LoginErrorCodes {
    const val EMPTY_CREDENTIALS = -2
    const val CONNECTIVITY_ISSUES = -1
    const val WRONG_CREDENTIALS = -4
    const val ACCOUNT_NOT_FOUND = -5
    const val PASSWORD_FORMAT= -6
    const val USERNAME_FORMAT=-7
    const val UNKNOWN_ERROR = -3
}


class LogInWithEmailViewModel : ViewModel() {
    private val gson = Gson()
    private val _loginStatus = MutableLiveData<Int?>()
    val loginStatus: LiveData<Int?> get() = _loginStatus

    fun logInUser(userEmail: String?, userPassword: String?) {
        if (userEmail.isNullOrEmpty() || userPassword.isNullOrEmpty()) {
            _loginStatus.postValue(LoginErrorCodes.EMPTY_CREDENTIALS)
            return
        }
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.retrofitApi.loginUser(UserCredentials(userEmail, userPassword))
                if (response.isSuccessful) {
                    _loginStatus.postValue(200)
                } else {
                    val errorString = response.errorBody()?.string()
                    Log.d("LoginWithEmailViewModel", "Raw error response: $errorString")
                    val errorResponse = gson.fromJson(errorString, ErrorResponse::class.java)
                    Log.d("errorResponse-Null", "Raw error response: $errorResponse")

                    if(errorResponse.errors==null){
                        when{
                            errorString?.contains("Incorrect username or password.") == true -> _loginStatus.postValue(LoginErrorCodes.WRONG_CREDENTIALS)
                            errorString?.contains("account_not_found") == true-> _loginStatus.postValue(LoginErrorCodes.ACCOUNT_NOT_FOUND)
                            else -> _loginStatus.postValue(LoginErrorCodes.UNKNOWN_ERROR)
                        }

                    }else {
                        loop@ for (errorMap in errorResponse.errors) {
                            for ((key, value) in errorMap) {
                                if (key == "msg") {
                                    Log.d("Value", "Raw error response: $value")
                                    when (value) {
                                        "Password must be at least 8 characters long" -> _loginStatus.postValue(LoginErrorCodes.PASSWORD_FORMAT)
                                        "Password must have at least one uppercase letter, one lowercase letter, one number, and one special character" -> _loginStatus.postValue(LoginErrorCodes.PASSWORD_FORMAT)
                                        "Username must only contain letters and numbers" -> _loginStatus.postValue(LoginErrorCodes.USERNAME_FORMAT)
                                        else -> _loginStatus.postValue(LoginErrorCodes.UNKNOWN_ERROR)
                                    }
                                    break@loop
                                }
                            }
                        }
                    }

                }
            } catch (e: UnknownHostException) {
                _loginStatus.postValue(LoginErrorCodes.CONNECTIVITY_ISSUES)
            } catch (e: Exception) {
                _loginStatus.postValue(LoginErrorCodes.UNKNOWN_ERROR)
            }
        }
    }

    fun resetPassword() {
        // TODO: Implement
    }
}

