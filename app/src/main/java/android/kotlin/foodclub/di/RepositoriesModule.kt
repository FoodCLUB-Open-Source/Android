package android.kotlin.foodclub.di

import android.kotlin.foodclub.localdatasource.localdatasource.product.ProductLocalDataSource
import android.kotlin.foodclub.localdatasource.localdatasource.profile_bookmarked_local_datasource.ProfileBookmarkedLocalDataSource
import android.kotlin.foodclub.network.retrofit.services.AuthenticationService
import android.kotlin.foodclub.network.retrofit.dtoMappers.posts.PostToVideoMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.ForgotChangePasswordMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.SignInUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.SignUpUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.FollowerUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.FollowingUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.UserDetailsMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.stories.StoryMapper
import android.kotlin.foodclub.network.retrofit.services.BookmarksService
import android.kotlin.foodclub.network.retrofit.services.LikesService
import android.kotlin.foodclub.network.retrofit.services.PostsService
import android.kotlin.foodclub.network.retrofit.services.RecipeService
import android.kotlin.foodclub.network.retrofit.services.StoriesService
import android.kotlin.foodclub.repositories.AuthRepository
import android.kotlin.foodclub.repositories.BookmarkRepository
import android.kotlin.foodclub.repositories.LikesRepository
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.repositories.RecipeRepository
import android.kotlin.foodclub.repositories.SettingsRepository
import android.kotlin.foodclub.repositories.StoryRepository
import android.kotlin.foodclub.localdatasource.localdatasource.user_details_local_datasource.UserDetailsLocalDataSource
import android.kotlin.foodclub.localdatasource.localdatasource.profile_posts_local_datasource.ProfilePostsLocalDataSource
import android.kotlin.foodclub.localdatasource.localdatasource.profile_local_datasource.ProfileLocalDataSource
import android.kotlin.foodclub.localdatasource.room.dao.UserProfilePostsDao
import android.kotlin.foodclub.localdatasource.room.database.FoodCLUBDatabase
import android.kotlin.foodclub.network.remotedatasource.product.ProductRemoteDataSource
import android.kotlin.foodclub.network.remotedatasource.profile_remote_datasource.ProfileRemoteDataSource
import android.kotlin.foodclub.network.remotedatasource.settings_remote_datasource.SettingsRemoteDataSource
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.LocalDataMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.OfflineProfileDataMapper
import android.kotlin.foodclub.network.retrofit.services.SearchService
import android.kotlin.foodclub.repositories.SearchRepository
import android.kotlin.foodclub.utils.helpers.ConnectivityUtils
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
    fun provideProfileRepository(
        profileRemoteDataSource: ProfileRemoteDataSource,
        profileLocalDataSource: ProfileLocalDataSource,
        profilePostsLocalDataSource: ProfilePostsLocalDataSource,
        profileBookmarkedLocalDataSource: ProfileBookmarkedLocalDataSource,
        localDataMapper: LocalDataMapper,
        foodCLUBDatabase: FoodCLUBDatabase,
        offlineProfileMapper: OfflineProfileDataMapper,
        followerUserMapper: FollowerUserMapper,
        followingUserMapper: FollowingUserMapper
    ): ProfileRepository {
        return ProfileRepository(
            profileRemoteDataSource,
            profileLocalDataSource,
            profilePostsLocalDataSource,
            profileBookmarkedLocalDataSource,
            localDataMapper,
            foodCLUBDatabase,
            offlineProfileMapper,
            followerUserMapper,
            followingUserMapper
        )
    }

    @Provides
    @Singleton
    fun providePostRepository(
        api: PostsService,
        postToVideoMapper: PostToVideoMapper,
        userProfilePostsDao: UserProfilePostsDao
    ): PostRepository {
        return PostRepository(api, postToVideoMapper, userProfilePostsDao)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthenticationService,
        signInUserMapper: SignInUserMapper,
        forgotChangePasswordMapper: ForgotChangePasswordMapper,
        signUpUserMapper: SignUpUserMapper
    ): AuthRepository {
        return AuthRepository(
            api, signInUserMapper, forgotChangePasswordMapper, signUpUserMapper
        )
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        productRemoteDataSource: ProductRemoteDataSource,
        productLocalDataSource: ProductLocalDataSource
    ): ProductRepository {
        return ProductRepository(productRemoteDataSource, productLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideStoryRepository(api: StoriesService, storyMapper: StoryMapper): StoryRepository {
        return StoryRepository(api, storyMapper)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(api: RecipeService): RecipeRepository {
        return RecipeRepository(api)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
        settingsRemoteDataSource: SettingsRemoteDataSource,
        userDetailsMapper: UserDetailsMapper,
        profileDataLocalSource: UserDetailsLocalDataSource,
        connectivityUtils: ConnectivityUtils
    ): SettingsRepository {
        return SettingsRepository(
            settingsRemoteDataSource,
            userDetailsMapper,
            profileDataLocalSource,
            connectivityUtils
        )
    }

    @Provides
    @Singleton
    fun provideLikesRepository(api: LikesService): LikesRepository {
        return LikesRepository(api)
    }

    @Singleton
    @Provides
    fun provideBookmarkRepository(api: BookmarksService): BookmarkRepository {
        return BookmarkRepository(api)
    }

    @Singleton
    @Provides
    fun provideSearchRepository(api: SearchService): SearchRepository {
        return SearchRepository(api)
    }
}