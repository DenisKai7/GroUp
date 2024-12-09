package com.example.projectcapstone

import android.content.Context
import android.content.SharedPreferences
import android.media.session.MediaSession.Token

object SessionManager {
    const val KEY_LOGIN: String = "login"
    const val KEY_TOKEN: String = "token"
    const val KEY_EMAIL: String = "email"

    fun getSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            "capstone", Context.MODE_PRIVATE
        )
    }

    fun setUserData(
        context: Context,
        accessToken: String?,
        email: String?
    ) {
        val editor = getSharedPreference(context).edit()
        editor.putBoolean(KEY_LOGIN, true)
        editor.putString(KEY_TOKEN, accessToken)
        editor.putString(KEY_EMAIL, email)
        editor.apply()
    }
    fun getEmail(context: Context): String {
        return getSharedPreference(context).getString(KEY_EMAIL, "") ?: ""
    }

    fun getAccessToken(context: Context): String {
        return getSharedPreference(context).getString(KEY_TOKEN, "")?:""
    }



    fun getIsLogin(context: Context): Boolean {
        return getSharedPreference(context).getBoolean(KEY_LOGIN, false)
    }

    fun clearData(context: Context) {
        getSharedPreference(context).edit().clear().apply()
    }
}