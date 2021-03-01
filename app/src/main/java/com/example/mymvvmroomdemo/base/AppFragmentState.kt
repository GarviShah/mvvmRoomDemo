package com.example.mymvvmroomdemo.base

import com.example.mymvvmroomdemo.appview.login.LoginFragment
import com.example.mymvvmroomdemo.view.main.ui.home.HomeFragment
import com.example.mymvvmroomdemo.view.main.ui.map.MapsFragment
import com.example.mymvvmroomdemo.view.main.ui.profile.ProfileFragment


enum class AppFragmentState(var fragment: Class<out AppFragment>) {

    F_LOGIN(LoginFragment::class.java),
    F_HOME(HomeFragment::class.java),
    F_MAP(MapsFragment::class.java),
    F_PROFILE(ProfileFragment::class.java);


    companion object {

        // To get AppFragmentState  enum from class name
        fun getValue(value: Class<*>): AppFragmentState {
            return AppFragmentState.values().firstOrNull { it.fragment == value }
                ?: AppFragmentState.F_LOGIN// not found
        }
    }

}
