package android.kotlin.foodclub.di

import android.kotlin.foodclub.network.retrofit.apiInterfaces.AuthenticationService
import android.kotlin.foodclub.network.retrofit.apiInterfaces.PostsService
import android.kotlin.foodclub.network.retrofit.apiInterfaces.ProductsService
import android.kotlin.foodclub.network.retrofit.apiInterfaces.ProfileService
import android.kotlin.foodclub.network.retrofit.apiInterfaces.RecipeService
import android.kotlin.foodclub.network.retrofit.apiInterfaces.StoriesService
import android.kotlin.foodclub.network.retrofit.dtoMappers.edamam.EdamamFoodProductsMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.posts.PostToVideoMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.ForgotChangePasswordMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.SignInUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.SignUpUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.FollowerUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.FollowingUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.UserPostsMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.UserProfileMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.recipes.RecipeMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.stories.StoryMapper
import android.kotlin.foodclub.repositories.AuthRepository
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.repositories.RecipeRepository
import android.kotlin.foodclub.repositories.StoryRepository
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
        profileMapper: UserProfileMapper,
        userPostsMapper: UserPostsMapper,
        followerUserMapper: FollowerUserMapper,
        followingUserMapper: FollowingUserMapper
    ): ProfileRepository {
        return ProfileRepository(
            api, profileMapper, userPostsMapper, followerUserMapper, followingUserMapper
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
}