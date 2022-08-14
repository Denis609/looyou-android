package ru.looyou.looyou_android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import ru.looyou.looyou_android.api.auth.AuthService
import ru.looyou.looyou_android.api.auth.AuthServiceImpl
import ru.looyou.looyou_android.di.NetworkModule.AuthInterceptorOkHttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AuthModule {

    @Singleton
    @Provides
    fun provideApi(@AuthInterceptorOkHttpClient httpClient: HttpClient): AuthService =
        AuthServiceImpl(httpClient)
}