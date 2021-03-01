package com.example.mymvvmroomdemo.view.main.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.mymvvmroomdemo.data.APIService
import com.example.mymvvmroomdemo.data.dataSource.PostDataSource
import com.mindorks.example.paging3.data.response.Data
import com.mindorks.example.paging3.data.response.MyDAO
import kotlinx.coroutines.launch


class HomeViewModel(private val apiService: APIService, private val myDAO: MyDAO) : ViewModel() {

    val listData = Pager(PagingConfig(pageSize = 6)) {
        PostDataSource(apiService, myDAO)
    }.flow.cachedIn(viewModelScope)

    fun getAllUsers() : LiveData<List<Data>> {
        return myDAO.getAllUser()
    }


}