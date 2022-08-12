package ru.looyou.looyou_android.ui.login.authorization

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.looyou.looyou_android.api.oauth.OAuthService
import ru.looyou.looyou_android.base.BaseViewModel
import ru.looyou.looyou_android.base.SharedPrefs
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val apiService: OAuthService,
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
            apiService.signIn(login, password)
            val response: HttpResponse = apiService.getCode()
            val code = parse(URI(response.headers["Location"]))
            sharedPrefs.authToken = apiService.getTokens(code!!)
            _success.value = true
            loading.value = true
        }
    }

    fun singInGoogle(
        authorization_code: String
    ) {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            apiService.signInGoogle(authorization_code)
            val response: HttpResponse = apiService.getCode()
            val code = parse(URI(response.headers["Location"]))
            sharedPrefs.authToken = apiService.getTokens(code!!)
            _success.value = true
        }
    }

    private fun parse(uri: URI): String? {
        val parameters: Map<String, String> = uri.query.split("&").associate {
            it.split("=").let {
                it[0] to it[1]
            }
        }
        return parameters["code"]
    }
}