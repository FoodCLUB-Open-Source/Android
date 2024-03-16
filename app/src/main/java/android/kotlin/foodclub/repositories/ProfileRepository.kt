package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import android.kotlin.foodclub.domain.models.profile.UserProfile
import android.kotlin.foodclub.localdatasource.localdatasource.profile_bookmarked_local_datasource.ProfileBookmarkedLocalDataSource
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.FollowerUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.FollowingUserMapper
import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.FollowUnfollowResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveFollowerListResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveFollowingListResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.RetrieveProfileResponse
import android.kotlin.foodclub.network.retrofit.responses.profile.UpdateUserProfileImageResponse
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.localdatasource.localdatasource.profile_posts_local_datasource.ProfilePostsLocalDataSource
import android.kotlin.foodclub.localdatasource.localdatasource.profile_local_datasource.ProfileLocalDataSource
import android.kotlin.foodclub.localdatasource.room.database.FoodCLUBDatabase
import android.kotlin.foodclub.localdatasource.room.entity.ProfileBookmarksEntity
import android.kotlin.foodclub.localdatasource.room.entity.ProfileEntity
import android.kotlin.foodclub.localdatasource.room.entity.ProfilePostsEntity
import android.kotlin.foodclub.network.remotedatasource.profile_remote_datasource.ProfileBookmarksRemoteMediator
import android.kotlin.foodclub.network.remotedatasource.profile_remote_datasource.ProfilePostsRemoteMediator
import android.kotlin.foodclub.network.remotedatasource.profile_remote_datasource.ProfileRemoteDataSource
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.LocalDataMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.OfflineProfileDataMapper
import android.kotlin.foodclub.network.retrofit.dtoModels.posts.PostModelDto
import android.kotlin.foodclub.network.retrofit.dtoModels.profile.UserProfileDto
import android.kotlin.foodclub.utils.helpers.Resource
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@OptIn(ExperimentalPagingApi::class)
class ProfileRepository(
    private val profileRemoteDataSource: ProfileRemoteDataSource,
    private val profileLocalDataSource: ProfileLocalDataSource,
    private val profilePostsLocalDataSource: ProfilePostsLocalDataSource,
    private val profileBookmarkedLocalDataSource: ProfileBookmarkedLocalDataSource,
    private val localDataMapper: LocalDataMapper,
    private val foodCLUBDatabase: FoodCLUBDatabase,
    private val offlineProfileMapper: OfflineProfileDataMapper,
    private val followerUserMapper: FollowerUserMapper,
    private val followingUserMapper: FollowingUserMapper
) {

    suspend fun retrieveProfileData(
        userId: Long, pageNo: Int? = null, pageSize: Int? = null
    ): Resource<UserProfileDto, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<RetrieveProfileResponse, DefaultErrorResponse> {
                profileRemoteDataSource.retrieveProfileData(userId, pageNo, pageSize)
            }
        ) {
            is Resource.Success -> {
                val mappedProfileData = localDataMapper.mapToDomainModel(resource.data!!.body()!!.data)
                mappedProfileData.userId = userId
                saveLocalProfileDetails(mappedProfileData)

                Resource.Success(
                    resource.data.body()!!.data
                )
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun retrieveBookmarkedPosts(
        userId: Long, pageSize: Int? = null, pageNo: Int? = null
    ): Resource<List<PostModelDto>, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<RetrievePostsListResponse, DefaultErrorResponse> {
                profileRemoteDataSource.getBookmarkedPosts(userId, pageNo, pageSize)
            }
        ) {
            is Resource.Success -> {
                Resource.Success(
                    resource.data!!.body()!!.data
                )
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    fun getUserProfileData(userId: Long): Flow<UserProfile> {
        return profileLocalDataSource.getProfileData(userId).map {
            if (it == null) {
                UserProfile.default()
            } else {
                offlineProfileMapper.mapToDomainModel(it)
            }
        }
    }

    fun getUserPosts(userId: Long): Pager<Int, ProfilePostsEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = ProfilePostsRemoteMediator(
                userId = userId,
                foodClubDb = foodCLUBDatabase,
                repository = this,
            ),
            pagingSourceFactory = { profilePostsLocalDataSource.pagingSource(userId) }
        )
    }

    fun getBookmarkedVideos(userId: Long): Pager<Int, ProfileBookmarksEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = ProfileBookmarksRemoteMediator(
                userId = userId,
                foodClubDb = foodCLUBDatabase,
                repository = this,
            ),
            pagingSourceFactory = { profileBookmarkedLocalDataSource.pagingSource(userId) }
        )
    }

    suspend fun updateUserProfileImage(
        file: File
    ): Resource<UpdateUserProfileImageResponse, DefaultErrorResponse> {
        return when (val resource = apiRequestFlow<UpdateUserProfileImageResponse, DefaultErrorResponse> {

            profileRemoteDataSource.updateUserProfileImage(
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

    suspend fun followUser(userId: Long): Resource<FollowUnfollowResponse, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<FollowUnfollowResponse, DefaultErrorResponse> {
                profileRemoteDataSource.followUser(userId)
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

    suspend fun unfollowUser(userId: Long): Resource<FollowUnfollowResponse, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<FollowUnfollowResponse, DefaultErrorResponse> {
                profileRemoteDataSource.unfollowUser(userId)
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

    suspend fun saveLocalProfileDetails(profileEntity: ProfileEntity){
        profileLocalDataSource.insertProfileLocalData(profileEntity)
    }
}