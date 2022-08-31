package ru.looyou.domain.looyou.profile

import ru.looyou.domain.looyou.LooYouRepository
import ru.looyou.domain.looyou.profile.model.ProfileDto
import javax.inject.Inject

class LooYouGetProfileUseCase @Inject constructor(
    private val looYouRepository: LooYouRepository,
) {
    suspend fun execute(): ProfileDto =
        looYouRepository.getProfile()
}