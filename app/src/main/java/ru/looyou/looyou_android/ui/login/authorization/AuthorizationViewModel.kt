package ru.looyou.looyou_android.ui.login.authorization

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.looyou.domain.auth.*
import ru.looyou.looyou_android.base.BaseViewModel
import ru.looyou.domain.db.sharedprefs.SharedPrefs
import ru.looyou.looyou_android.util.Parser
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authSignInUseCase: AuthSignInUseCase,
    private val authGetCodeUseCase: AuthGetCodeUseCase,
    private val authGetTokenUseCase: AuthGetTokenUseCase,
    private val signInGoogleUseCase: AuthSignInGoogleUseCase,
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
            authSignInUseCase.execute(login, password)
            val response: HttpResponse = authGetCodeUseCase.execute()
            val code = Parser.parse(URI(response.headers["Location"]))
            sharedPrefs.setAuthToken(authGetTokenUseCase.execute(code!!))
            _success.value = true
            loading.value = true
        }
    }

    fun singInGoogle(
        authorizationCode: String
    ) {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            signInGoogleUseCase.execute(authorizationCode)
            val response: HttpResponse = authGetCodeUseCase.execute()
            val code = Parser.parse(URI(response.headers["Location"]))
            sharedPrefs.setAuthToken(authGetTokenUseCase.execute(code!!))
            _success.value = true
        }
    }
}