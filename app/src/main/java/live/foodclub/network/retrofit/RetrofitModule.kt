package live.foodclub.network.retrofit

import live.foodclub.network.retrofit.services.AuthenticationService
import live.foodclub.network.retrofit.utils.auth.RefreshTokenManager
import live.foodclub.network.retrofit.utils.SessionCache
import live.foodclub.network.retrofit.utils.auth.AuthAuthenticator
import live.foodclub.network.retrofit.utils.auth.AuthInterceptor
import live.foodclub.network.retrofit.utils.auth.ResponsesInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
    fun provideResponsesInterceptor(): ResponsesInterceptor {
        return ResponsesInterceptor()
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
            .baseUrl("http://foodclub-prod-load-balancer-347824685.eu-west-2.elb.amazonaws.com:3000/api/v1/")
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
        responsesInterceptor: ResponsesInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addNetworkInterceptor(responsesInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }
}