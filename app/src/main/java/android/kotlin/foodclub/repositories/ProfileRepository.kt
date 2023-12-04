package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import android.kotlin.foodclub.domain.models.profile.UserDetailsModel
import android.kotlin.foodclub.domain.models.profile.UserPosts
import android.kotlin.foodclub.domain.models.profile.UserProfile
import android.kotlin.foodclub.room.util.daoRequestFlow
import android.kotlin.foodclub.network.retrofit.services.ProfileService
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.FollowerUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.FollowingUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.UserDetailsMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.UserPostsMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.UserProfileMapper
import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.FollowUnfollowResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveFollowerListResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveFollowingListResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveProfileResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveUserDetailsResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.UpdateUserProfileImageResponse
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.room.entity.ProfileModel
import android.kotlin.foodclub.room.repository.datasource.ProfileDataLocalSource
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.ProfileModelMapper
import android.kotlin.foodclub.utils.helpers.Resource
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ProfileRepository(
    private val api: ProfileService,
    private val profileDataLocalSource: ProfileDataLocalSource,
    private val profileMapper: UserProfileMapper,
    private val userPostsMapper: UserPostsMapper,
    private val profileModelMapper: ProfileModelMapper,
    private val followerUserMapper: FollowerUserMapper,
    private val followingUserMapper: FollowingUserMapper,
    private val userDetailsMapper: UserDetailsMapper
) {

    suspend fun retrieveProfileData(
        userId: Long, pageNo: Int? = null, pageSize: Int? = null
    ): Resource<UserProfile, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<RetrieveProfileResponse, DefaultErrorResponse> {
                api.retrieveProfileData(userId, pageNo, pageSize)
            }
        ) {
            is Resource.Success -> {
                Resource.Success(
                    profileMapper.mapToDomainModel(resource.data!!.body()!!.data)
                )
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun retrieveUserDetails(userId: Long): Resource<UserDetailsModel, DefaultErrorResponse> {
        return when (val resource = apiRequestFlow<RetrieveUserDetailsResponse, DefaultErrorResponse> {
            api.retrieveUserDetails(userId)
        }) {
            is Resource.Success -> {
                val userDetailsDto = resource.data?.body()?.data
                if (userDetailsDto != null) {
                    val userDetailsModel = userDetailsMapper.mapToDomainModel(userDetailsDto)
                    Resource.Success(userDetailsModel)
                } else {
                    Log.i("MYTAG","Response body or data is null")
                    Resource.Error("Response body or data is null")
                }
            }
            is Resource.Error -> {
                Log.i("MYTAG","${resource.message}")
                Resource.Error(resource.message ?: "Unknown error")
            }
        }
    }

    fun retrieveLocalUserDetails(userId: Long): Resource<Flow<UserDetailsModel>, String> {
        return when(val response = daoRequestFlow<Flow<UserDetailsModel>, String> {
            profileDataLocalSource.getData(userId)
                .map { profileModelMapper.mapToDomainModel(it) }
        }){
            is Resource.Success -> {
                Resource.Success(response.data!!)
            }
            is Resource.Error -> {
                Resource.Error(response.message!!)
            }
        }
    }

    suspend fun insertLocalUserDetails(profileModel: ProfileModel) {
        return profileDataLocalSource.insertData(profileModel)
    }

    suspend fun updateLocalProfileData(profileModel: ProfileModel) {
        return profileDataLocalSource.updateData(profileModel)
    }

    suspend fun updateUserProfileImage(
        userId: Long,
        file: File
    ): Resource<UpdateUserProfileImageResponse, DefaultErrorResponse> {
        return when (val resource = apiRequestFlow<UpdateUserProfileImageResponse, DefaultErrorResponse> {
            api.updateUserProfileImage(
                userId,
                imagePart = MultipartBody.Part
                    .createFormData(
                        "image",
                        file.name,
                        file.asRequestBody()
                    )
            )
        }) {
            is Resource.Success -> {
                Resource.Success(resource.data!!.body()!!)
            }
            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun retrieveBookmarkedPosts(
        userId: Long, pageSize: Int? = null, pageNo: Int? = null
    ): Resource<List<UserPosts>, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<RetrievePostsListResponse, DefaultErrorResponse> {
                api.getBookmarkedPosts(userId, pageNo, pageSize)
            }
        ) {
            is Resource.Success -> {
                Resource.Success(
                    resource.data!!.body()!!.data.map {
                        userPostsMapper.mapToDomainModel(it)
                    }
                )
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun retrieveProfileFollowers(
        userId: Long, pageSize: Int? = null, pageNo: Int? = null
    ): Resource<List<SimpleUserModel>, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<RetrieveFollowerListResponse, DefaultErrorResponse> {
                api.retrieveProfileFollowers(userId, pageNo, pageSize)
            }
        ) {
            is Resource.Success -> {
                Resource.Success(
                    resource.data!!.body()!!.data.map {
                        followerUserMapper.mapToDomainModel(it)
                    }
                )
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun retrieveProfileFollowing(
        userId: Long, pageSize: Int? = null, pageNo: Int? = null
    ): Resource<List<SimpleUserModel>, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<RetrieveFollowingListResponse, DefaultErrorResponse> {
                api.retrieveProfileFollowing(userId, pageNo, pageSize)
            }
        ) {
            is Resource.Success -> {
                Resource.Success(
                    resource.data!!.body()!!.data.map {
                        followingUserMapper.mapToDomainModel(it)
                    }
                )
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun followUser(
        followerId: Long, userId: Long
    ): Resource<FollowUnfollowResponse, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<FollowUnfollowResponse, DefaultErrorResponse> {
                api.followUser(followerId, userId)
            }
        ) {
            is Resource.Success -> {
                Resource.Success(resource.data!!.body()!!)
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun unfollowUser(
        followerId: Long, userId: Long
    ): Resource<FollowUnfollowResponse, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<FollowUnfollowResponse, DefaultErrorResponse> {
                api.unfollowUser(followerId, userId)
            }
        ) {
            is Resource.Success -> {
                Resource.Success(resource.data!!.body()!!)
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }
}