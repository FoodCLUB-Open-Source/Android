package android.kotlin.foodclub.utils.helpers

import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.domain.enums.QuantityUnit
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

        fun quantityStringToInt(quantityString: String, quantityUnit: QuantityUnit): Int {
            return Integer.valueOf(
                quantityString.substring(0, quantityString.length - quantityUnit.short.length)
            )

        }

        fun <T> errorResponseToMessage(response: Response<T>): String {
            if(response.errorBody() == null) return "Unknown error occurred."


            val errorResponse = try {
                Gson().fromJson(response.errorBody()?.string(), DefaultErrorResponse::class.java)
            } catch (e: Exception) {
                DefaultErrorResponse(message = "${response.code()} ${response.message()}")
            }

            return if(!errorResponse.message.isNullOrEmpty()) {
                errorResponse.message
            } else if(!errorResponse.errors.isNullOrEmpty()) {
                "Input data are invalid. Check mistakes and try again."
            } else {
                "Unknown error occurred."
            }
        }

        fun extractQueriesFromUri(uri: String): Map<String, String> {
            val queriesMap = mutableMapOf<String, String>()
            if(uri.isBlank()) return queriesMap

            val baseAndQueriesList = uri.split("?")
            if(baseAndQueriesList.size < 2) return queriesMap

            val queriesString = baseAndQueriesList[1]
            queriesString.split("&").forEach {
                val queryList = it.split("=")
                if (queryList.size > 1) queriesMap[queryList[0]] = queryList[1]
            }
            return queriesMap
        }
    }
}