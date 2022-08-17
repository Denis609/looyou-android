package ru.looyou.domain.looyou.account.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountRegistrationDto(
    val id: String,
    val email: String,
    val isVerified: Boolean,
    val isComplete: Boolean
)
