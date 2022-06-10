package ru.looyou.looyou_android

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.looyou.looyou_android.api.oauth.ApiService
import ru.looyou.looyou_android.api.oauth.ApiServiceImpl
import ru.looyou.looyou_android.base.SharedPrefs
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun providerApi() : ApiService = ApiServiceImpl(ApiService.create())

    @Singleton
    @Provides
    fun provideShared(context: Application) : SharedPrefs = SharedPrefs(context)
}