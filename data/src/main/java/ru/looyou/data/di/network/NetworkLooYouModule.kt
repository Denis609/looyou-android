package ru.looyou.data.di.network

import android.content.ContentValues
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import ru.looyou.data.Const

import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkLooYouModule {

    @Provides
    @Singleton
    @LooYouInterceptorOkHttpClient
    fun provideLooYouHttpClient(): HttpClient =
        HttpClient {
            /* Logging */
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d(ContentValues.TAG, message)
                    }
                }
                level = LogLevel.ALL
            }
            /* JSON */
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = false
                    prettyPrint = true
                })
            }
            /* Timeout */
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }
            defaultRequest {
                url(Const.baseLooYouUrl)
                contentType(ContentType.Application.Json)
            }
        }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LooYouInterceptorOkHttpClient
}