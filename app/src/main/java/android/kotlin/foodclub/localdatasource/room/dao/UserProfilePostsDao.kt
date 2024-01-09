package android.kotlin.foodclub.localdatasource.room.dao

import android.kotlin.foodclub.localdatasource.room.entity.OfflineUserPostsModel
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfilePostsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfileVideosData(videos: List<OfflineUserPostsModel>)

    @Query("SELECT * FROM user_posts where videoId=:id")
    fun getProfileVideosData(id: Long): Flow<OfflineUserPostsModel>

    @Update
    suspend fun updateProfileVideosData(videosModel: OfflineUserPostsModel)

    @Query("SELECT * FROM user_posts")
    fun getAllProfileVideosData(): Flow<List<OfflineUserPostsModel>>

}