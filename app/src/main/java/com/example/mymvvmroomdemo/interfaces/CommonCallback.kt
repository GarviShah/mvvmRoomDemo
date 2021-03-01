package com.example.mymvvmroomdemo.interfaces

import android.view.View
import org.json.JSONObject

interface CommonCallback {

    public fun onSuccessWithData(jsonObject: JSONObject)
    public fun onSuccess(message: String)
    public fun onFailure(message : String)
}