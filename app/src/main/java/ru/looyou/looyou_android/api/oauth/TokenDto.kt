package ru.looyou.looyou_android.api.oauth

import kotlinx.serialization.Serializable

@Serializable
data class TokenDto(
    val access_token: String,
    val refresh_token: String,
    val scope: String,
    val token_type: String,
    val expires_in: String
)