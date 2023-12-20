package android.kotlin.foodclub.network.retrofit.dtoMappers.profile

import android.kotlin.foodclub.network.retrofit.dtoMappers.posts.PostToVideoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileMappersModule {
    @Provides
    @Singleton
    fun provideUserProfileMapper(userPostsMapper: PostToVideoMapper): UserProfileMapper {
        return UserProfileMapper(userPostsMapper)
    }

    @Provides
    @Singleton
    fun provideFollowerUserMapper(): FollowerUserMapper {
        return FollowerUserMapper()
    }

    @Provides
    @Singleton
    fun provideFollowingUserMapper(): FollowingUserMapper {
        return FollowingUserMapper()
    }

    @Provides
    @Singleton
    fun provideUserDetailsMapper(): UserDetailsMapper {
        return UserDetailsMapper()
    }

    @Provides
    @Singleton
    fun provideLocalDataMapper(): LocalDataMapper {
        return LocalDataMapper()
    }

    @Provides
    @Singleton
    fun provideUserLocalPostsMapper(): UserLocalPostsMapper {
        return UserLocalPostsMapper()
    }

    @Provides
    @Singleton
    fun provideUserLocalBookmarksMapper(): UserLocalBookmarksMapper {
        return UserLocalBookmarksMapper()
    }

}