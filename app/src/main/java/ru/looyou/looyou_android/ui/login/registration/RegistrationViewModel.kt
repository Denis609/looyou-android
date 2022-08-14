package ru.looyou.looyou_android.ui.login.registration

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.looyou.looyou_android.api.auth.AuthService
import ru.looyou.looyou_android.api.dto.*
import ru.looyou.looyou_android.api.looyou.LooYouService
import ru.looyou.looyou_android.base.BaseViewModel
import ru.looyou.looyou_android.base.SharedPrefs
import ru.looyou.looyou_android.util.Parser
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val looYouService: LooYouService,
    private val authService: AuthService,
    private val sharedPrefs: SharedPrefs
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
            _registrationState.value = looYouService.createRegistration(
                AccountRegistrationCreateDto(email = email)
            )
            loading.value = false
        }
    }

    fun confirmRegistration(verificationCode: String) {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            loading.value = true
            _registrationState.value = looYouService.confirmRegistration(
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
            _account.value = looYouService.createAccount(
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
            looYouService.sendVerifyCode(
                AccountRegistrationSendVerifyCodeDto(
                    accountRegistrationId = registrationState.value!!.id
                )
            )
            loading.value = false
        }
    }

    fun singIn(
        login: String
    ) {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            loading.value = true
            authService.signIn(login, _password)
            val response: HttpResponse = authService.getCode()
            val code = Parser.parse(URI(response.headers["Location"]))
            sharedPrefs.authToken = authService.getTokens(code!!)
            _success.value = true
            loading.value = true
        }
    }
}