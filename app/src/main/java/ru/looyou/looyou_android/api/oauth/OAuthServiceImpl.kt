package ru.looyou.looyou_android.api.oauth

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import ru.looyou.looyou_android.Const
import ru.looyou.looyou_android.api.dto.TokenDto
import javax.inject.Inject

class OAuthServiceImpl @Inject constructor(
    private val client: HttpClient
) : OAuthService {
    override suspend fun signIn(
        login: String,
        password: String
    ): HttpResponse {
        return client.post("auth/login") {
            setBody(FormDataContent(Parameters.build {
                append("email", login)
                append("password", password)
                append("account_type", "Looyou")
            }))
        }
    }

    override suspend fun signInGoogle(
        authorization_code: String
    ): HttpResponse {
        return client.post("auth/login") {
            setBody(FormDataContent(Parameters.build {
                append("authorization_code", authorization_code)
                append("account_type", "Google")
            }))
        }
    }

    override suspend fun getCode(): HttpResponse {
        return client.get("auth/authorize?response_type=code&client_id=${Const.CLIENT_ID}&scope=${Const.SCOPE}&redirect_uri=${Const.REDIRECT_URI}")
    }

    override suspend fun getTokens(code: String): TokenDto {
        return client.post("auth/token") {
            setBody(FormDataContent(Parameters.build {
                append("grant_type", Const.GRANT_TYPE)
                append("client_id", Const.CLIENT_ID)
                append("client_secret", Const.CLIENT_SECRET)
                append("code", code)
                append("redirect_uri", Const.REDIRECT_URI)
            }))
        }.body()
    }
}