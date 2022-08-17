package ru.looyou.domain.looyou.account

import ru.looyou.domain.looyou.LooYouRepository
import ru.looyou.domain.looyou.account.model.AccountRegistrationDto
import ru.looyou.domain.looyou.account.model.AccountRegistrationVerifyDto
import javax.inject.Inject

class LooYouConfirmRegistrationUseCase @Inject constructor(
    private val looYouRepository: LooYouRepository,
) {
    suspend fun execute(accountRegistrationVerifyDto: AccountRegistrationVerifyDto): AccountRegistrationDto =
        looYouRepository.confirmRegistration(
            accountRegistrationVerifyDto = accountRegistrationVerifyDto
        )
}