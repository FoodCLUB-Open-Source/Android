package android.kotlin.foodclub.localdatasource.localdatasource.profile_posts_local_datasource

import android.kotlin.foodclub.localdatasource.room.dao.UserProfilePostsDao
import android.kotlin.foodclub.localdatasource.room.entity.ProfilePostsEntity
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

class ProfilePostsLocalDataSourceImpl(
    private val userProfilePostsDao: UserProfilePostsDao
): ProfilePostsLocalDataSource {
    override suspend fun insertProfileVideosData(videos: List<ProfilePostsEntity>) {
        userProfilePostsDao.insertProfileVideosData(videos)
    }

    override fun getProfileVideosData(id: Long): Flow<ProfilePostsEntity> {
        return userProfilePostsDao.getProfileVideosData(id)
    }

    override fun pagingSource(userId: Long): PagingSource<Int, ProfilePostsEntity> {
        return userProfilePostsDao.pagingSource(userId)
    }

    override suspend fun updateProfileVideosData(videosModel: ProfilePostsEntity) {
        return userProfilePostsDao.updateProfileVideosData(videosModel)
    }

    override fun getAllProfileVideosData(): Flow<List<ProfilePostsEntity>> {
        return userProfilePostsDao.getAllProfileVideosData()
    }
}