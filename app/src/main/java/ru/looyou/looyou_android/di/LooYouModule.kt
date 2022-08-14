package ru.looyou.looyou_android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import ru.looyou.looyou_android.api.looyou.LooYouService
import ru.looyou.looyou_android.api.looyou.LooYouServiceImpl
import ru.looyou.looyou_android.di.NetworkModule.LooYouInterceptorOkHttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LooYouModule {
    @Singleton
    @Provides
    fun provideApi(@LooYouInterceptorOkHttpClient httpClient: HttpClient): LooYouService =
        LooYouServiceImpl(httpClient)

}