package android.kotlin.foodclub.viewModels.authentication.loginWithEmail

import android.kotlin.foodclub.domain.models.session.Session
import android.kotlin.foodclub.navigation.Graph
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.kotlin.foodclub.network.retrofit.utils.auth.JWTManager
import android.kotlin.foodclub.repositories.AuthRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.views.authentication.loginWithEmail.LoginState
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

object LoginErrorCodes {
    const val EMPTY_CREDENTIALS = "Please enter both email and password"
    const val WRONG_CREDENTIALS = "Wrong username or password"
    const val ACCOUNT_NOT_FOUND = "Account Not found"
    const val PASSWORD_FORMAT= "Password must have at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character"
    const val USERNAME_FORMAT="Username must only contain letters and numbers"
    const val UNKNOWN_ERROR = "An unexpected error occurred. Please try again later."
}

@HiltViewModel
class LogInWithEmailViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val sessionCache: SessionCache
) : ViewModel(), LoginWithEmailEvents {

    private val _state = MutableStateFlow(LoginState.default())
    val state : StateFlow<LoginState>
    get() = _state

    private fun setSession(userId: Long) {
        if(sessionCache.getActiveSession() != null) sessionCache.clearSession()
        sessionCache.saveSession(Session(JWTManager.createJWT(userId)!!))
        Log.d("LoginWithEmailViewModel", "Logged in user: $userId")
    }


    override fun logInUser(userEmail: String?, userPassword: String?, navController: NavController) {
        if (userEmail.isNullOrEmpty() || userPassword.isNullOrEmpty()) {
            _state.update { it.copy(loginStatus = LoginErrorCodes.EMPTY_CREDENTIALS) }
            return
        }
        viewModelScope.launch {
            when (val resource = repository.signIn(userEmail, userPassword)) {
                is Resource.Success -> {
                    setSession(resource.data!!.id.toLong())
                    navController.navigate(Graph.HOME) {
                        popUpTo(Graph.AUTHENTICATION) { inclusive = true }
                    }
                }

                is Resource.Error -> {
                    val errors = resource.errorData?.errors
                    if (errors.isNullOrEmpty()) {
                        val message = resource.message
                        when {
                            message?.contains("Incorrect username or password.") == true -> {
                                _state.update { it.copy(loginStatus = LoginErrorCodes.WRONG_CREDENTIALS) }
                            }

                            message?.contains("User is not confirmed.") == true -> {
                                navController.navigate(
                                    "VERIFY_SIGN_UP/${userEmail}?password=$userPassword"
                                )
                            }

                            message?.contains("account_not_found") == true -> {
                                _state.update { it.copy(loginStatus = LoginErrorCodes.ACCOUNT_NOT_FOUND) }
                            }

                            else -> _state.update { it.copy(loginStatus = LoginErrorCodes.UNKNOWN_ERROR) }
                        }
                    } else {
                        for (error in errors) {
                            when (error.message) {
                                "Password must be at least 8 characters long" -> {
                                    _state.update { it.copy(loginStatus = LoginErrorCodes.PASSWORD_FORMAT) }
                                }

                                "Password must have at least one uppercase letter, one lowercase letter, one number, and one special character" -> {
                                    _state.update { it.copy(loginStatus = LoginErrorCodes.PASSWORD_FORMAT) }
                                }

                                "Username must only contain letters and numbers" -> {
                                    _state.update { it.copy(loginStatus = LoginErrorCodes.PASSWORD_FORMAT) }
                                }

                                else -> _state.update { it.copy(loginStatus = LoginErrorCodes.UNKNOWN_ERROR) }
                            }
                        }
                    }

                }
            }
        }
    }
}
