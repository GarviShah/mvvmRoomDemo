package com.example.mymvvmroomdemo.utils.pref

import android.content.Context
import android.content.SharedPreferences
import com.example.mymvvmroomdemo.utils.Constants.APP_SHARED_PREFERENCE_NAME


class PreferencesHelperImpl constructor(context: Context) : PreferencesHelper {

    private var mPrefs: SharedPreferences =
        context.getSharedPreferences(APP_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)

    companion object {
        const val IS_LOGGEN_IN = "is_logged_in"
        const val TOKEN = "token"
        const val USER = "user"
    }

    override fun isLoggedIn(): Boolean {
        return mPrefs.getBoolean(IS_LOGGEN_IN, false)
    }

    override fun setIsLoggedIn() {
        mPrefs.edit().putBoolean(IS_LOGGEN_IN, true).apply()
    }

    override fun setToken(token: String) {
        mPrefs.edit().putString(TOKEN, token).apply()
    }


    override fun getToken(): String? {
        return mPrefs.getString(TOKEN, null)
    }


    override fun setUser(user: String) {
        mPrefs.edit().putString(USER, user).apply()
    }


  /*  override fun getUser(): AppUser? {
        val userJson = mPrefs.getString(USER, null)
        return userJson?.let {
            Moshi.Builder().build().adapter(AppUser::class.java).fromJson(it)
        }
    }*/


    override fun clear() {
        mPrefs.edit().clear().apply()
    }
}