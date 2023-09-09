package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.api.responses.FollowUnfollowResponse
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.kotlin.foodclub.data.models.FollowerUserModel
import android.kotlin.foodclub.data.models.FollowingUserModel
import android.kotlin.foodclub.data.models.UserProfileModel
import android.kotlin.foodclub.utils.helpers.Resource
import java.io.IOException

class ProfileRepository {
    private val api = RetrofitInstance.retrofitApi

    suspend fun retrieveProfileData(userId: Long): Resource<UserProfileModel> {
        val response = try {
            api.retrieveProfileData(userId, null, null)
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

    suspend fun retrieveProfileFollowers(userId: Long): Resource<List<FollowerUserModel>> {
        val response = try {
            api.retrieveProfileFollowers(userId, null, null)
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

    suspend fun retrieveProfileFollowing(userId: Long): Resource<List<FollowingUserModel>> {
        val response = try {
            api.retrieveProfileFollowing(userId, null, null)
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

    suspend fun followUser(followerId: Long, userId: Long): Resource<FollowUnfollowResponse> {
        val response = try {
            api.followUser(followerId, userId)
        } catch (e: IOException) {
            return Resource.Error("Cannot retrieve data. Check your internet connection and try again.")
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred.")
        }

        if(response.isSuccessful && response.body() != null){
            return Resource.Success(response.body()!!)
        }
        return Resource.Error("Unknown error occurred.")
    }

    suspend fun unfollowUser(followerId: Long, userId: Long): Resource<FollowUnfollowResponse> {
        val response = try {
            api.unfollowUser(followerId, userId)
        } catch (e: IOException) {
            return Resource.Error("Cannot retrieve data. Check your internet connection and try again.")
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred.")
        }

        if(response.isSuccessful && response.body() != null){
            return Resource.Success(response.body()!!)
        }
        return Resource.Error("Unknown error occurred.")
    }
}