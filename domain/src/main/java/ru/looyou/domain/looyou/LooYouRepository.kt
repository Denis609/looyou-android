package ru.looyou.domain.looyou

import io.ktor.client.statement.*
import ru.looyou.domain.looyou.account.model.*
import ru.looyou.domain.looyou.profile.model.ProfileDto

interface LooYouRepository {
    suspend fun createRegistration(accountRegistrationCreateDto: AccountRegistrationCreateDto): AccountRegistrationDto

    suspend fun confirmRegistration(accountRegistrationVerifyDto: AccountRegistrationVerifyDto): AccountRegistrationDto

    suspend fun createAccount(accountCreateDto: AccountCreateDto): AccountDto

    suspend fun sendVerifyCode(accountRegistrationSendVerifyCodeDto: AccountRegistrationSendVerifyCodeDto): HttpResponse

    suspend fun getProfile(): ProfileDto
}