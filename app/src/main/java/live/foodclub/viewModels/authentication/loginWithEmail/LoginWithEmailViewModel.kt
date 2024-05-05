package live.foodclub.viewModels.authentication.loginWithEmail

import live.foodclub.domain.models.session.RefreshToken
import live.foodclub.domain.models.session.Session
import live.foodclub.navigation.Graph
import live.foodclub.network.retrofit.utils.SessionCache
import live.foodclub.network.retrofit.utils.auth.RefreshTokenManager
import live.foodclub.repositories.AuthRepository
import live.foodclub.utils.helpers.Resource
import live.foodclub.views.authentication.loginWithEmail.LoginState
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
    const val UNKNOWN_ERROR = "An unexpected error occurred. Please try again later."
}

@HiltViewModel
class LogInWithEmailViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val sessionCache: SessionCache,
    private val refreshTokenManager: RefreshTokenManager
) : ViewModel(), LoginWithEmailEvents {

    private val _state = MutableStateFlow(LoginState.default())
    val state : StateFlow<LoginState>
    get() = _state

    private fun setSession(
        accessToken: String, idToken: String, refreshToken: String, username: String, userId: Long
    ) {
        if(sessionCache.getActiveSession() != null) sessionCache.clearSession()
        if(refreshTokenManager.getActiveToken() != null) refreshTokenManager.clearToken()
        sessionCache.saveSession(Session(accessToken, idToken, userId))
        refreshTokenManager.saveToken(RefreshToken(refreshToken, username))
    }


    override fun logInUser(userEmail: String?, userPassword: String?, navController: NavController) {
        if (userEmail.isNullOrEmpty() || userPassword.isNullOrEmpty()) {
            _state.update { it.copy(loginStatus = LoginErrorCodes.EMPTY_CREDENTIALS) }
            return
        }
        viewModelScope.launch {
            when (val resource = repository.signIn(userEmail, userPassword)) {
                is Resource.Success -> {
                    val data = resource.data!!
                    setSession(
                        data.accessToken, data.idToken, data.refreshToken, data.username,
                        data.id.toLong()
                    )
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

