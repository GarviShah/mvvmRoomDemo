package com.example.mymvvmroomdemo.utils.pref


//import com.fctech.customer.data.models.AppUser

interface PreferencesHelper {
    fun isLoggedIn(): Boolean
    fun setIsLoggedIn()
    fun setToken(token: String)
    fun getToken(): String?
    fun setUser(user: String)
    //fun getUser(): AppUser?
    fun clear()
}