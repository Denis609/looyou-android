package ru.looyou.domain.auth

import io.ktor.client.statement.*

interface AuthRepository {

    suspend fun signIn(login: String, password: String): HttpResponse

    suspend fun getCode(): HttpResponse

    suspend fun getToken(code: String): TokenDto

    suspend fun signInGoogle(authorizationCode: String): HttpResponse
}