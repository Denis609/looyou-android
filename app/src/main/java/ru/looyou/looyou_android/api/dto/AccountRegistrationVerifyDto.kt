package ru.looyou.looyou_android.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class AccountRegistrationVerifyDto(
    val accountRegistrationId: String,
    val verificationCode: String
)
