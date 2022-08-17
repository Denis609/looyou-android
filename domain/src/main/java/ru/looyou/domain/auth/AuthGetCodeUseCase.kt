package ru.looyou.domain.auth

import io.ktor.client.statement.*
import javax.inject.Inject

class AuthGetCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend fun execute(): HttpResponse = authRepository.getCode()
}