package ru.looyou.looyou_android.api.looyou

import io.ktor.client.statement.*
import ru.looyou.looyou_android.api.dto.*

interface LooYouService {
    suspend fun createRegistration(accountRegistrationCreateDto: AccountRegistrationCreateDto): AccountRegistrationDto

    suspend fun confirmRegistration(accountRegistrationVerifyDto: AccountRegistrationVerifyDto): AccountRegistrationDto

    suspend fun createAccount(accountCreateDto: AccountCreateDto): AccountDto

    suspend fun sendVerifyCode(accountRegistrationSendVerifyCodeDto: AccountRegistrationSendVerifyCodeDto): HttpResponse
}