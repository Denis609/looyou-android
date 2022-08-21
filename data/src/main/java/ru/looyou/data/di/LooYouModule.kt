package ru.looyou.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import ru.looyou.data.di.network.NetworkLooYouModule
import ru.looyou.data.looyou.LooYouRepositoryImpl
import ru.looyou.domain.looyou.LooYouRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LooYouModule {

    @Singleton
    @Provides
    fun provideLooYouRepository(
        @NetworkLooYouModule.LooYouInterceptorOkHttpClient httpClient: HttpClient
    ): LooYouRepository =
        LooYouRepositoryImpl(httpClient)
}