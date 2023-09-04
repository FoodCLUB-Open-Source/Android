package android.kotlin.foodclub.viewmodels.authentication

import android.kotlin.foodclub.api.authentication.SignUpResponseMessage
import android.kotlin.foodclub.api.authentication.UserSignUpInformation
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.kotlin.foodclub.utils.enums.ApiCallStatus
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.foodclub.navigation.graphs.AuthScreen
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class SignupWithEmailViewModel() : ViewModel() {
    private val _status = MutableStateFlow<ApiCallStatus>(ApiCallStatus.DONE)
    val status: StateFlow<ApiCallStatus> get() = _status

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> get() = _error

    private val _userSignUpInformation = MutableStateFlow(UserSignUpInformation("", "", ""))
    val userSignUpInformation: StateFlow<UserSignUpInformation> get() = _userSignUpInformation

    fun saveEmailPasswordData(email: String, password: String) {
        _userSignUpInformation.value = UserSignUpInformation(_userSignUpInformation.value.username, email, password)
    }

    fun saveUsername(username: String) {
        _userSignUpInformation.value = UserSignUpInformation(
            username, _userSignUpInformation.value.email, _userSignUpInformation.value.password
        )
    }

     fun signUpUser(navController: NavHostController){
         viewModelScope.launch(Dispatchers.Main) {
             try {
                 val response = RetrofitInstance.retrofitApi.postUser(_userSignUpInformation.value)

                 if(response.isSuccessful){
                     navController.navigate(route = AuthScreen.VerifySignup.route + "/" + _userSignUpInformation.value.username) {
                         popUpTo(AuthScreen.SignUp.route) {
                             inclusive = true
                         }
                     }
                 } else {
                     errorMessage(response)
                 }
             } catch(e: IOException) {
                 _error.value = "Cannot connect to the server. Check your Internet connection."
             } catch (e: Exception) {
                 _error.value = "Unknown error occurred."
             }
         }

     }

    private fun errorMessage(response: Response<SignUpResponseMessage>) {
        if(response.errorBody() == null) return
        val errorResponse = Gson().fromJson(response.errorBody()?.string(), SignUpResponseMessage::class.java)

        _error.value = errorResponse.message

        if(_error.value.isEmpty()) {
            if(errorResponse.errors.isNotEmpty()) {
                _error.value = "Input data are invalid. Check mistakes and try again."
            } else {
                _error.value = "Unknown error occurred."
            }
        }
    }

}
