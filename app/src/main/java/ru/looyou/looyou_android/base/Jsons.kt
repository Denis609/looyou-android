package ru.looyou.looyou_android.base

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javax.inject.Inject
import javax.inject.Singleton

class Jsons {
    companion object {
        fun create(dateFormat: String? = null): Gson =
            GsonBuilder().setLenient().apply {
                dateFormat?.let {
                    setDateFormat(it)
                }
            }.create()
    }
}