package android.kotlin.foodclub.utils.helpers

import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.utils.enums.QuantityUnit
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

        fun quantityUnitToString(quantityUnit: QuantityUnit): String {
            return when(quantityUnit) {
                QuantityUnit.GRAMS -> "g"
                QuantityUnit.KILOGRAMS -> "kg"
                QuantityUnit.MILLILITERS -> "ml"
                QuantityUnit.LITERS -> "l"
            }
        }

        fun quantityStringToInt(quantityString: String, quantityUnit: QuantityUnit): Int {
            return Integer.valueOf(
                when(quantityUnit) {
                    QuantityUnit.GRAMS -> quantityString.substring(0, quantityString.length - 1)
                    QuantityUnit.KILOGRAMS -> quantityString.substring(0, quantityString.length - 2)
                    QuantityUnit.MILLILITERS -> quantityString.substring(0, quantityString.length - 2)
                    QuantityUnit.LITERS -> quantityString.substring(0, quantityString.length - 1)
                }
            )

        }

        fun <T> errorResponseToMessage(response: Response<T>): String {
            if(response.errorBody() == null) return "Unknown error occurred."


            val errorResponse = Gson().fromJson(response.errorBody()?.string(), DefaultErrorResponse::class.java)

            return if(!errorResponse.message.isNullOrEmpty()) {
                errorResponse.message
            } else if(errorResponse.errors.isNotEmpty()) {
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