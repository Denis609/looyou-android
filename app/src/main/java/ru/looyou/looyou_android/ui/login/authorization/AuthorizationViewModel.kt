package ru.looyou.looyou_android.ui.login.authorization

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.looyou.looyou_android.api.auth.AuthService
import ru.looyou.looyou_android.base.BaseViewModel
import ru.looyou.looyou_android.base.SharedPrefs
import ru.looyou.looyou_android.util.Parser
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authService: AuthService,
    private val sharedPrefs: SharedPrefs
) : BaseViewModel() {
    private val _success: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    fun singIn(
        login: String,
        password: String
    ) {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            loading.value = true
            authService.signIn(login, password)
            val response: HttpResponse = authService.getCode()
            val code = Parser.parse(URI(response.headers["Location"]))
            sharedPrefs.authToken = authService.getTokens(code!!)
            _success.value = true
            loading.value = true
        }
    }

    fun singInGoogle(
        authorization_code: String
    ) {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            authService.signInGoogle(authorization_code)
            val response: HttpResponse = authService.getCode()
            val code = Parser.parse(URI(response.headers["Location"]))
            sharedPrefs.authToken = authService.getTokens(code!!)
            _success.value = true
        }
    }
}