package live.foodclub.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import live.foodclub.localdatasource.localdatasource.product.ProductLocalDataSource
import live.foodclub.localdatasource.localdatasource.profile_bookmarked_local_datasource.ProfileBookmarkedLocalDataSource
import live.foodclub.localdatasource.localdatasource.profile_local_datasource.ProfileLocalDataSource
import live.foodclub.localdatasource.localdatasource.profile_posts_local_datasource.ProfilePostsLocalDataSource
import live.foodclub.localdatasource.localdatasource.user_details_local_datasource.UserDetailsLocalDataSource
import live.foodclub.localdatasource.room.dao.UserProfilePostsDao
import live.foodclub.localdatasource.room.database.FoodCLUBDatabase
import live.foodclub.network.remotedatasource.product.ProductRemoteDataSource
import live.foodclub.network.remotedatasource.profile_remote_datasource.ProfileRemoteDataSource
import live.foodclub.network.remotedatasource.settings_remote_datasource.SettingsRemoteDataSource
import live.foodclub.network.retrofit.dtoMappers.auth.FirebaseUserMapper
import live.foodclub.network.retrofit.services.AuthenticationService
import live.foodclub.network.retrofit.dtoMappers.posts.PostToVideoMapper
import live.foodclub.network.retrofit.dtoMappers.auth.ForgotChangePasswordMapper
import live.foodclub.network.retrofit.dtoMappers.auth.SignInUserMapper
import live.foodclub.network.retrofit.dtoMappers.auth.SignUpUserMapper
import live.foodclub.network.retrofit.dtoMappers.posts.PostToVideoMapper
import live.foodclub.network.retrofit.dtoMappers.profile.FollowerUserMapper
import live.foodclub.network.retrofit.dtoMappers.profile.FollowingUserMapper
import live.foodclub.network.retrofit.dtoMappers.profile.LocalDataMapper
import live.foodclub.network.retrofit.dtoMappers.profile.OfflineProfileDataMapper
import live.foodclub.network.retrofit.dtoMappers.profile.UserDetailsMapper
import live.foodclub.network.retrofit.dtoMappers.stories.StoryMapper
import live.foodclub.network.retrofit.services.AuthenticationService
import live.foodclub.network.retrofit.services.BookmarksService
import live.foodclub.network.retrofit.services.LikesService
import live.foodclub.network.retrofit.services.PostsService
import live.foodclub.network.retrofit.services.RecipeService
import live.foodclub.network.retrofit.services.SearchService
import live.foodclub.network.retrofit.services.StoriesService
import live.foodclub.repositories.AuthRepository
import live.foodclub.repositories.BookmarkRepository
import live.foodclub.repositories.FirebaseUserRepository
import live.foodclub.repositories.LikesRepository
import live.foodclub.repositories.PostRepository
import live.foodclub.repositories.ProductRepository
import live.foodclub.repositories.ProfileRepository
import live.foodclub.repositories.RecipeRepository
import live.foodclub.repositories.SearchRepository
import live.foodclub.repositories.SettingsRepository
import live.foodclub.repositories.StoryRepository
import live.foodclub.utils.helpers.ConnectivityUtils
import live.foodclub.localdatasource.localdatasource.user_details_local_datasource.UserDetailsLocalDataSource
import live.foodclub.localdatasource.localdatasource.profile_local_datasource.ProfileLocalDataSource
import live.foodclub.network.remotedatasource.product.ProductRemoteDataSource
import live.foodclub.network.remotedatasource.profile_remote_datasource.ProfileRemoteDataSource
import live.foodclub.network.remotedatasource.settings_remote_datasource.SettingsRemoteDataSource
import live.foodclub.network.retrofit.dtoMappers.auth.FirebaseUserMapper
import live.foodclub.network.retrofit.services.SearchService
import live.foodclub.repositories.FirebaseUserRepository
import live.foodclub.repositories.SearchRepository
import live.foodclub.utils.helpers.ConnectivityUtils
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import live.foodclub.localdatasource.room.dao.PostDao
import live.foodclub.network.remotedatasource.posts.provider.PostsRemoteDataSourceProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Provides
    @Singleton
    fun provideProfileRepository(
        profileRemoteDataSource: ProfileRemoteDataSource,
        profileLocalDataSource: ProfileLocalDataSource,
        postDao: PostDao,
        postsRemoteDataSourceProvider: PostsRemoteDataSourceProvider,
        followerUserMapper: FollowerUserMapper,
        followingUserMapper: FollowingUserMapper
    ): ProfileRepository {
        return ProfileRepository(
            profileRemoteDataSource,
            profileLocalDataSource,
            postDao,
            postsRemoteDataSourceProvider,
            followerUserMapper,
            followingUserMapper
        )
    }

    @Provides
    @Singleton
    fun providePostRepository(
        api: PostsService,
        postDao: PostDao,
        postsRemoteDataSourceProvider: PostsRemoteDataSourceProvider,
        postToVideoMapper: PostToVideoMapper,
    ): PostRepository {
        return PostRepository(api, postDao, postsRemoteDataSourceProvider, postToVideoMapper)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthenticationService,
        signInUserMapper: SignInUserMapper,
        forgotChangePasswordMapper: ForgotChangePasswordMapper,
        signUpUserMapper: SignUpUserMapper,
        firebaseUserRepository: FirebaseUserRepository,
        firebaseUserMapper: FirebaseUserMapper,
        firebaseMessaging: FirebaseMessaging
    ): AuthRepository {
        return AuthRepository(
            api,
            signInUserMapper,
            forgotChangePasswordMapper,
            signUpUserMapper,
            firebaseUserRepository,
            firebaseUserMapper,
            firebaseMessaging
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

    @Singleton
    @Provides
    fun provideFirebaseUserRepository(
        firestore: FirebaseFirestore,
        firebaseMessaging: FirebaseMessaging,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): FirebaseUserRepository =
        FirebaseUserRepository(
            firestore = firestore,
            firebaseMessaging = firebaseMessaging,
            ioDispatcher = ioDispatcher,
        )
}