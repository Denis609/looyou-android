package ru.looyou.domain.looyou.account.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountRegistrationVerifyDto(
    val accountRegistrationId: String,
    val verificationCode: String
)
