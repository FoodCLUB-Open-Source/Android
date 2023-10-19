package android.kotlin.foodclub.network.retrofit.utils

import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.utils.helpers.ValueParser
import retrofit2.Response
import java.io.IOException

suspend fun<T> apiRequestFlow(call: suspend () -> Response<T>): Resource<Response<T>> {
    val response = try {
        call()
    } catch (e: IOException) {
        return Resource.Error("Cannot retrieve data. Check your internet connection and try again.")
    } catch (e: Exception) {
        return Resource.Error("Unknown error occurred.")
    }

    if(response.isSuccessful && response.body() != null){
        return Resource.Success(response)
    }
    return Resource.Error(ValueParser.errorResponseToMessage(response), response)
}