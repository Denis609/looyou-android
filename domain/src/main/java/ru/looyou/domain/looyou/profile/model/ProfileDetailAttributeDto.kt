package ru.looyou.domain.looyou.profile.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDetailAttributeDto(
    val id: String,
    val type: ProfileDetailAttributeTypeDto,
    val value: String
)
