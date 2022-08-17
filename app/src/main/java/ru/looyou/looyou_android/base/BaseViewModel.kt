package ru.looyou.looyou_android.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel : ViewModel() {

    val loading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val errorHandler = CoroutineExceptionHandler { _, exception ->
        loading.value = false
        Log.d("ERROR", exception.message.toString())
    }
}