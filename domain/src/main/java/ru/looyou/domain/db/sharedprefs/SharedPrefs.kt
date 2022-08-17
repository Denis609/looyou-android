package ru.looyou.domain.db.sharedprefs

import ru.looyou.domain.auth.TokenDto

interface SharedPrefs {

    fun getAuthToken(): TokenDto?

    fun setAuthToken(value: TokenDto?)

    fun logout()
}