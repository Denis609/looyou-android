package ru.looyou.looyou_android.api.oauth

import android.content.ContentValues.TAG
import android.util.Log
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import ru.looyou.looyou_android.Const
import ru.looyou.looyou_android.api.dto.TokenDto

interface OAuthService {

    suspend fun signIn(login: String, password: String): HttpResponse

    suspend fun getCode(): HttpResponse

    suspend fun getTokens(code: String): TokenDto

    companion object {
        fun create(): HttpClient =
            HttpClient {
                /* Logging */
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Log.d(TAG, message)
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
                    })
                }
                /* Timeout */
                install(HttpTimeout) {
                    requestTimeoutMillis = 15000L
                    connectTimeoutMillis = 15000L
                    socketTimeoutMillis = 15000L
                }
                install(HttpCookies)
                followRedirects = false
                defaultRequest {
                    url(Const.baseOAuthUrl)
                }
            }
    }
}