package ru.looyou.looyou_android.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

object Timer {
    fun timer(seconds: Int = 30): Flow<Int> =
        (seconds - 1 downTo 0).asFlow()
            .onEach { delay(1000) }
            .onStart { emit(seconds) }
            .conflate()
}