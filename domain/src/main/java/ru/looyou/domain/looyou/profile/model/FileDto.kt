package ru.looyou.domain.looyou.profile.model

import kotlinx.serialization.Serializable

@Serializable
data class FileDto(
    val id: String,
    val createdAt: String,
    val name: String,
    val extension: String,
    val type: FileTypeDto
)
