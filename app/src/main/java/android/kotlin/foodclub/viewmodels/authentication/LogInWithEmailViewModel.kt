package android.kotlin.foodclub.viewmodels.authentication

import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.kotlin.foodclub.api.retrofit.RetrofitInstance.retrofitApi
import androidx.camera.core.ImageProcessor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


class LogInWithEmailViewModel :ViewModel(){

    interface API {
        @POST("login/signin")

        suspend fun loginUser(
            @Body credentials: UserCredentials
        ): Response<LoginResponse>

    }
    data class UserCredentials(
        val email: String,
        val password: String
    )
//for the LoginResponse, maybe can be replaced by the code added in LogInWithEmail
    data class LoginResponse(
        val token: String? // or other fields you expect in the response
    )

    private val _loginStatus = MutableLiveData<String>()
    val loginStatus: LiveData<String> get() = _loginStatus

    //
    fun logInUser(userEmail:String,userPassword:String){
        viewModelScope.launch {
            try{
                val response = RetrofitInstance.retrofitApi1.loginUser(UserCredentials(userEmail, userPassword))

                if(response.isSuccessful){
                    val loginResponse = response.body()

                    if(loginResponse?.token!=null){
                        _loginStatus.value= "Login Successful"
                    }else{
                        _loginStatus.value = "Login Failed: Invalid Token"
                    }
                }else{
                    _loginStatus.value="Login Failed: ${response.message()}"
                }

            } catch(e : Exception){
                _loginStatus.value = "Login Error: ${e.message}"
            }
        }
    }

    fun resetPassword(){

    }

}

