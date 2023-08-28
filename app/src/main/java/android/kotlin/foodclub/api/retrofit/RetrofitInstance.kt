package android.kotlin.foodclub.api.retrofit

import android.kotlin.foodclub.api.authentication.SignUpUserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://ec2-13-40-224-220.eu-west-2.compute.amazonaws.com:3000/api/v1/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val signUpUserService: SignUpUserService by lazy {
        retrofit.create(SignUpUserService::class.java)
    }
}