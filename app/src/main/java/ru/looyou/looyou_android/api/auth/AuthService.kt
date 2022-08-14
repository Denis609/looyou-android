package ru.looyou.looyou_android.api.auth

import io.ktor.client.statement.*
import ru.looyou.looyou_android.api.dto.TokenDto

interface AuthService {

    suspend fun signIn(login: String, password: String): HttpResponse

    suspend fun getCode(): HttpResponse

    suspend fun getTokens(code: String): TokenDto

    suspend fun signInGoogle(authorization_code: String): HttpResponse
}