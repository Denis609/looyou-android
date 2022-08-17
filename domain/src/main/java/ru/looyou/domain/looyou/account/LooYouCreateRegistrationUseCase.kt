package ru.looyou.domain.looyou.account

import ru.looyou.domain.looyou.LooYouRepository
import ru.looyou.domain.looyou.account.model.AccountRegistrationCreateDto
import ru.looyou.domain.looyou.account.model.AccountRegistrationDto
import javax.inject.Inject

class LooYouCreateRegistrationUseCase @Inject constructor(
    private val looYouRepository: LooYouRepository,
) {
    suspend fun execute(accountRegistrationCreateDto: AccountRegistrationCreateDto): AccountRegistrationDto =
        looYouRepository.createRegistration(
            accountRegistrationCreateDto = accountRegistrationCreateDto
        )
}