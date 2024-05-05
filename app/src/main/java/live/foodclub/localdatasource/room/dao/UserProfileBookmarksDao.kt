package live.foodclub.localdatasource.room.dao

import live.foodclub.localdatasource.room.entity.ProfileBookmarksEntity
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileBookmarksDao {

    @Upsert
    suspend fun insertBookmarkedVideosData(videos: List<ProfileBookmarksEntity>)

    @Query("SELECT * FROM profile_bookmarked_posts where videoId=:id")
    fun getBookmarkedVideosData(id: Long): Flow<ProfileBookmarksEntity>

    @Query("SELECT * FROM profile_bookmarked_posts where bookmarkedBy=:bookmarkedBy")
    fun pagingSource(bookmarkedBy: Long): PagingSource<Int, ProfileBookmarksEntity>

    @Query("SELECT COUNT(*) FROM profile_bookmarked_posts")
    fun countRows(): Flow<Int>

    @Update
    suspend fun updateBookmarkedVideosData(videosModel: ProfileBookmarksEntity)

    @Query("SELECT * FROM profile_bookmarked_posts")
    fun getAllBookmarkedVideosData(): Flow<List<ProfileBookmarksEntity>>

    @Query("DELETE FROM profile_bookmarked_posts where bookmarkedBy=:userId")
    fun clearAllForBookmarkedBy(userId: Long)

}