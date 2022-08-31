package ru.looyou.domain.looyou.profile.model

import kotlinx.serialization.Serializable
import ru.looyou.domain.looyou.account.model.AccountDto
import kotlinx.serialization.UseContextualSerialization
import ru.looyou.domain.looyou.enums.GenderEnum
import java.time.LocalDateTime
import java.util.Date

@Serializable
data class ProfileDto(
    val id: String,
    val account: AccountDto,
    val name: String,
    val avatar: ProfileDetailPhotoDto?,
    val birthday: String?,
    val gender: GenderEnum,
    val detail: ProfileDetailDto?,
    val lastOnlineAt: String
)
