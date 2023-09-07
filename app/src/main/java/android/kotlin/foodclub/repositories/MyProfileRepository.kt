package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.kotlin.foodclub.data.models.MyProfileModel
import android.kotlin.foodclub.utils.helpers.Resource
import java.io.IOException

class MyProfileRepository {
    private val api = RetrofitInstance.retrofitApi

    suspend fun retrieveProfileData(userId: Long): Resource<MyProfileModel> {
        val response = try {
            api.retrieveMyProfileData(userId, 1, 5)
        } catch (e: IOException) {
            return Resource.Error("Cannot retrieve data. Check your internet connection and try again.")
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred.")
        }

        if(response.isSuccessful && response.body() != null && response.body()?.data != null){
            return Resource.Success(response.body()!!.data)
        }
        return Resource.Error("Unknown error occurred.")
    }
}