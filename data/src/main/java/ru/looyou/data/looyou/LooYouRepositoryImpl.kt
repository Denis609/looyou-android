package ru.looyou.data.looyou

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import ru.looyou.domain.looyou.LooYouRepository
import ru.looyou.domain.looyou.account.model.*

class LooYouRepositoryImpl(
    private val client: HttpClient,
) : LooYouRepository {

    override suspend fun createRegistration(accountRegistrationCreateDto: AccountRegistrationCreateDto): AccountRegistrationDto =
        client.post("account/registration") {
            setBody(accountRegistrationCreateDto)
        }.body()

    override suspend fun confirmRegistration(accountRegistrationVerifyDto: AccountRegistrationVerifyDto): AccountRegistrationDto =
        client.post("account/registration/verify") {
            setBody(accountRegistrationVerifyDto)
        }.body()

    override suspend fun createAccount(accountCreateDto: AccountCreateDto): AccountDto =
        client.post("account") {
            setBody(accountCreateDto)
        }.body()

    override suspend fun sendVerifyCode(accountRegistrationSendVerifyCodeDto: AccountRegistrationSendVerifyCodeDto): HttpResponse =
        client.post("account/registration/verify/code/send") {
            setBody(accountRegistrationSendVerifyCodeDto)
        }
}