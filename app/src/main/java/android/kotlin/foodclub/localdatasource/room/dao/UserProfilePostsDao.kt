package android.kotlin.foodclub.localdatasource.room.dao

import android.kotlin.foodclub.localdatasource.room.entity.ProfilePostsEntity
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfilePostsDao {

    @Upsert
    suspend fun insertProfileVideosData(videos: List<ProfilePostsEntity>)

    @Query("SELECT * FROM profile_posts where videoId=:id")
    fun getProfileVideosData(id: Long): Flow<ProfilePostsEntity>

    @Query("SELECT * FROM profile_posts where authorId=:userId")
    fun pagingSource(userId: Long): PagingSource<Int, ProfilePostsEntity>

    @Update
    suspend fun updateProfileVideosData(videosModel: ProfilePostsEntity)

    @Query("DELETE FROM profile_posts where videoId=:id")
    fun deletePost(id: Long)

    @Query("SELECT COUNT(*) FROM profile_posts")
    fun countRows(): Flow<Int>

    @Query("SELECT * FROM profile_posts")
    fun getAllProfileVideosData(): Flow<List<ProfilePostsEntity>>

    @Query("DELETE FROM profile_posts where authorId=:userId")
    fun clearAllForAuthor(userId: Long)
}