package ru.looyou.looyou_android.util

import java.net.URI

object Parser {
    fun parse(uri: URI): String? {
        val parameters: Map<String, String> = uri.query.split("&").associate {
            it.split("=").let {
                it[0] to it[1]
            }
        }
        return parameters["code"]
    }
}