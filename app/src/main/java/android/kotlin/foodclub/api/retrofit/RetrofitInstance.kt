package android.kotlin.foodclub.api.retrofit

import android.kotlin.foodclub.api.authentication.API
import android.kotlin.foodclub.api.authentication.UserSignUpInformation
import android.kotlin.foodclub.viewmodels.authentication.LogInWithEmailViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://ec2-13-40-224-220.eu-west-2.compute.amazonaws.com:3000/api/v1/"


     val retrofitApi : API by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }

//    val retrofitApi1 : LogInWithEmailViewModel.API by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(LogInWithEmailViewModel.API::class.java)
//    }

}