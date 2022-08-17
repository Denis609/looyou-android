package ru.looyou.domain.looyou.account

import ru.looyou.domain.looyou.LooYouRepository
import ru.looyou.domain.looyou.account.model.AccountCreateDto
import ru.looyou.domain.looyou.account.model.AccountDto
import javax.inject.Inject

class LooYouCreateAccountUseCase @Inject constructor(
    private val looYouRepository: LooYouRepository,
) {
    suspend fun execute(accountCreateDto: AccountCreateDto): AccountDto =
        looYouRepository.createAccount(
            accountCreateDto = accountCreateDto
        )
}