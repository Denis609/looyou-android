package ru.looyou.domain.auth

import io.ktor.client.statement.*
import javax.inject.Inject

class AuthSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend fun execute(login: String, password: String): HttpResponse =
        authRepository.signIn(
            login = login,
            password = password
        )
}