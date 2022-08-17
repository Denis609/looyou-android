package ru.looyou.domain.looyou.account

import io.ktor.client.statement.*
import ru.looyou.domain.looyou.LooYouRepository
import ru.looyou.domain.looyou.account.model.AccountRegistrationSendVerifyCodeDto
import javax.inject.Inject

class LooYouSendVerifyCodeUseCase @Inject constructor(
    private val looYouRepository: LooYouRepository,
) {
    suspend fun execute(accountRegistrationSendVerifyCodeDto: AccountRegistrationSendVerifyCodeDto): HttpResponse =
        looYouRepository.sendVerifyCode(
            accountRegistrationSendVerifyCodeDto = accountRegistrationSendVerifyCodeDto
        )
}