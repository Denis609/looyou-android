package ru.looyou.domain.looyou.account.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountRegistrationSendVerifyCodeDto(
    val accountRegistrationId: String
)
