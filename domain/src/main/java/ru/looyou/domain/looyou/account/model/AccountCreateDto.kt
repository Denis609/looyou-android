package ru.looyou.domain.looyou.account.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountCreateDto(
    val accountRegistrationId: String,
    val password: String
)
