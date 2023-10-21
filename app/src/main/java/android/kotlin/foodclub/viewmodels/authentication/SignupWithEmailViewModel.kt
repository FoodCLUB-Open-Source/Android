package android.kotlin.foodclub.viewmodels.authentication

import android.kotlin.foodclub.domain.models.auth.SignUpUser
import android.kotlin.foodclub.utils.enums.ApiCallStatus
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import android.kotlin.foodclub.navigation.graphs.AuthScreen
import android.kotlin.foodclub.repositories.AuthRepository
import android.kotlin.foodclub.utils.helpers.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupWithEmailViewModel @Inject constructor(
    val repository: AuthRepository
) : ViewModel() {
    private val _status = MutableStateFlow(ApiCallStatus.DONE)
    val status: StateFlow<ApiCallStatus> get() = _status

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> get() = _error

    private val _userSignUpInformation = MutableStateFlow(
        SignUpUser("", "", "", "")
    )
    val userSignUpInformation: StateFlow<SignUpUser> get() = _userSignUpInformation

    private val _repeatedEmail = MutableStateFlow("")
    val repeatedEmail: StateFlow<String> get() = _repeatedEmail

    fun saveEmailPasswordData(email: String, password: String) {
        _userSignUpInformation.value = SignUpUser(_userSignUpInformation.value.username,
            email, password, _userSignUpInformation.value.name)
    }

    fun saveRepeatedEmail(repeatedEmail: String) {
        _repeatedEmail.value = repeatedEmail
    }

    fun saveUsername(username: String) {
        _userSignUpInformation.value = SignUpUser(
            username, _userSignUpInformation.value.email, _userSignUpInformation.value.password,
            _userSignUpInformation.value.name
        )
    }

    fun saveFullName(name: String) {
        _userSignUpInformation.value = SignUpUser(
            _userSignUpInformation.value.username, _userSignUpInformation.value.email,
            _userSignUpInformation.value.password, name
        )
    }

     fun signUpUser(navController: NavHostController) {
         viewModelScope.launch {
             when (
                 val resource = repository.signUp(_userSignUpInformation.value)
             ) {
                 is Resource.Success -> {
                     navController.navigate(
                         route = AuthScreen.VerifySignup.route
                                 + "/${_userSignUpInformation.value.username}"
                                 + "?password=${_userSignUpInformation.value.password}&email="
                                 + _userSignUpInformation.value.email
                     ) {
                         popUpTo(AuthScreen.SignUp.route) {
                             inclusive = true
                         }
                     }
                 }

                 is Resource.Error -> {
                     _error.value = resource.message!!
                 }
             }
         }
     }

}
