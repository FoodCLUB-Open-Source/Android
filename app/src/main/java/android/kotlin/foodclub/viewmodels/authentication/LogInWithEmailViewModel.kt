package android.kotlin.foodclub.viewmodels.authentication

import android.kotlin.foodclub.api.retrofit.RetrofitInstance.retrofitApi
import androidx.camera.core.ImageProcessor
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LogInWithEmailViewModel :ViewModel(){

    interface AuthApi {
        @POST("login/signin")

        suspend fun loginUser(
            @Body credentials: UserCredentials
        ): Response<LoginResponse>

    }

    data class UserCredentials(
        val email: String,
        val password: String
    )

    data class LoginResponse(
        val token: String? // or other fields you expect in the response
    )

    fun logInUser(userEmail:String,userPassword:String){
        viewModelScope.launch {
            try{
                val response = retrofitApi.loginUser(UserCredentials(userEmail, userPassword))
            } catch(e : Exception){

            }
        }
    }

    fun resetPassword(){

    }

}

