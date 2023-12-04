package android.kotlin.foodclub.viewModels.authentication

import android.kotlin.foodclub.domain.models.auth.SignUpUser
import android.kotlin.foodclub.domain.enums.ApiCallStatus
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import android.kotlin.foodclub.navigation.auth.AuthScreen
import android.kotlin.foodclub.repositories.AuthRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.views.authentication.signup.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupWithEmailViewModel @Inject constructor(
    val repository: AuthRepository
) : ViewModel() {

    companion object {
        private val TAG = SignupWithEmailViewModel::class.java.simpleName
    }

    private val _state = MutableStateFlow(SignUpState.default())
    val state: StateFlow<SignUpState>
        get() = _state

    fun saveEmailPasswordData(email: String, password: String) {

        _state.update { it.copy(
            userSignUpInformation = it.userSignUpInformation.copy(
                email = email,
                password = password
            ),
        ) }
    }

    fun saveRepeatedEmail(repeatedEmail: String) {
        _state.update { it.copy(
            repeatedEmail = repeatedEmail
        ) }
    }

    fun saveUsername(username: String) {
        _state.update { it.copy(
            userSignUpInformation = it.userSignUpInformation.copy(
                username = username
            ),
        ) }
    }

    fun saveFullName(name: String) {
        _state.update { it.copy(
            userSignUpInformation = it.userSignUpInformation.copy(
                name = name
            ),
        ) }
    }

     fun signUpUser(navController: NavHostController) {
         viewModelScope.launch {
             when (
                 val resource = repository.signUp(state.value.userSignUpInformation)
             ) {
                 is Resource.Success -> {
                     navController.navigate(
                         route = AuthScreen.VerifySignup.route
                                 + "/${state.value.userSignUpInformation.username}"
                                 + "?password=${state.value.userSignUpInformation.password}&email="
                                 + state.value.userSignUpInformation.email
                     ) {
                         popUpTo(AuthScreen.SignUp.route) {
                             inclusive = true
                         }
                     }
                 }

                 is Resource.Error -> {
                        _state.update { it.copy(
                            error = resource.message!!
                        ) }
                 }
             }
         }
     }

}
