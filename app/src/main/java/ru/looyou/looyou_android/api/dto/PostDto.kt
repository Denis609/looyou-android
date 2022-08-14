package ru.looyou.looyou_android.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val postPhoto: String?,
    val profilePhoto: String?,
    val name: String,
    val year: String,
    val addres: String,
    val date: String
)