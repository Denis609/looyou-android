package ru.looyou.data.di.db

import android.app.Application
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.looyou.data.db.sharedprefs.Jsons
import ru.looyou.data.db.sharedprefs.SharedPrefsImpl
import ru.looyou.domain.db.sharedprefs.SharedPrefs
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SharedPrefsModule {

    @Singleton
    @Provides
    fun provideSharedPrefs(context: Application, gson: Gson): SharedPrefs =
        SharedPrefsImpl(context, gson = gson)

    @Singleton
    @Provides
    fun provideGson(): Gson = Jsons.create("yyyy-MM-dd HH:mm:ss")
}