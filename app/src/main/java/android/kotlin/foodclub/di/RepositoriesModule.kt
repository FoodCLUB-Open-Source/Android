package android.kotlin.foodclub.di

import android.kotlin.foodclub.api.authentication.API
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.repositories.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Provides
    @Singleton
    fun provideProfileRepository(api: API): ProfileRepository {
        return ProfileRepository(api)
    }

    @Provides
    @Singleton
    fun providePostRepository(api: API): PostRepository {
        return PostRepository(api)
    }
}