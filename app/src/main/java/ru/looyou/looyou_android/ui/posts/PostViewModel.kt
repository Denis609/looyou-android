package ru.looyou.looyou_android.ui.posts

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.looyou.looyou_android.api.dto.TokenDto
import ru.looyou.looyou_android.base.BaseViewModel
import ru.looyou.looyou_android.base.SharedPrefs
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val sharedPrefs: SharedPrefs
) : BaseViewModel() {


    private val _token: MutableStateFlow<TokenDto?> = MutableStateFlow(null)
    val token: StateFlow<TokenDto?> = _token

    fun getToken() {
        _token.value = sharedPrefs.authToken
    }

    fun logout() {
        sharedPrefs.logout()
    }
}