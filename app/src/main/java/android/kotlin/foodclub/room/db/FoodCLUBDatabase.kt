package android.kotlin.foodclub.room.db

import android.kotlin.foodclub.room.dao.UserDetailsDao
import android.kotlin.foodclub.room.dao.UserProfileVideosDao
import android.kotlin.foodclub.room.entity.OfflineProfileModel
import android.kotlin.foodclub.room.entity.OfflineProfileVideosModel
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [
    OfflineProfileModel::class,
    OfflineProfileVideosModel:: class
                     ],
    version = 1,
    exportSchema = false
)
abstract class FoodCLUBDatabase: RoomDatabase() {
    abstract fun getUserDetailsDao(): UserDetailsDao
    abstract fun getProfileVideosDao(): UserProfileVideosDao
}