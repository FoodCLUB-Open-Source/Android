package android.kotlin.foodclub.localdatasource.room.dao

import android.kotlin.foodclub.localdatasource.room.entity.OfflineUserBookmarksModel
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileBookmarksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarkedVideosData(videos: List<OfflineUserBookmarksModel>)

    @Query("SELECT * FROM user_bookmarks where videoId=:id")
    fun getBookmarkedVideosData(id: Long): Flow<OfflineUserBookmarksModel>

    @Update
    suspend fun updateBookmarkedVideosData(videosModel: OfflineUserBookmarksModel)

    @Query("SELECT * FROM user_bookmarks")
    fun getAllBookmarkedVideosData(): Flow<List<OfflineUserBookmarksModel>>

}