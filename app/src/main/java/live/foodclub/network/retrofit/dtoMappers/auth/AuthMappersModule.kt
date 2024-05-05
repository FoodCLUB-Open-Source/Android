package live.foodclub.network.retrofit.dtoMappers.auth

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthMappersModule {
    @Provides
    @Singleton
    fun provideSignInUserMapper(): SignInUserMapper {
        return SignInUserMapper()
    }

    @Provides
    @Singleton
    fun provideForgotChangePasswordMapper(): ForgotChangePasswordMapper {
        return ForgotChangePasswordMapper()
    }

    @Provides
    @Singleton
    fun provideSignUpUserMapper(): SignUpUserMapper {
        return SignUpUserMapper()
    }

    @Provides
    @Singleton
    fun provideFirebaseUserMapper(): FirebaseUserMapper {
        return FirebaseUserMapper()
    }
}