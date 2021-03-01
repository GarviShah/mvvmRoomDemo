package com.example.mymvvmroomdemo.view.login.model

import android.text.TextUtils
import android.util.Patterns
import androidx.databinding.BaseObservable

class LoginModel(private var email :String, private var password:String) : BaseObservable() {


    val isDataValid : Boolean
    get() = (!TextUtils.isEmpty(getEmail()))
            && Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()
            && getPassword().length>5

    fun setEmail(email: String){
        this.email = email
    }

    fun setPassword(password: String){
        this.password = password
    }

    fun getEmail(): String{
        return email
    }

    fun getPassword():String{
        return password
    }

}