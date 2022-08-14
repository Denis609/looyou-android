package ru.looyou.looyou_android.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class AccountCreateDto(
    val accountRegistrationId: String,
    val password: String
)
