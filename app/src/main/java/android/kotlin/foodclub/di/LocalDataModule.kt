package android.kotlin.foodclub.di

import android.kotlin.foodclub.localdatasource.localdatasource.profile_bookmarked_local_datasource.ProfileBookmarkedLocalDataSource
import android.kotlin.foodclub.localdatasource.localdatasource.profile_bookmarked_local_datasource.ProfileBookmarkedLocalDataSourceImpl
import android.kotlin.foodclub.localdatasource.room.dao.UserDetailsDao
import android.kotlin.foodclub.localdatasource.room.dao.UserProfilePostsDao
import android.kotlin.foodclub.localdatasource.localdatasource.user_details_local_datasource.UserDetailsLocalDataSource
import android.kotlin.foodclub.localdatasource.localdatasource.profile_posts_local_datasource.ProfilePostsLocalDataSource
import android.kotlin.foodclub.localdatasource.localdatasource.user_details_local_datasource.UserDetailsLocalDataSourceImpl
import android.kotlin.foodclub.localdatasource.localdatasource.profile_posts_local_datasource.ProfileVideosLocalDataSourceImpl
import android.kotlin.foodclub.localdatasource.localdatasource.profile_local_datasource.ProfileLocalDataSource
import android.kotlin.foodclub.localdatasource.localdatasource.profile_local_datasource.ProfileLocalDataSourceImpl
import android.kotlin.foodclub.localdatasource.room.dao.ProfileDataDao
import android.kotlin.foodclub.localdatasource.room.dao.UserProfileBookmarksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideUserDetailsLocalDataSource(userDetailsDao: UserDetailsDao): UserDetailsLocalDataSource {
        return UserDetailsLocalDataSourceImpl(userDetailsDao)
    }

    @Singleton
    @Provides
    fun provideProfilePostsLocalDataSource(userProfileVideosDao: UserProfilePostsDao): ProfilePostsLocalDataSource {
        return ProfileVideosLocalDataSourceImpl(userProfileVideosDao)
    }

    @Singleton
    @Provides
    fun provideProfileLocalDataSource(profileDao: ProfileDataDao): ProfileLocalDataSource {
        return ProfileLocalDataSourceImpl(profileDao)
    }

    @Singleton
    @Provides
    fun provideProfileBookmarksLocalDataSource(profileBookmarksDao: UserProfileBookmarksDao): ProfileBookmarkedLocalDataSource {
        return ProfileBookmarkedLocalDataSourceImpl(profileBookmarksDao)
    }
}
