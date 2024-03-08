package android.kotlin.foodclub.di

import android.kotlin.foodclub.localdatasource.localdatasource.profile_posts_local_datasource.ProfilePostsLocalDataSource
import android.kotlin.foodclub.localdatasource.room.database.FoodCLUBDatabase
import android.kotlin.foodclub.localdatasource.room.entity.OfflineUserPostsModel
import android.kotlin.foodclub.network.remotedatasource.profile_remote_datasource.ProfilePostsRemoteMediator
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.UserLocalPostsMapper
import android.kotlin.foodclub.repositories.ProfileRepository
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
class PagerModule {
    @Provides
    @Singleton
    fun provideUserPostsPager(
        foodCLUBDatabase: FoodCLUBDatabase,
        profileRepository: ProfileRepository,
        localVideosMapper: UserLocalPostsMapper,
        profilePostsLocalDataSource: ProfilePostsLocalDataSource
    ): Pager<Int, OfflineUserPostsModel> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = ProfilePostsRemoteMediator(
                foodClubDb = foodCLUBDatabase,
                repository = profileRepository,
                localVideosMapper = localVideosMapper
            ),
            pagingSourceFactory = { profilePostsLocalDataSource.pagingSource() }
        )
    }
}