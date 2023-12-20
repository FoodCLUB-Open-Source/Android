package android.kotlin.foodclub.di

import android.kotlin.foodclub.network.retrofit.services.AuthenticationService
import android.kotlin.foodclub.network.retrofit.services.PostsService
import android.kotlin.foodclub.network.retrofit.services.ProductsService
import android.kotlin.foodclub.network.retrofit.services.RecipeService
import android.kotlin.foodclub.network.retrofit.services.StoriesService
import android.kotlin.foodclub.network.retrofit.dtoMappers.edamam.EdamamFoodProductsMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.posts.PostToVideoMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.ForgotChangePasswordMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.SignInUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.SignUpUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.FollowerUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.FollowingUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.UserDetailsMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.UserProfileMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.recipes.RecipeMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.stories.StoryMapper
import android.kotlin.foodclub.network.retrofit.services.BookmarksService
import android.kotlin.foodclub.network.retrofit.services.LikesService
import android.kotlin.foodclub.repositories.AuthRepository
import android.kotlin.foodclub.repositories.BookmarkRepository
import android.kotlin.foodclub.repositories.LikesRepository
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.repositories.RecipeRepository
import android.kotlin.foodclub.repositories.SettingsRepository
import android.kotlin.foodclub.repositories.StoryRepository
import android.kotlin.foodclub.localdatasource.localdatasource.userdetailslocaldatasource.UserDetailsLocalDataSource
import android.kotlin.foodclub.localdatasource.localdatasource.profilevideoslocaldatasource.ProfileVideosDataLocalSource
import android.kotlin.foodclub.network.remotedatasource.profile_remote_datasource.ProfileRemoteDataSource
import android.kotlin.foodclub.network.remotedatasource.settings_remote_datasource.SettingsRemoteDataSource
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
        profileVideosDataLocalSource: ProfileVideosDataLocalSource,
        profileMapper: UserProfileMapper,
        userPostsMapper: PostToVideoMapper,
        followerUserMapper: FollowerUserMapper,
        followingUserMapper: FollowingUserMapper
    ): ProfileRepository {
        return ProfileRepository(
            profileRemoteDataSource,
            profileVideosDataLocalSource,
            profileMapper,
            userPostsMapper,
            followerUserMapper,
            followingUserMapper
        )
    }

    @Provides
    @Singleton
    fun providePostRepository(
        api: PostsService, postToVideoMapper: PostToVideoMapper
    ): PostRepository {
        return PostRepository(api, postToVideoMapper)
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
        api: ProductsService, mapper: EdamamFoodProductsMapper
    ): ProductRepository {
        return ProductRepository(api, mapper)
    }

    @Provides
    @Singleton
    fun provideStoryRepository(api: StoriesService, storyMapper: StoryMapper): StoryRepository {
        return StoryRepository(api, storyMapper)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(api: RecipeService, recipeMapper: RecipeMapper): RecipeRepository {
        return RecipeRepository(api, recipeMapper)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(settingsRemoteDataSource: SettingsRemoteDataSource, userDetailsMapper: UserDetailsMapper, profileDataLocalSource: UserDetailsLocalDataSource): SettingsRepository {
        return SettingsRepository(settingsRemoteDataSource, userDetailsMapper, profileDataLocalSource)
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
}