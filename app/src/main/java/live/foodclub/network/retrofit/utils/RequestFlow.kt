package live.foodclub.network.retrofit.utils

import android.util.Log
import live.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import live.foodclub.utils.helpers.Resource
import live.foodclub.utils.helpers.ValueParser
import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException

inline fun<T, reified E> apiRequestFlow(call: () -> Response<T>): Resource<Response<T>, E> {
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

    val message = if(E::class.java == DefaultErrorResponse::class.java) {
        ValueParser.errorResponseToMessage(response)
    } else {
        ""
    }

    return Resource.Error(message, Gson().fromJson(response.errorBody()?.string(), E::class.java))
}