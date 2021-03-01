package com.example.mymvvmroomdemo

import android.app.Application
import com.example.mymvvmroomdemo.utils.ResourceUtils


class  MyMVVMRoomApplication: Application() {
   // var apiService: ApiService? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        //apiService = ApiService()
        ResourceUtils.setContext(this)
    }


    override fun onTerminate() {
        super.onTerminate()
        if (instance != null) {
            instance = null
        }
    }

    companion object {
        var instance: MyMVVMRoomApplication? = null
    }

}
