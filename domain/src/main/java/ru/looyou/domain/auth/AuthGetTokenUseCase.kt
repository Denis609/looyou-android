package ru.looyou.domain.auth

import javax.inject.Inject

class AuthGetTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend fun execute(code: String): TokenDto = authRepository.getToken(code = code)
}