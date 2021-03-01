package com.example.mymvvmroomdemo.appview.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.mymvvmroomdemo.R
import com.example.mymvvmroomdemo.base.AppFragment
import com.example.mymvvmroomdemo.databinding.FragmentLoginBinding
import com.example.mymvvmroomdemo.interfaces.CommonCallback
import com.example.mymvvmroomdemo.appview.login.viewmodel.LoginViewModel
import com.example.mymvvmroomdemo.appview.login.viewmodel.LoginViewModelFactory
import com.example.mymvvmroomdemo.utils.pref.PreferencesHelper
import com.example.mymvvmroomdemo.view.MainActivity
import org.json.JSONObject

class LoginFragment : AppFragment(), CommonCallback {

    lateinit var preferencesHelper: PreferencesHelper

    override fun initializeComponent(view: View?) {

    }

    override fun pageVisible() {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activityLoginBinding = DataBindingUtil.inflate<FragmentLoginBinding>(
            LayoutInflater.from(container!!.context),
            R.layout.fragment_login,
            container,
            false
        )
        activityLoginBinding.myModel =
            ViewModelProviders.of(this, LoginViewModelFactory(this)).get(LoginViewModel::class.java)


        return activityLoginBinding.root
    }


    override fun onSuccessWithData(jsonObject: JSONObject) {
        this.activity?.finish()
        val sharedPreferences =
            this.context?.getSharedPreferences("production", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putBoolean("IS_LOGIN", true)
            ?.putString("LOGIN_OBJECT", jsonObject.toString())
            ?.apply()

        startActivity(Intent(context, MainActivity::class.java))
    }

    override fun onSuccess(message: String) {
        this.activity?.finish()
        val sharedPreferences =
            this.context?.getSharedPreferences("production", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putBoolean("IS_LOGIN", true)?.apply()

        startActivity(Intent(context, MainActivity::class.java))    }

    override fun onFailure(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}