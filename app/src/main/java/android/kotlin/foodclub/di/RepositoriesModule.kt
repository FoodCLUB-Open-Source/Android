package android.kotlin.foodclub.di

import android.kotlin.foodclub.api.authentication.API
import android.kotlin.foodclub.network.retrofit.apiInterfaces.AuthenticationService
import android.kotlin.foodclub.network.retrofit.apiInterfaces.ProductsService
import android.kotlin.foodclub.network.retrofit.dtoMappers.EdamamFoodProductsMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.ForgotChangePasswordMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.SignInUserMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.auth.SignUpUserMapper
import android.kotlin.foodclub.repositories.AuthRepository
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.repositories.ProfileRepository
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
    fun provideProfileRepository(api: API): ProfileRepository {
        return ProfileRepository(api)
    }

    @Provides
    @Singleton
    fun providePostRepository(api: API): PostRepository {
        return PostRepository(api)
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
    fun provideProductRepository(api: ProductsService, mapper: EdamamFoodProductsMapper): ProductRepository {
        return ProductRepository(api, mapper)
    }

    @Provides
    @Singleton
    fun provideStoryRepository(api: API): StoryRepository {
        return StoryRepository(api)
    }
}