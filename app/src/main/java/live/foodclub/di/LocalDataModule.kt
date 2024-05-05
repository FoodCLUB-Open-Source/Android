package live.foodclub.di

import live.foodclub.localdatasource.localdatasource.product.ProductLocalDataSource
import live.foodclub.localdatasource.localdatasource.product.ProductLocalDataSourceImpl
import live.foodclub.localdatasource.localdatasource.profile_bookmarked_local_datasource.ProfileBookmarkedLocalDataSource
import live.foodclub.localdatasource.localdatasource.profile_bookmarked_local_datasource.ProfileBookmarkedLocalDataSourceImpl
import live.foodclub.localdatasource.room.dao.UserDetailsDao
import live.foodclub.localdatasource.room.dao.UserProfilePostsDao
import live.foodclub.localdatasource.localdatasource.user_details_local_datasource.UserDetailsLocalDataSource
import live.foodclub.localdatasource.localdatasource.profile_posts_local_datasource.ProfilePostsLocalDataSource
import live.foodclub.localdatasource.localdatasource.user_details_local_datasource.UserDetailsLocalDataSourceImpl
import live.foodclub.localdatasource.localdatasource.profile_posts_local_datasource.ProfilePostsLocalDataSourceImpl
import live.foodclub.localdatasource.localdatasource.profile_local_datasource.ProfileLocalDataSource
import live.foodclub.localdatasource.localdatasource.profile_local_datasource.ProfileLocalDataSourceImpl
import live.foodclub.localdatasource.room.dao.ProductDao
import live.foodclub.localdatasource.room.dao.ProfileDataDao
import live.foodclub.localdatasource.room.dao.UserProfileBookmarksDao
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
        return ProfilePostsLocalDataSourceImpl(userProfileVideosDao)
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

    @Singleton
    @Provides
    fun provideProductLocalDataSource(productDao: ProductDao): ProductLocalDataSource {
        return ProductLocalDataSourceImpl(productDao)
    }
}
