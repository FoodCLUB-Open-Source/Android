package android.kotlin.foodclub.api.retrofit

import android.kotlin.foodclub.api.authentication.API
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://ec2-35-177-0-50.eu-west-2.compute.amazonaws.com:3000/api/v1/"


     val retrofitApi:API by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }



}