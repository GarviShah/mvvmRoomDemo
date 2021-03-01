package com.example.mymvvmroomdemo.utils.baseinrerface

interface ConnectionBridge {

    fun isNetworkAvailable(): Boolean

    fun checkNetworkAvailableWithError(): Boolean
}