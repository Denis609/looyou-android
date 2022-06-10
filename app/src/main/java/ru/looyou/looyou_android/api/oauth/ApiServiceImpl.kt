package ru.looyou.looyou_android.api.oauth

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import ru.looyou.looyou_android.Const
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(
    private val client: HttpClient
) : ApiService {
    override suspend fun signIn(
        login: String,
        password: String
    ): HttpResponse {
        return client.post("login") {
            setBody(FormDataContent(Parameters.build {
                append("username", login)
                append("password", password)
            }))
        }
    }

    override suspend fun getCode(): HttpResponse {
        return client.get("oauth2/authorize?response_type=code&client_id=${Const.CLIENT_ID}&scope=${Const.SCOPE}&redirect_uri=${Const.REDIRECT_URI}")
    }

    override suspend fun getTokens(code: String): TokenDto {
        return client.post("oauth2/token") {
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