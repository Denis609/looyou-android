package ru.looyou.data.db.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.looyou.domain.auth.TokenDto
import ru.looyou.domain.db.sharedprefs.SharedPrefs
import javax.inject.Inject

class SharedPrefsImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    name: String = "MAIN",
    private val gson: Gson
) : SharedPrefs {
    companion object {
        private const val CLIENT = "CLIENT"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    private var authTokenDto: TokenDto?
        set(value) = save(CLIENT, value)
        get() = get(CLIENT, object : TypeToken<TokenDto>(){})


    override fun getAuthToken(): TokenDto? = authTokenDto

    override fun setAuthToken(value: TokenDto?) {
        authTokenDto = value
    }

    override fun logout() {
        delete(CLIENT)
    }

    private fun <T> save(key: String, data: T) = prefs.edit().putString(key, gson.toJson(data)).apply()

    private fun <T> get(key: String, typeToken: TypeToken<T>): T? =
        gson.fromJson<T>(prefs.getString(key, ""), typeToken.type)

    private fun delete(key: String) = prefs.edit().remove(key).apply()

}