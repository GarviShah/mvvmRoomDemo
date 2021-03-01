package com.example.mymvvmroomdemo.appview.login.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mymvvmroomdemo.interfaces.CommonCallback
import com.example.mymvvmroomdemo.view.login.model.LoginModel

class LoginViewModel(private val listener: CommonCallback) : ViewModel() {

    private val loginModel: LoginModel

    init {
        this.loginModel = LoginModel("", "")
    }

    //Create function to set email after user finish enter text
    val emailTextWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                loginModel.setEmail(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        }

    //Create function to set password after user finish enter text
    val passwordTextWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                loginModel.setPassword(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        }

    //Create function to preocess login button click

    fun onLoginClicked(v: View) {

        //listener.onSuccess("Success")
        if (loginModel.isDataValid) {

            listener.onSuccess("Login successfully")

        } else
            listener.onFailure("Please check email and password")
    }
}