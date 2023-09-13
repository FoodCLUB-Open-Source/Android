package android.kotlin.foodclub.viewmodels.authentication

import android.kotlin.foodclub.api.authentication.ErrorResponse
import android.kotlin.foodclub.api.authentication.UserCredentials
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.kotlin.foodclub.data.models.Session
import android.kotlin.foodclub.navigation.graphs.Graph
import android.kotlin.foodclub.utils.helpers.SessionCache
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

//object LoginErrorCodes {
//    const val EMPTY_CREDENTIALS = 1
//    const val CONNECTIVITY_ISSUES = 2
//    const val WRONG_CREDENTIALS = 3
//    const val ACCOUNT_NOT_FOUND = 4
//    const val PASSWORD_FORMAT= 5
//    const val USERNAME_FORMAT=6
//    const val UNKNOWN_ERROR = 7
//}
object LoginErrorCodes {
    const val EMPTY_CREDENTIALS = "Please enter both email and password"
    const val CONNECTIVITY_ISSUES = "Connectivity Issues. Please check your internet connection."
    const val WRONG_CREDENTIALS = "Wrong username or password"
    const val ACCOUNT_NOT_FOUND = "Account Not found"
    const val PASSWORD_FORMAT= "Password must have at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character"
    const val USERNAME_FORMAT="Username must only contain letters and numbers"
    const val UNKNOWN_ERROR = "An unexpected error occurred. Please try again later."
}

@HiltViewModel
class LogInWithEmailViewModel @Inject constructor(
    private val sessionCache: SessionCache
) : ViewModel() {
    private val gson = Gson()
    private val _loginStatus = MutableLiveData<String?>()
    val loginStatus: LiveData<String?> get() = _loginStatus

    private fun setSession(userId: Long) {
//        if(sessionCache == null) return
        if(sessionCache.getActiveSession() != null) sessionCache.clearSession()
        sessionCache.saveSession(Session(userId))
        Log.d("LoginWithEmailViewModel", "Logged in user: $userId")
    }


    fun logInUser(userEmail: String?, userPassword: String?, navController: NavController) {
        if (userEmail.isNullOrEmpty() || userPassword.isNullOrEmpty()) {
            _loginStatus.postValue(LoginErrorCodes.EMPTY_CREDENTIALS)
            return
        }
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.retrofitApi.loginUser(UserCredentials(userEmail, userPassword))
                if (response.isSuccessful) {
                    setSession(response.body()!!.user.id.toLong())
                    navController.navigate(Graph.HOME) { popUpTo(Graph.AUTHENTICATION) { inclusive = true } }
//                    _loginStatus.postValue("Login Successful")
                } else {
                    val errorString = response.errorBody()?.string()
                    Log.d("LoginWithEmailViewModel", "Raw error response: $errorString")
                    val errorResponse = gson.fromJson(errorString, ErrorResponse::class.java)
                    Log.d("errorResponse-Null", "Raw error response: $errorResponse")

                    if(errorResponse.errors==null){
                        when{
                            errorString?.contains("Incorrect username or password.") == true -> _loginStatus.postValue(LoginErrorCodes.WRONG_CREDENTIALS)
                            errorString?.contains("User is not confirmed.") == true ->  {
                                navController.navigate("VERIFY_SIGN_UP/${userEmail}?resendCode=${true}")
                            }
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

//        when (loginStatus) {
//            null -> {}
//            200 -> navController.navigate("home_graph")
//            LoginErrorCodes.EMPTY_CREDENTIALS -> errorMessage = "Please enter both email and password"
//            LoginErrorCodes.WRONG_CREDENTIALS -> errorMessage = "Wrong username or password"
//            LoginErrorCodes.ACCOUNT_NOT_FOUND -> errorMessage = "Account Not found"
//            LoginErrorCodes.PASSWORD_FORMAT -> errorMessage = "Password must have at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character"
//            LoginErrorCodes.CONNECTIVITY_ISSUES -> errorMessage = "Connectivity Issues. Please check your internet connection."
//            LoginErrorCodes.UNKNOWN_ERROR -> errorMessage = "An unexpected error occurred. Please try again later."
//            LoginErrorCodes.USERNAME_FORMAT -> errorMessage ="Username must only contain letters and numbers"
//            else -> errorMessage = "Error Code: $loginStatus" // Display other HTTP codes for further analysis or debugging
//        }
    }

    fun resetPassword() {
        // TODO: Implement
    }
}

