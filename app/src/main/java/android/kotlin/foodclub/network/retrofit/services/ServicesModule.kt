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
        return Builder()
            .baseUrl("https://api.edamam.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductsService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthenticationService(
        @Named("defaultRetrofit") defaultRetrofit:Builder
    ): AuthenticationService {
        return defaultRetrofit
            .build()
            .create(AuthenticationService::class.java)
    }

    //Here all main services start
    @Provides
    @Singleton
    fun providePostsService(
        @Named("interceptedRetrofit") defaultRetrofit: Builder
    ): PostsService {
        return defaultRetrofit
            .build()
            .create(PostsService::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileService(
        @Named("interceptedRetrofit") defaultRetrofit: Builder
    ): ProfileService {
        return defaultRetrofit
            .build()
            .create(ProfileService::class.java)
    }

    @Provides
    @Singleton
    fun provideStoriesService(
        @Named("interceptedRetrofit") defaultRetrofit: Builder
    ): StoriesService {
        return defaultRetrofit
            .build()
            .create(StoriesService::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeService(
        @Named("interceptedRetrofit") defaultRetrofit: Builder
    ): RecipeService {
        return defaultRetrofit
            .build()
            .create(RecipeService::class.java)
    }

    @Provides
    @Singleton
    fun provideSettingsService(
        @Named("interceptedRetrofit") defaultRetrofit: Builder
    ): SettingsService {
        return defaultRetrofit
            .build()
            .create(SettingsService::class.java)
    }

    @Provides
    @Singleton
    fun provideLikesService(
        @Named("interceptedRetrofit") defaultRetrofit: Builder
    ): LikesService {
        return defaultRetrofit
            .build()
            .create(LikesService::class.java)
    }
    @Provides
    @Singleton
    fun provideBookmarkService(
        @Named("interceptedRetrofit") defaultRetrofit: Builder
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

    @Provides
    @Singleton
    fun provideSearchService(
        @Named("defaultRetrofit") defaultRetrofit: Builder
    ): SearchService {
        return defaultRetrofit
            .build()
            .create(SearchService::class.java)
    }
}