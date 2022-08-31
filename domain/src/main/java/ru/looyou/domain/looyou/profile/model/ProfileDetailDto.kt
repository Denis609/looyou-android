package ru.looyou.domain.looyou.profile.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDetailDto(
    val id: String,
    val photos: Set<ProfileDetailPhotoDto>,
    val attributes: Set<ProfileDetailAttributeDto>
)
