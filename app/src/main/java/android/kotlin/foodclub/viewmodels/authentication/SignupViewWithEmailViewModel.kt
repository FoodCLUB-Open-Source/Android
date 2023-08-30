package com.example.foodclub.viewmodels.authentication

import android.kotlin.foodclub.api.authentication.UserSignUpInformation
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


     fun signUpUser(user:UserSignUpInformation){



         viewModelScope.launch(Dispatchers.IO) {
               val response = RetrofitInstance.retrofitApi.postUser("shubham1212",user.email,user.password)



             if(response.isSuccessful){

             }
             else{

             }
         }

     }

}
