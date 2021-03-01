package com.example.mymvvmroomdemo.appview.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymvvmroomdemo.interfaces.CommonCallback

class LoginViewModelFactory(private val listener: CommonCallback) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(listener) as T
    }
}