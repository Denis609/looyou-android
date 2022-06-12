package ru.looyou.looyou_android.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel() {

    val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.d("ERROR", exception.message.toString())
    }
}