    package com.example.mymvvmroomdemo.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat


object ResourceUtils {
    private var mContext: Context? = null
    fun setContext(context: Context) {
        mContext = context
    }

    fun getColor(color: Int): Int {
        return ResourcesCompat.getColor(mContext?.resources!!, color, null)
    }

    fun getString(stringId: Int): String {
        return mContext?.resources?.getString(stringId) ?: ""
    }


    fun getDrawable(drawable: Int): Drawable {
        return ResourcesCompat.getDrawable(mContext?.resources!!, drawable, null)!!
    }

}