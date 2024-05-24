package live.foodclub.repositories

import live.foodclub.domain.models.profile.SimpleUserModel
import live.foodclub.domain.models.profile.UserProfile
import live.foodclub.network.retrofit.dtoMappers.profile.FollowerUserMapper
import live.foodclub.network.retrofit.dtoMappers.profile.FollowingUserMapper
import live.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import live.foodclub.network.retrofit.responses.profile.FollowUnfollowResponse
import live.foodclub.network.retrofit.responses.profile.RetrieveFollowerListResponse
import live.foodclub.network.retrofit.responses.profile.RetrieveFollowingListResponse
import live.foodclub.network.retrofit.responses.profile.RetrieveProfileResponse
import live.foodclub.network.retrofit.responses.profile.UpdateUserProfileImageResponse
import live.foodclub.network.retrofit.utils.apiRequestFlow
import live.foodclub.localdatasource.localdatasource.profile_local_datasource.ProfileLocalDataSource
import live.foodclub.localdatasource.room.entity.ProfileEntity
import live.foodclub.network.remotedatasource.profile_remote_datasource.ProfileRemoteDataSource
import live.foodclub.network.retrofit.dtoMappers.profile.LocalDataMapper
import live.foodclub.network.retrofit.dtoMappers.profile.OfflineProfileDataMapper
import live.foodclub.network.retrofit.dtoModels.profile.UserProfileDto
import live.foodclub.utils.helpers.Resource
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import live.foodclub.domain.enums.PostType
import live.foodclub.localdatasource.room.dao.PostDao
import live.foodclub.localdatasource.room.relationships.PostWithUser
import live.foodclub.network.remotedatasource.posts.PostsRemoteMediator
import live.foodclub.network.remotedatasource.posts.provider.PostsRemoteDataSourceProvider
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@OptIn(ExperimentalPagingApi::class)
class ProfileRepository(
    private val profileRemoteDataSource: ProfileRemoteDataSource,
    private val profileLocalDataSource: ProfileLocalDataSource,
    private val postDao: PostDao,
    private val postsRemoteDataSourceProvider: PostsRemoteDataSourceProvider,
    private val localDataMapper: LocalDataMapper,
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

    fun getUserProfileData(userId: Long): Flow<UserProfile> {
        return profileLocalDataSource.getProfileData(userId).map {
            if (it == null) {
                UserProfile.default()
            } else {
                offlineProfileMapper.mapToDomainModel(it)
            }
        }
    }

    fun getUserPosts(userId: Long): Pager<Int, PostWithUser> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PostsRemoteMediator(
                userId = userId,
                postType = PostType.PROFILE,
                postDao = postDao,
                postsDataSource = postsRemoteDataSourceProvider.getProfilePostsDataSource(),
            ),
            pagingSourceFactory = { postDao.getPagingData(PostType.PROFILE) }
        )
    }

    fun getBookmarkedVideos(userId: Long): Pager<Int, PostWithUser> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PostsRemoteMediator(
                userId = userId,
                postType = PostType.BOOKMARK,
                postDao = postDao,
                postsDataSource = postsRemoteDataSourceProvider.getBookmarkDataSource(),
            ),
            pagingSourceFactory = { postDao.getPagingData(PostType.BOOKMARK) }
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