package com.example.mymvvmroomdemo.utils

import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.example.mymvvmroomdemo.MyMVVMRoomApplication
import com.example.mymvvmroomdemo.R


object ToastUtils {

    fun showToast(stringText : String? = "") {
        val context = MyMVVMRoomApplication.instance!!
        val layout = LayoutInflater.from(context).inflate(R.layout.layout_toast, null, false)
        val text = layout.findViewById(R.id.tToastTitle) as TextView
        text.text = stringText
        val toast = Toast(context)
        toast.setGravity(Gravity.BOTTOM, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }
}