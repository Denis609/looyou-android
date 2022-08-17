package ru.looyou.looyou_android.ui.login.registration

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.looyou.domain.auth.AuthGetCodeUseCase
import ru.looyou.domain.auth.AuthGetTokenUseCase
import ru.looyou.domain.auth.AuthSignInUseCase
import ru.looyou.domain.looyou.account.model.*
import ru.looyou.looyou_android.base.BaseViewModel
import ru.looyou.domain.db.sharedprefs.SharedPrefs
import ru.looyou.domain.looyou.account.LooYouConfirmRegistrationUseCase
import ru.looyou.domain.looyou.account.LooYouCreateAccountUseCase
import ru.looyou.domain.looyou.account.LooYouCreateRegistrationUseCase
import ru.looyou.domain.looyou.account.LooYouSendVerifyCodeUseCase
import ru.looyou.looyou_android.util.Parser
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val looYouCreateRegistrationUseCase: LooYouCreateRegistrationUseCase,
    private val looYouConfirmRegistrationUseCase: LooYouConfirmRegistrationUseCase,
    private val looYouCreateAccountUseCase: LooYouCreateAccountUseCase,
    private val looYouSendVerifyCodeUseCase: LooYouSendVerifyCodeUseCase,
    private val authSignInUseCase: AuthSignInUseCase,
    private val authGetCodeUseCase: AuthGetCodeUseCase,
    private val authGetTokenUseCase: AuthGetTokenUseCase,
    private val sharedPrefs: SharedPrefs,
) : BaseViewModel() {

    private val _registrationState: MutableStateFlow<AccountRegistrationDto?> =
        MutableStateFlow(null)
    val registrationState: StateFlow<AccountRegistrationDto?> = _registrationState

    private val _account: MutableStateFlow<AccountDto?> =
        MutableStateFlow(null)
    val account: StateFlow<AccountDto?> = _account

    private val _success: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    private var _password: String = ""

    fun createRegistration(email: String) {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            loading.value = true
            _registrationState.value = looYouCreateRegistrationUseCase.execute(
                AccountRegistrationCreateDto(email = email)
            )
            loading.value = false
        }
    }

    fun confirmRegistration(verificationCode: String) {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            loading.value = true
            _registrationState.value = looYouConfirmRegistrationUseCase.execute(
                AccountRegistrationVerifyDto(
                    accountRegistrationId = registrationState.value!!.id,
                    verificationCode = verificationCode
                )
            )
            loading.value = false
        }
    }

    fun createAccount(password: String) {
        _password = password
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            loading.value = true
            _account.value = looYouCreateAccountUseCase.execute(
                AccountCreateDto(
                    accountRegistrationId = registrationState.value!!.id,
                    password = password
                )
            )
            loading.value = false
        }
    }

    fun sendVerifyCode() {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            loading.value = true
            looYouSendVerifyCodeUseCase.execute(
                AccountRegistrationSendVerifyCodeDto(
                    accountRegistrationId = registrationState.value!!.id
                )
            )
            loading.value = false
        }
    }

    fun singIn(
        login: String,
    ) {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            loading.value = true
            authSignInUseCase.execute(login, _password)
            val response: HttpResponse = authGetCodeUseCase.execute()
            val code = Parser.parse(URI(response.headers["Location"]))
            sharedPrefs.setAuthToken(authGetTokenUseCase.execute(code!!))
            _success.value = true
            loading.value = true
        }
    }
}