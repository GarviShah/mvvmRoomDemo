package com.example.mymvvmroomdemo.view

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.WindowManager
import com.example.mymvvmroomdemo.R
import com.example.mymvvmroomdemo.appview.login.LoginActivity
import com.example.mymvvmroomdemo.base.AppActivity

class SplashActivity : AppActivity() {

    override fun defineLayoutResource(): Int {
        return R.layout.activity_splash
    }

    override fun initializeComponents() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        navigateToLoginScreen()
    }

    override fun trackScreen() {
    }

    private fun navigateToLoginScreen() {

        Handler().postDelayed({

            val isLogin = this.getSharedPreferences("production", Context.MODE_PRIVATE)
                .getBoolean("IS_LOGIN", false)

            if (!isLogin) {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }

        }, 2000)

    }
}