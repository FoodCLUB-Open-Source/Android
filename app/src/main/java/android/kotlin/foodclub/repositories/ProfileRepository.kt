package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import android.kotlin.foodclub.domain.models.profile.UserProfile
import android.kotlin.foodclub.network.retrofit.dtoMappers.posts.PostToVideoMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.FollowerUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.FollowingUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.UserProfileMapper
import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.FollowUnfollowResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveFollowerListResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveFollowingListResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveProfileResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.UpdateUserProfileImageResponse
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.localdatasource.room.entity.OfflineProfileVideosModel
import android.kotlin.foodclub.localdatasource.localdatasource.profilevideoslocaldatasource.ProfileVideosDataLocalSource
import android.kotlin.foodclub.localdatasource.util.daoRequestFlow
import android.kotlin.foodclub.localdatasource.util.daoRequestWithFlow
import android.kotlin.foodclub.network.remotedatasource.profile_remote_datasource.ProfileRemoteDataSource
import android.kotlin.foodclub.utils.helpers.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ProfileRepository(
    private val profileRemoteDataSource: ProfileRemoteDataSource,
    private val profileVideosDataLocalSource: ProfileVideosDataLocalSource,
    private val profileMapper: UserProfileMapper,
    private val userPostsMapper: PostToVideoMapper,
    private val followerUserMapper: FollowerUserMapper,
    private val followingUserMapper: FollowingUserMapper
) {

    suspend fun retrieveProfileData(
        userId: Long, pageNo: Int? = null, pageSize: Int? = null
    ): Resource<UserProfile, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<RetrieveProfileResponse, DefaultErrorResponse> {
                profileRemoteDataSource.retrieveProfileData(userId, pageNo, pageSize )
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

    suspend fun updateUserProfileImage(
        userId: Long,
        file: File
    ): Resource<UpdateUserProfileImageResponse, DefaultErrorResponse> {
        return when (val resource = apiRequestFlow<UpdateUserProfileImageResponse, DefaultErrorResponse> {

            profileRemoteDataSource.updateUserProfileImage(
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
    ): Resource<List<VideoModel>, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<RetrievePostsListResponse, DefaultErrorResponse> {
                profileRemoteDataSource.getBookmarkedPosts(userId, pageNo, pageSize)
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
                profileRemoteDataSource.retrieveProfileFollowers(userId, pageNo, pageSize)
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
                profileRemoteDataSource.retrieveProfileFollowing(userId, pageNo, pageSize)
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
                profileRemoteDataSource.followUser(followerId, userId)
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
                profileRemoteDataSource.unfollowUser(followerId, userId)
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

    // local data related
    suspend fun insertProfileVideosData(videosModel: OfflineProfileVideosModel) {
        daoRequestFlow<Unit, String> {
            profileVideosDataLocalSource.insertProfileVideosData(videosModel)
        }
    }

    suspend fun retrieveAllLocalProfileVideos(): Resource<List<OfflineProfileVideosModel>, String> {
        return daoRequestWithFlow {
            profileVideosDataLocalSource.getAllProfileVideosData()
        }
    }

}