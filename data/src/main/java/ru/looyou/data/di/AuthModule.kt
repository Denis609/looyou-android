package ru.looyou.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import ru.looyou.data.auth.AuthServiceImpl
import ru.looyou.data.di.network.NetworkAuthModule
import ru.looyou.domain.auth.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Singleton
    @Provides
    fun provideAuthRepository(@NetworkAuthModule.AuthInterceptorOkHttpClient httpClient: HttpClient): AuthRepository =
        AuthServiceImpl(httpClient)
}