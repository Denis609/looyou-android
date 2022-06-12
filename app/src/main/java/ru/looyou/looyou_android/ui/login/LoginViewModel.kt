package ru.looyou.looyou_android.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.looyou.looyou_android.api.oauth.OAuthService
import ru.looyou.looyou_android.base.SharedPrefs
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiService: OAuthService,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {

    val success = MutableLiveData<Boolean>()

    fun singIn(
        login: String,
        password: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            apiService.signIn(login, password)
            val response: HttpResponse = apiService.getCode()
            val code = parse(URI(response.headers["Location"]))
            sharedPrefs.authToken = apiService.getTokens(code!!)
            success.postValue(true)
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