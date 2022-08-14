package ru.looyou.looyou_android.di

import android.app.Application
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.looyou.looyou_android.base.Jsons
import ru.looyou.looyou_android.base.SharedPrefs
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideShared(context: Application, gson: Gson): SharedPrefs =
        SharedPrefs(context, gson = gson)

    @Singleton
    @Provides
    fun provideGson(): Gson = Jsons.create("yyyy-MM-dd HH:mm:ss")
}