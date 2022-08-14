package ru.looyou.looyou_android.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class AccountDto(
    val id: String,
    val email: String
)
