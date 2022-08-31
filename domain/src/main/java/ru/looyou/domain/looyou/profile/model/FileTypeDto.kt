package ru.looyou.domain.looyou.profile.model

import kotlinx.serialization.Serializable
import ru.looyou.domain.looyou.enums.FileTypeEnum

@Serializable
data class FileTypeDto(
    val id: FileTypeEnum,
    val name: String
)