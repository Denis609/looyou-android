package ru.looyou.domain.auth

import io.ktor.client.statement.*
import javax.inject.Inject

class AuthSignInGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend fun execute(authorizationCode: String): HttpResponse =
        authRepository.signInGoogle(authorizationCode = authorizationCode)
}