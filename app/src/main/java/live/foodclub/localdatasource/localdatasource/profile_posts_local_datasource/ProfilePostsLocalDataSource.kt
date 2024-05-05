package live.foodclub.localdatasource.localdatasource.profile_posts_local_datasource

import live.foodclub.localdatasource.room.entity.ProfilePostsEntity
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

interface ProfilePostsLocalDataSource {

    suspend fun insertProfileVideosData(videos: List<ProfilePostsEntity>)
    fun getProfileVideosData(id: Long): Flow<ProfilePostsEntity>

    fun pagingSource(userId: Long): PagingSource<Int, ProfilePostsEntity>
    suspend fun updateProfileVideosData(videosModel: ProfilePostsEntity)
    fun getAllProfileVideosData(): Flow<List<ProfilePostsEntity>>

}