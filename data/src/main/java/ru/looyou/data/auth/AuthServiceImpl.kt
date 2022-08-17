package ru.looyou.data.auth

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import ru.looyou.data.Const
import ru.looyou.domain.auth.TokenDto
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val client: HttpClient
) : ru.looyou.domain.auth.AuthRepository {
    override suspend fun signIn(
        login: String,
        password: String
    ): HttpResponse = client.post("login") {
        setBody(FormDataContent(Parameters.build {
            append("email", login)
            append("password", password)
            append("authentication_method", "Looyou")
            append("authentication_client", "Mobile")
        }))
    }

    override suspend fun signInGoogle(
        authorizationCode: String
    ): HttpResponse = client.post("login") {
        setBody(FormDataContent(Parameters.build {
            append("authorization_code", authorizationCode)
            append("account_type", "Google")
            append("authentication_client", "Android")
        }))
    }

    override suspend fun getCode(): HttpResponse {
        return client.get("oauth2/authorize?response_type=code&client_id=${Const.CLIENT_ID}&scope=${Const.SCOPE}&redirect_uri=${Const.REDIRECT_URI}")
    }

    override suspend fun getToken(code: String): TokenDto = client.post("oauth2/token") {
        setBody(FormDataContent(Parameters.build {
            append("grant_type", Const.GRANT_TYPE)
            append("client_id", Const.CLIENT_ID)
            append("client_secret", Const.CLIENT_SECRET)
            append("code", code)
            append("redirect_uri", Const.REDIRECT_URI)
        }))
    }.body()
}