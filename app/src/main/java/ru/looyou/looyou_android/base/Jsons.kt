package ru.looyou.looyou_android.base

import com.google.gson.Gson
import com.google.gson.GsonBuilder

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