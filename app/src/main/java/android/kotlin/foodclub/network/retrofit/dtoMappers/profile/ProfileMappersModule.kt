package android.kotlin.foodclub.network.retrofit.dtoMappers.profile

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
    fun provideUserPostsMapper(): UserPostsMapper {
        return UserPostsMapper()
    }

    @Provides
    @Singleton
    fun provideUserProfileMapper(userPostsMapper: UserPostsMapper): UserProfileMapper {
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
}