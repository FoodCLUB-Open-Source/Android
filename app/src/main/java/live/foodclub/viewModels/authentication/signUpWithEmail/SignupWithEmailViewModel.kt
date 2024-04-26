package live.foodclub.viewModels.authentication.signUpWithEmail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import live.foodclub.navigation.auth.AuthScreen
import live.foodclub.repositories.AuthRepository
import live.foodclub.utils.helpers.Resource
import live.foodclub.views.authentication.signup.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupWithEmailViewModel @Inject constructor(
    val repository: AuthRepository
) : ViewModel(), SignUpEmailEvents {

    companion object {
        private val TAG = SignupWithEmailViewModel::class.java.simpleName
    }

    private val _state = MutableStateFlow(SignUpState.default())
    val state: StateFlow<SignUpState>
        get() = _state

    override fun saveEmailPasswordData(email: String, password: String) {

        _state.update { it.copy(
            userSignUpInformation = it.userSignUpInformation.copy(
                email = email,
                password = password
            ),
        ) }
    }

    override fun saveRepeatedEmail(repeatedEmail: String) {
        _state.update { it.copy(
            repeatedEmail = repeatedEmail
        ) }
    }

    override fun saveUsername(username: String) {
        _state.update { it.copy(
            userSignUpInformation = it.userSignUpInformation.copy(
                username = username
            ),
        ) }
    }

    override fun saveFullName(name: String) {
        _state.update { it.copy(
            userSignUpInformation = it.userSignUpInformation.copy(
                name = name
            ),
        ) }
    }

     override fun signUpUser(navController: NavHostController) {
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
