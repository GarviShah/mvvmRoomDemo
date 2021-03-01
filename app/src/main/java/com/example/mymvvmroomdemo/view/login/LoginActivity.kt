package com.example.mymvvmroomdemo.appview.login

import android.os.Build
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.example.mymvvmroomdemo.R
import com.example.mymvvmroomdemo.base.AppActivity
import com.example.mymvvmroomdemo.base.AppFragmentState


class LoginActivity : AppActivity() {
    override fun defineLayoutResource(): Int {
        return R.layout.activity_login
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initializeComponents() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        appFragmentManager?.addFragment<Any>(AppFragmentState.F_LOGIN, null, true)    }

    override fun trackScreen() {
    }


}