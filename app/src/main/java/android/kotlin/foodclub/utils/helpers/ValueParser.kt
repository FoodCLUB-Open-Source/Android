package android.kotlin.foodclub.utils.helpers

import android.kotlin.foodclub.api.authentication.SignUpResponseMessage
import android.kotlin.foodclub.api.responses.DefaultErrorResponse
import android.util.Log
import com.google.gson.Gson
import retrofit2.Response

class ValueParser {
    companion object {
        fun numberToThousands(number: Long): String {
            if(number < 999) {
                return number.toString()
            }
            return String.format("%.1f",number.toDouble()/1000) + "K"
        }

        fun <T : Any> errorResponseToMessage(response: Response<T>): String {
            if(response.errorBody() == null) return "Unknown error occurred."

            val errorResponse = Gson().fromJson(response.errorBody()?.string(), DefaultErrorResponse::class.java)

            return if(errorResponse.errors.isNotEmpty()) {
                "Input data are invalid. Check mistakes and try again."
            } else if(errorResponse.message.isNotEmpty()) {
                errorResponse.message
            } else {
                "Unknown error occurred."
            }
        }
    }
}