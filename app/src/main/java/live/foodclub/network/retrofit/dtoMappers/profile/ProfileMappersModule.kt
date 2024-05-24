package live.foodclub.network.retrofit.dtoMappers.profile

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
    fun provideOfflineProfileDataMapper(): OfflineProfileDataMapper {
        return OfflineProfileDataMapper()
    }
}