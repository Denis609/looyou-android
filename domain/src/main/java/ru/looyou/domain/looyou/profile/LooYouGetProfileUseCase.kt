package ru.looyou.domain.looyou.profile

import ru.looyou.domain.looyou.LooYouRepository
import javax.inject.Inject

class LooYouGetProfileUseCase @Inject constructor(
    private val looYouRepository: LooYouRepository,
) {
    suspend fun execute(): Any =
        looYouRepository.getProfile()
}