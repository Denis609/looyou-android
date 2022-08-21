package ru.looyou.looyou_android

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.looyou.domain.db.sharedprefs.SharedPrefs
import ru.looyou.looyou_android.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPrefs: SharedPrefs
) : ViewModel() {

    fun authorize() : Boolean = sharedPrefs.getAuthToken()?.let { true } ?: false

}