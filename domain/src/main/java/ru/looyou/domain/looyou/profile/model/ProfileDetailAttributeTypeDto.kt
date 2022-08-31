package ru.looyou.domain.looyou.profile.model

import kotlinx.serialization.Serializable
import ru.looyou.domain.looyou.enums.ProfileDetailAttributeTypeEnum

@Serializable
data class ProfileDetailAttributeTypeDto(
    val id: ProfileDetailAttributeTypeEnum,
    val name: String
)
