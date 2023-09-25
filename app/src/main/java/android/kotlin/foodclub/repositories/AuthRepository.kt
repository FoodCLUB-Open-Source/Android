package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.api.authentication.API
import android.kotlin.foodclub.api.authentication.UserCredentials
import android.kotlin.foodclub.api.responses.LoginUserData
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.utils.helpers.ValueParser
import java.io.IOException

class AuthRepository(
    private val api: API
) {
    suspend fun signIn(username: String, password: String): Resource<LoginUserData> {
        val response = try {
            api.loginUser(UserCredentials(username, password))
        } catch (e: IOException) {
            return Resource.Error("Cannot retrieve data. Check your internet connection and try again.")
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred.")
        }

        if(response.isSuccessful && response.body() != null && response.body()?.user != null){
            return Resource.Success(response.body()!!.user)
        }
        return Resource.Error(ValueParser.errorResponseToMessage(response))
    }
}