package com.example.foodclub.viewmodels.authentication

import android.kotlin.foodclub.api.authentication.UserSignUpInformation
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.kotlin.foodclub.utils.enums.ApiCallStatus
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.foodclub.navigation.graphs.AuthScreen
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignupViewWithEmailViewModel() : ViewModel() {
    private val _status = MutableLiveData<ApiCallStatus>()
    val status: LiveData<ApiCallStatus> get() = _status

     fun signUpUser(user:UserSignUpInformation, navController: NavHostController){
         viewModelScope.launch(Dispatchers.Main) {
             try {

                 val response = RetrofitInstance.retrofitApi.postUser(user)
                 Log.d("SignupWithEmailViewModel", "Response header: ${response.headers()}")
                 Log.d("SignupWithEmailViewModel", "Response body: ${response.raw()}")
                 Log.d("SignupWithEmailViewModel", "Response code: ${response.code()}")

                 if(response.isSuccessful){
                     navController.navigate(route = AuthScreen.VerifySignup.route + "/" + user.username)
                 }
             } catch(e: Exception) {
                 Log.d("SignupWithEmailViewModel", "Couldn't send signup request")
                 e.printStackTrace()
             }
         }

     }

}
