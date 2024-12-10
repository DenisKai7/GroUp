package com.example.projectcapstone

import android.content.Context
import android.content.SharedPreferences
import android.media.session.MediaSession.Token

object SessionManager {
    const val KEY_LOGIN: String = "login"
    const val KEY_TOKEN: String = "token"
    const val KEY_EMAIL: String = "email"
    private const val NAME_KEY = "NAME_KEY"
    private const val PROFILE_IMAGE_KEY = "PROFILE_IMAGE_KEY"

    fun getSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            "capstone", Context.MODE_PRIVATE
        )
    }

    fun setUserData(
        context: Context,
        accessToken: String?,
        email: String?,

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

    fun getUserData(context: Context): Triple<String?, String?, String?> {
        val sharedPreferences = context.getSharedPreferences("capstone", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString(NAME_KEY, null)
        val email = sharedPreferences.getString(KEY_EMAIL, null)
        val profileImage = sharedPreferences.getString(PROFILE_IMAGE_KEY, null)
        return Triple(name, email, profileImage)
    }

    fun saveUserData(context: Context, name: String, email: String, profileImage: String? = null) {
        val sharedPreferences = context.getSharedPreferences("capstone", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(NAME_KEY, name)
            putString(KEY_EMAIL, email)
            profileImage?.let { putString(PROFILE_IMAGE_KEY, it) }
            apply()
        }
    }


    fun getIsLogin(context: Context): Boolean {
        return getSharedPreference(context).getBoolean(KEY_LOGIN, false)
    }

    fun clearData(context: Context) {
        val sharedPreferences = context.getSharedPreferences("capstone", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}