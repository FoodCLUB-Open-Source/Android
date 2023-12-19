package android.kotlin.foodclub.viewModels.authentication.signupVerification

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
import android.kotlin.foodclub.views.authentication.signupVerification.SignupVerificationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupVerificationViewModel @Inject constructor(
    val repository: AuthRepository,
    private val sessionCache: SessionCache
) : ViewModel(), SignupVerificationEvents {

    companion object {
        private val TAG = SignupVerificationViewModel::class.java.simpleName
    }

    private val _state = MutableStateFlow(SignupVerificationState.default())
    val state: StateFlow<SignupVerificationState>
        get() = _state

    override fun sendVerificationCode(navController: NavHostController) {
        if (state.value.username == null) {
            _state.update {
                it.copy(
                    errorOccurred = true,
                    message = "Unknown error occurred. Try again later"
                )
            }
            navController.navigate(Graph.AUTHENTICATION)
        }

        viewModelScope.launch {
            when (
                val resource = repository.resendAccountVerificationCode(state.value.username!!)
            ) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            errorOccurred = false,
                            message = resource.data!!.message.replaceFirstChar(Char::uppercase)
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            errorOccurred = true,
                            message = resource.message!!
                        )
                    }
                }
            }
        }

    }

    override fun verifyCode(code: String, navController: NavHostController) {
        if (state.value.username == null) {
            _state.update {
                it.copy(
                    errorOccurred = true,
                    message = "Unknown error occurred. Try again later"
                )
            }
            navController.navigate(Graph.AUTHENTICATION)
            return
        }

        viewModelScope.launch {
            when (
                val resource = repository.verifyAccount(state.value.username!!, code)
            ) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            errorOccurred = false,
                            message = ""
                        )
                    }
                    logInUser(navController = navController)
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            errorOccurred = true,
                            message = resource.message!!
                        )
                    }
                }
            }
        }
    }

    override fun setData(
        navController: NavHostController,
        username: String?,
        password: String?
    ) {
        _state.update {
            it.copy(
                username = username,
                password = password
            )
        }
    }

    private fun logInUser(navController: NavHostController) {
        if (state.value.password == null) {
            navController.navigate(AuthScreen.Login.route)
        }

        viewModelScope.launch() {
            when (val resource =
                repository.signIn(state.value.username!!, state.value.password!!)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            errorOccurred = false,
                            message = ""
                        )
                    }

                    setSession(resource.data!!.id.toLong())
                    navController.navigate(Graph.HOME) {
                        popUpTo(Graph.AUTHENTICATION) { inclusive = true }
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            errorOccurred = true,
                            message = resource.message!!
                        )
                    }

                    navController.navigate(AuthScreen.Login.route)
                }
            }
        }
    }

    private fun setSession(userId: Long) {
        if (sessionCache.getActiveSession() != null) sessionCache.clearSession()
        sessionCache.saveSession(Session(JWTManager.createJWT(userId)!!))
        Log.d(TAG, "Logged in user: $userId")
    }

}