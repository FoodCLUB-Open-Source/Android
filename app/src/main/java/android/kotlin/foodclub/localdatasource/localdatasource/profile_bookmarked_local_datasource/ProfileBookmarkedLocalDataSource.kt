package android.kotlin.foodclub.localdatasource.localdatasource.profile_bookmarked_local_datasource

import android.kotlin.foodclub.localdatasource.room.entity.ProfileBookmarksEntity
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

interface ProfileBookmarkedLocalDataSource {

    suspend fun insertBookmarkedVideosData(videos: List<ProfileBookmarksEntity>)
    fun getBookmarkedVideosData(id: Long): Flow<ProfileBookmarksEntity>

    fun pagingSource(userId: Long): PagingSource<Int, ProfileBookmarksEntity>
    suspend fun updateBookmarkedVideosData(videosModel: ProfileBookmarksEntity)
    fun getAllBookmarkedVideosData(): Flow<List<ProfileBookmarksEntity>>


}