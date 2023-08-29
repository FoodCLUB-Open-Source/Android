package android.kotlin.foodclub.api.retrofit

import android.kotlin.foodclub.api.authentication.API
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://ec2-13-40-224-220.eu-west-2.compute.amazonaws.com:3000/"
//
//     lateinit var retrofit:Retrofit;
//
////    private val retrofit: Sign by lazy {
////        Retrofit.Builder()
////            .baseUrl(BASE_URL)
////            .addConverterFactory(GsonConverterFactory.create())
////            .build()
////    }
//
//    fun getRetrofitInstance():Retrofit{
//
//        if(retrofit == null){
//                retrofit = Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//
//        return retrofit;
//    }
//
//    fun getService(): SignUpUserService? {
//        return retrofit.create(SignUpUserService::class.java)
//    }
//
////    val signUpUserService: SignUpUserService by lazy {
////        retrofit.create(SignUpUserService::class.java)
////    }

    // retrofit instance object class

     val retrofitApi:API by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }

//    val api: API by lazy {
//        retrofit.create(API::class.java)
//    }

}