package android.kotlin.foodclub.network.retrofit.services

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {
    @Provides
    @Singleton
    fun provideEdamamService(): ProductsService {
        return Retrofit.Builder()
            .baseUrl("https://api.edamam.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductsService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthenticationService(
        @Named("defaultRetrofit") defaultRetrofit: Retrofit.Builder
    ): AuthenticationService {
        return defaultRetrofit
            .build()
            .create(AuthenticationService::class.java)
    }

    //Here all main services start
    @Provides
    @Singleton
    fun providePostsService(
        @Named("defaultRetrofit") defaultRetrofit: Retrofit.Builder
    ): PostsService {
        return defaultRetrofit
            .build()
            .create(PostsService::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileService(
        @Named("defaultRetrofit") defaultRetrofit: Retrofit.Builder
    ): ProfileService {
        return defaultRetrofit
            .build()
            .create(ProfileService::class.java)
    }

    @Provides
    @Singleton
    fun provideStoriesService(
        @Named("defaultRetrofit") defaultRetrofit: Retrofit.Builder
    ): StoriesService {
        return defaultRetrofit
            .build()
            .create(StoriesService::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeService(
        @Named("defaultRetrofit") defaultRetrofit: Retrofit.Builder
    ): RecipeService {
        return defaultRetrofit
            .build()
            .create(RecipeService::class.java)
    }

    @Provides
    @Singleton
    fun provideSettingsService(
        @Named("defaultRetrofit") defaultRetrofit: Retrofit.Builder
    ): SettingsService {
        return defaultRetrofit
            .build()
            .create(SettingsService::class.java)
    }

    @Provides
    @Singleton
    fun provideLikesService(
        @Named("defaultRetrofit") defaultRetrofit: Retrofit.Builder
    ): LikesService {
        return defaultRetrofit
            .build()
            .create(LikesService::class.java)
    }
    @Provides
    @Singleton
    fun provideBookmarkService(
        @Named("defaultRetrofit") defaultRetrofit: Retrofit.Builder
    ): BookmarksService {
        return defaultRetrofit
            .build()
            .create(BookmarksService::class.java)
    }

    @Provides
    @Singleton
    fun provideFcmService(
        @Named("defaultRetrofit") defaultRetrofit: Builder
    ): FcmService {
        return defaultRetrofit
            .build()
            .create(FcmService::class.java)
    }
}