package com.example.mymvvmroomdemo.view.main.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymvvmroomdemo.data.APIService
import com.mindorks.example.paging3.data.response.MyDAO

class HomeViewModelFactory(private val apiService: APIService, private val myDAO: MyDAO) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(apiService, myDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}