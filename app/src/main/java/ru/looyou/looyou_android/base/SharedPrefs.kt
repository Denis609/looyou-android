package ru.looyou.looyou_android.base

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.looyou.looyou_android.api.dto.TokenDto
import javax.inject.Inject

class SharedPrefs @Inject constructor(
    @ApplicationContext private val context: Context,
    name: String = "MAIN",
    private val gson: Gson
) {
    companion object {
        private const val CLIENT = "CLIENT"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    var authToken: TokenDto?
        set(value) = save(CLIENT, value)
        get() = get(CLIENT, object : TypeToken<TokenDto>(){})

    fun logout() {
        delete(CLIENT)
    }

    fun <T> save(key: String, data: T) = prefs.edit().putString(key, gson.toJson(data)).apply()

    fun <T> get(key: String, typeToken: TypeToken<T>): T? =
        gson.fromJson<T>(prefs.getString(key, ""), typeToken.type)

    fun delete(key: String) = prefs.edit().remove(key).apply()

}