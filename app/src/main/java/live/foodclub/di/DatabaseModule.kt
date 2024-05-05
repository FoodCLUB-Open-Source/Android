package live.foodclub.di

import android.app.Application
import live.foodclub.localdatasource.room.dao.ProductDao
import live.foodclub.localdatasource.room.dao.ProfileDataDao
import live.foodclub.localdatasource.room.database.FoodCLUBDatabase
import live.foodclub.localdatasource.room.dao.UserDetailsDao
import live.foodclub.localdatasource.room.dao.UserProfileBookmarksDao
import live.foodclub.localdatasource.room.dao.UserProfilePostsDao
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
    fun provideFoodCLUBDatabase(app: Application): FoodCLUBDatabase {
        return Room.databaseBuilder(
            app,
            FoodCLUBDatabase::class.java,
            "foodclub_room_database"
        )
            .fallbackToDestructiveMigration() // later change this to meet our intends
            .addMigrations(FoodCLUBDatabase.migration1To2)
            .addMigrations(FoodCLUBDatabase.migration2to3)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDetailsDao(foodCLUBDatabase: FoodCLUBDatabase): UserDetailsDao {
        return foodCLUBDatabase.getUserDetailsDao()
    }

    @Provides
    @Singleton
    fun provideUserProfilePostsDao(foodCLUBDatabase: FoodCLUBDatabase): UserProfilePostsDao {
        return foodCLUBDatabase.getUserProfilePostsDao()
    }

    @Provides
    @Singleton
    fun provideProfileDao(foodCLUBDatabase: FoodCLUBDatabase): ProfileDataDao {
        return foodCLUBDatabase.getProfileDao()
    }

    @Provides
    @Singleton
    fun provideUserProfileBookmarksDao(foodCLUBDatabase: FoodCLUBDatabase): UserProfileBookmarksDao {
        return foodCLUBDatabase.getUserProfileBookmarksDao()
    }

    @Provides
    @Singleton
    fun provideProductDao(foodCLUBDatabase: FoodCLUBDatabase): ProductDao {
        return foodCLUBDatabase.getProductDao()
    }
}
