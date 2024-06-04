package live.foodclub.presentation.ui.authentication.signupVerification

import live.foodclub.domain.models.session.RefreshToken
import live.foodclub.domain.models.session.Session
import live.foodclub.localdatasource.room.entity.ProfileEntity
import live.foodclub.presentation.navigation.Graph
import live.foodclub.presentation.navigation.auth.AuthScreen
import live.foodclub.network.retrofit.utils.SessionCache
import live.foodclub.network.retrofit.utils.auth.RefreshTokenManager
import live.foodclub.repositories.AuthRepository
import live.foodclub.repositories.ProfileRepository
import live.foodclub.utils.helpers.Resource
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupVerificationViewModel @Inject constructor(
    val repository: AuthRepository,
    private val sessionCache: SessionCache,
    private val tokenManager: RefreshTokenManager,
    private val profileRepository: ProfileRepository
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
        if (state.value.password == null) {
            navController.navigate(AuthScreen.Login.route)
        }

        viewModelScope.launch {
            when (
                val resource = repository.verifyAccount(
                    state.value.username!!, state.value.password!!, code
                )
            ) {
                is Resource.Success -> {
                    val data = resource.data!!
                    _state.update {
                        it.copy(
                            errorOccurred = false,
                            message = ""
                        )
                    }
                    setSession(
                        data.accessToken, data.idToken, data.refreshToken, data.user.id.toLong()
                    )
                    profileRepository.saveLocalProfileDetails(
                        ProfileEntity(
                            userId = data.user.id.toLong(),
                            userName = state.value.username!!,
                            profilePicture = data.user.profileImageUrl,
                            fullName = data.user.fullName ?: "Undefined"
                        )
                    )
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

//    private fun logInUser(navController: NavHostController) {
//        if (state.value.password == null) {
//            navController.navigate(AuthScreen.Login.route)
//        }
//
//        viewModelScope.launch {
//            when (val resource =
//                repository.signIn(state.value.username!!, state.value.password!!)) {
//                is Resource.Success -> {
//                    _state.update {
//                        it.copy(
//                            errorOccurred = false,
//                            message = ""
//                        )
//                    }
//
//                    setSession(resource.data!!.id.toLong())
//                    navController.navigate(Graph.HOME) {
//                        popUpTo(Graph.AUTHENTICATION) { inclusive = true }
//                    }
//                }
//
//                is Resource.Error -> {
//                    _state.update {
//                        it.copy(
//                            errorOccurred = true,
//                            message = resource.message!!
//                        )
//                    }
//
//                    navController.navigate(AuthScreen.Login.route)
//                }
//            }
//        }
//    }

    private fun setSession(
        accessToken: String, idToken: String, refreshToken: String, userId: Long
    ) {
        if (sessionCache.getActiveSession() != null) sessionCache.clearSession()
        if (tokenManager.getActiveToken() != null) tokenManager.clearToken()
        tokenManager.saveToken(RefreshToken(refreshToken, _state.value.username!!))
        sessionCache.saveSession(Session(accessToken, idToken, userId))
        Log.d(TAG, "Logged in user: ${_state.value.username}")
    }

}