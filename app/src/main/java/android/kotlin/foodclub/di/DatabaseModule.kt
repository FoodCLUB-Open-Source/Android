package android.kotlin.foodclub.di

import android.app.Application
import android.kotlin.foodclub.localdatasource.room.database.FoodCLUBDatabase
import android.kotlin.foodclub.localdatasource.room.dao.UserDetailsDao
import android.kotlin.foodclub.localdatasource.room.dao.UserProfileVideosDao
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideCoinDatabase(app: Application): FoodCLUBDatabase {
        return Room.databaseBuilder(
            app,
            FoodCLUBDatabase::class.java,
            "foodclub_room_database"
        )
            .fallbackToDestructiveMigration() // later change this to meet our intends
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDetailsDao(foodCLUBDatabase: FoodCLUBDatabase): UserDetailsDao {
        return foodCLUBDatabase.getUserDetailsDao()
    }

    @Provides
    @Singleton
    fun provideUserProfileVideosDao(foodCLUBDatabase: FoodCLUBDatabase): UserProfileVideosDao {
        return foodCLUBDatabase.getProfileVideosDao()
    }

}
