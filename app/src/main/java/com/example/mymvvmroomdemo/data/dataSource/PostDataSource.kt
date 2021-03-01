package com.example.mymvvmroomdemo.data.dataSource

import androidx.paging.PagingSource
import com.example.mymvvmroomdemo.data.APIService
import com.mindorks.example.paging3.data.response.Data
import com.mindorks.example.paging3.data.response.MyDAO

class PostDataSource(private val apiService: APIService, private val myDAO: MyDAO) :
    PagingSource<Int, Data>() {

    companion object {
        @Volatile
        private var instance: PostDataSource? = null

        fun getInstance(apiService: APIService, myDAO: MyDAO): PostDataSource {
            return instance ?: synchronized(this) {
                instance ?: PostDataSource(apiService, myDAO)
                    .also { instance = it }
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = apiService.getListData(currentLoadingPageKey)
            val responseData = mutableListOf<Data>()
            val data = response.body()?.myData ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )

            if (responseData.isNotEmpty()) {
                myDAO.insertAll(responseData)
            }


        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


}