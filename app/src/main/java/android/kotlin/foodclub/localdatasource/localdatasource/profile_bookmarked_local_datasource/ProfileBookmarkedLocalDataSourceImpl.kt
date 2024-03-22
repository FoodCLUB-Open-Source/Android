package android.kotlin.foodclub.localdatasource.localdatasource.profile_bookmarked_local_datasource

import android.kotlin.foodclub.localdatasource.room.dao.UserProfileBookmarksDao
import android.kotlin.foodclub.localdatasource.room.entity.ProfileBookmarksEntity
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileBookmarkedLocalDataSourceImpl @Inject constructor(
    private val profileBookmarksDao: UserProfileBookmarksDao
): ProfileBookmarkedLocalDataSource {
    override suspend fun insertBookmarkedVideosData(videos: List<ProfileBookmarksEntity>) {
        profileBookmarksDao.insertBookmarkedVideosData(videos)
    }

    override fun getBookmarkedVideosData(id: Long): Flow<ProfileBookmarksEntity> {
        return profileBookmarksDao.getBookmarkedVideosData(id)
    }

    override fun pagingSource(userId: Long): PagingSource<Int, ProfileBookmarksEntity> {
        return profileBookmarksDao.pagingSource(userId)
    }

    override suspend fun updateBookmarkedVideosData(videosModel: ProfileBookmarksEntity) {
        profileBookmarksDao.updateBookmarkedVideosData(videosModel)
    }

    override fun getAllBookmarkedVideosData(): Flow<List<ProfileBookmarksEntity>> {
        return profileBookmarksDao.getAllBookmarkedVideosData()
    }
}