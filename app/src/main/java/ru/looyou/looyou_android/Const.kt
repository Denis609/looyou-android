package ru.looyou.looyou_android

import androidx.viewbinding.BuildConfig

object Const {

    private const val BASE_OAUTH_URL_WORK = "https://looyou-auth-dev.herokuapp.com/" // "http://192.168.1.100:8000/"
    private const val BASE_OAUTH_URL_TEST = "https://looyou-auth-dev.herokuapp.com/" // "http://192.168.1.100:8000/"
    val baseOAuthUrl = if (BuildConfig.DEBUG) BASE_OAUTH_URL_TEST else BASE_OAUTH_URL_WORK

    const val CLIENT_ID = "72935774ef0f2ecf3c9514bba3e16375f766fac060acc63790e1e21a98d0cb12"
    const val SCOPE = "mobileapp"
    const val REDIRECT_URI = "http://authorized"
    const val GRANT_TYPE = "authorization_code"
    const val CLIENT_SECRET = "d2a82db0d9dbac0cf308bdd7c732774bda1b66bcec2e710eabd8c633c3dbc8f2s_secret_671873d2d42d523391005b15733867bc783d8ebe903657e17ad21b2b8d7f675d"
}