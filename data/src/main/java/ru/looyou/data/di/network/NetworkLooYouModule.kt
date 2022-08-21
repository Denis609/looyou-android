package ru.looyou.data.di.network

import android.content.ContentValues
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import ru.looyou.data.Const
import ru.looyou.domain.auth.TokenDto
import ru.looyou.domain.db.sharedprefs.SharedPrefs

import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkLooYouModule {

    @Provides
    @Singleton
    @LooYouInterceptorOkHttpClient
    fun provideLooYouHttpClient(
        sharedPrefs: SharedPrefs
    ): HttpClient =
        HttpClient(CIO) {
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
                    prettyPrint = true
                })
            }
            /* Timeout */
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }

            install(Auth) {
                bearer {
                    refreshTokens {
                        val response: HttpResponse = client.submitForm(
                            url = "${Const.baseAuthUrl}oauth2/token",
                            formParameters = Parameters.build {
                                append("grant_type", "refresh_token")
                                append("client_id", Const.CLIENT_ID)
                                append("client_secret", Const.CLIENT_SECRET)
                                append(
                                    "refresh_token",
                                    sharedPrefs.getAuthToken()?.refresh_token ?: ""
                                )
                                append("redirect_uri", Const.REDIRECT_URI)
                            }
                        ) { markAsRefreshTokenRequest() }
                        if (response.status == HttpStatusCode.Unauthorized) {
                            null
                        } else {
                            sharedPrefs.setAuthToken(response.body<TokenDto>())
                            BearerTokens(
                                accessToken = response.body<TokenDto>().access_token,
                                refreshToken = response.body<TokenDto>().refresh_token
                            )
                        }

                    }
                }
            }

            defaultRequest {

                url(Const.baseLooYouUrl)
                contentType(ContentType.Application.Json)
                bearerAuth(token = sharedPrefs.getAuthToken()?.access_token ?: "")
            }

        }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LooYouInterceptorOkHttpClient
}