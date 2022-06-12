package ru.looyou.looyou_android.ui.home

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.looyou.looyou_android.api.dto.TokenDto
import ru.looyou.looyou_android.base.BaseViewModel
import ru.looyou.looyou_android.base.SharedPrefs
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sharedPrefs: SharedPrefs
) : BaseViewModel() {

    val token = MutableLiveData<TokenDto>()

    fun getToken() {
        token.postValue(sharedPrefs.authToken)
    }

    fun logout() {
        sharedPrefs.logout()
    }
}