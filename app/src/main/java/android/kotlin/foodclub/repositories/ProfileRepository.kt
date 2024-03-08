package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import android.kotlin.foodclub.domain.models.profile.UserProfile
import android.kotlin.foodclub.localdatasource.localdatasource.profile_bookmarked_local_datasource.ProfileBookmarkedLocalDataSource
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
import android.kotlin.foodclub.localdatasource.localdatasource.profile_posts_local_datasource.ProfilePostsLocalDataSource
import android.kotlin.foodclub.localdatasource.localdatasource.profile_local_datasource.ProfileLocalDataSource
import android.kotlin.foodclub.localdatasource.room.entity.OfflineProfileModel
import android.kotlin.foodclub.network.remotedatasource.profile_remote_datasource.ProfileRemoteDataSource
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.LocalDataMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.OfflineProfileDataMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.SharedVideoMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.UserLocalBookmarksMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.UserLocalPostsMapper
import android.kotlin.foodclub.network.retrofit.dtoModels.profile.UserProfileDto
import android.kotlin.foodclub.utils.helpers.Resource
import androidx.paging.Pager
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ProfileRepository(
    private val profileRemoteDataSource: ProfileRemoteDataSource,
    private val profileLocalDataSource: ProfileLocalDataSource,
    private val profilePostsLocalDataSource: ProfilePostsLocalDataSource,
    private val profileBookmarkedLocalDataSource: ProfileBookmarkedLocalDataSource,
    private val localDataMapper: LocalDataMapper,
    private val localVideosMapper: UserLocalPostsMapper,
    private val userLocalBookmarksMapper: UserLocalBookmarksMapper,
    private val sharedVideoMapper: SharedVideoMapper,
    private val profileMapper: UserProfileMapper,
    private val userPostsMapper: PostToVideoMapper,
    private val offlineProfileMapper: OfflineProfileDataMapper,
    private val followerUserMapper: FollowerUserMapper,
    private val followingUserMapper: FollowingUserMapper
) {

    suspend fun retrieveProfileData(
        userId: Long = 1, pageNo: Int? = null, pageSize: Int? = null
    ): Resource<UserProfileDto, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<RetrieveProfileResponse, DefaultErrorResponse> {
                profileRemoteDataSource.retrieveProfileData(pageNo, pageSize )
            }
        ) {
            is Resource.Success -> {
                val mappedProfileData = localDataMapper.mapToDomainModel(resource.data!!.body()!!.data)
                mappedProfileData.userId = userId
                saveLocalProfileDetails(mappedProfileData)

//                val userPosts = resource.data.body()!!.data.userPosts.take(10)
//                val mappedPosts = localVideosMapper.mapToDomainModel(userPosts)
//                profilePostsLocalDataSource.insertProfileVideosData(mappedPosts)

                Resource.Success(
//                    profileMapper.mapToDomainModel(resource.data.body()!!.data)
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
    ): Resource<List<VideoModel>, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<RetrievePostsListResponse, DefaultErrorResponse> {
                profileRemoteDataSource.getBookmarkedPosts(userId, pageNo, pageSize)
            }
        ) {
            is Resource.Success -> {
                val bookmarkedPosts = resource.data!!.body()!!.data.take(10)
                val mappedBookmarks = userLocalBookmarksMapper.mapToDomainModel(bookmarkedPosts)

                profileBookmarkedLocalDataSource.insertBookmarkedVideosData(mappedBookmarks)

                Resource.Success(
                    resource.data.body()!!.data.map {
                        userPostsMapper.mapToDomainModel(it)
                    }
                )
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun getUserProfileData(userId: Long): UserProfile {
        val profileData = profileLocalDataSource.getProfileData(userId).firstOrNull()
        profileData?.let { user->
            return offlineProfileMapper.mapToDomainModel(user)
        }
        return UserProfile(
            "User",
            "",
            0,
            0,
            0,
            emptyList(),
            emptyList()
        )
    }

    suspend fun getUserPosts(): List<VideoModel> {
        val profileVideos = profilePostsLocalDataSource.getAllProfileVideosData().firstOrNull()
        profileVideos?.let { posts ->
            return posts.map { videos ->
                sharedVideoMapper.mapToDomainModel(videos)
            }
        } ?: return emptyList()
    }

    suspend fun getBookmarkedVideos(): List<VideoModel> {
        val bookmarkedVideos =  profileBookmarkedLocalDataSource.getAllBookmarkedVideosData().firstOrNull()
        bookmarkedVideos?.let { bookmarks ->
            return bookmarks.map { videos ->
                sharedVideoMapper.mapToDomainModel(videos)
            }
        } ?: return emptyList()
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

    suspend fun saveLocalProfileDetails(offlineProfileModel: OfflineProfileModel){
        profileLocalDataSource.insertProfileLocalData(offlineProfileModel)
    }
}