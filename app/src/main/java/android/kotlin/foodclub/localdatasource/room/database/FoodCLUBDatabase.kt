package android.kotlin.foodclub.localdatasource.room.database

import android.kotlin.foodclub.localdatasource.room.entity.UserDetailsModel
import android.kotlin.foodclub.localdatasource.room.converters.Converters
import android.kotlin.foodclub.localdatasource.room.dao.ProfileDataDao
import android.kotlin.foodclub.localdatasource.room.dao.UserDetailsDao
import android.kotlin.foodclub.localdatasource.room.dao.UserProfileBookmarksDao
import android.kotlin.foodclub.localdatasource.room.dao.UserProfilePostsDao
import android.kotlin.foodclub.localdatasource.room.entity.OfflineProfileModel
import android.kotlin.foodclub.localdatasource.room.entity.OfflineUserBookmarksModel
import android.kotlin.foodclub.localdatasource.room.entity.OfflineUserPostsModel
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        UserDetailsModel::class,
        OfflineUserPostsModel::class,
        OfflineProfileModel::class,
        OfflineUserBookmarksModel::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FoodCLUBDatabase : RoomDatabase() {
    abstract fun getUserDetailsDao(): UserDetailsDao
    abstract fun getUserProfilePostsDao(): UserProfilePostsDao
    abstract fun getProfileDao(): ProfileDataDao
    abstract fun getUserProfileBookmarksDao(): UserProfileBookmarksDao
}