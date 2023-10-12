package android.kotlin.foodclub.network.retrofit

import android.kotlin.foodclub.network.retrofit.apiInterfaces.AuthenticationService
import android.kotlin.foodclub.network.retrofit.utils.auth.RefreshTokenManager
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.kotlin.foodclub.network.retrofit.utils.auth.AuthAuthenticator
import android.kotlin.foodclub.network.retrofit.utils.auth.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideAuthInterceptor(sessionCache: SessionCache): AuthInterceptor {
        return AuthInterceptor(sessionCache)
    }

    @Provides
    @Singleton
    fun provideAuthAuthenticator(
        sessionCache: SessionCache,
        tokenManager: RefreshTokenManager,
        authenticationService: AuthenticationService
    ): AuthAuthenticator {
        return AuthAuthenticator(sessionCache, tokenManager, authenticationService)
    }

    @Provides
    @Singleton
    @Named("defaultRetrofit")
    fun provideDefaultRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("http://ec2-35-177-0-50.eu-west-2.compute.amazonaws.com:3000/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    @Singleton
    @Named("interceptedRetrofit")
    fun provideInterceptedRetrofitBuilder(
        @Named("defaultRetrofit") defaultRetrofit: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder {
        return defaultRetrofit.client(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }
}