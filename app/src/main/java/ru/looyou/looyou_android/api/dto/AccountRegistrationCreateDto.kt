package ru.looyou.looyou_android.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class AccountRegistrationCreateDto(
    val email: String
)
