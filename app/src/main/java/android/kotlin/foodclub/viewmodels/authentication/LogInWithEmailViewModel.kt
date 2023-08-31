package android.kotlin.foodclub.viewmodels.authentication

import android.kotlin.foodclub.api.authentication.UserCredentials
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


//for the LoginResponse, maybe can be replaced by the code added in LogInWithEmail


    private val _loginStatus = MutableLiveData<String>()
    val loginStatus: LiveData<String> get() = _loginStatus

    //
    fun logInUser(userEmail:String,userPassword:String): Int {
        var status: Int = 0;

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.retrofitApi.loginUser(UserCredentials(userEmail, userPassword))

                if (response.isSuccessful) {
                    status = 200;
                } else {
                    if (response == null) {
                        status = 404;
                    }
                }
            } catch(e: Exception){

            }
        }
        return status;
    }

    fun resetPassword(){

    }

}

