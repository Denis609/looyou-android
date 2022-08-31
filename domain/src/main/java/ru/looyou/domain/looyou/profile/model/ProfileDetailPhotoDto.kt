package ru.looyou.domain.looyou.profile.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDetailPhotoDto(
    val id: String,
    val photo: FileDto
)
