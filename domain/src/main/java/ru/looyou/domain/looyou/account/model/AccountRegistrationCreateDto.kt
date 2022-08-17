package ru.looyou.domain.looyou.account.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountRegistrationCreateDto(
    val email: String
)
