package android.kotlin.foodclub.di

import android.kotlin.foodclub.network.retrofit.services.AuthenticationService
import android.kotlin.foodclub.network.retrofit.services.PostsService
import android.kotlin.foodclub.network.retrofit.services.ProductsService
import android.kotlin.foodclub.network.retrofit.services.ProfileService
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
import android.kotlin.foodclub.network.retrofit.services.SettingsService
import android.kotlin.foodclub.repositories.AuthRepository
import android.kotlin.foodclub.repositories.BookmarkRepository
import android.kotlin.foodclub.repositories.LikesRepository
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.repositories.RecipeRepository
import android.kotlin.foodclub.repositories.SettingsRepository
import android.kotlin.foodclub.repositories.StoryRepository
import android.kotlin.foodclub.room.repository.datasource.ProfileDataLocalSource
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.ProfileModelMapper
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
        api: ProfileService,
        profileDataLocalSource: ProfileDataLocalSource,
        profileMapper: UserProfileMapper,
        userPostsMapper: PostToVideoMapper,
        profileModelMapper: ProfileModelMapper,
        followerUserMapper: FollowerUserMapper,
        followingUserMapper: FollowingUserMapper,
        userDetailsMapper: UserDetailsMapper
    ): ProfileRepository {
        return ProfileRepository(
            api,
            profileDataLocalSource,
            profileMapper,
            userPostsMapper,
            profileModelMapper,
            followerUserMapper,
            followingUserMapper,
            userDetailsMapper
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
    fun provideSettingsRepository(api: SettingsService, userDetailsMapper: UserDetailsMapper): SettingsRepository {
        return SettingsRepository(api, userDetailsMapper)
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