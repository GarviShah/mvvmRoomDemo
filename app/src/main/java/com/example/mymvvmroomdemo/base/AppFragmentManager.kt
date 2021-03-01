package com.example.mymvvmroomdemo.base

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.mymvvmroomdemo.R
import com.example.mymvvmroomdemo.utils.KeyboardUtils
import com.example.mymvvmroomdemo.base.AppFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*

// Handling of fragment switch , adding fragment to stack or removing fragment from stack, setting top bar data

class AppFragmentManager(private val activity: AppActivity, private val containerId: Int) {

    private val TAG = "SwitchFragment"
    private val fragmentManager: FragmentManager = activity.supportFragmentManager
    private var ft: FragmentTransaction? = null

    private val stack = Stack<Fragment>()
    fun getStackSize() = stack.size

    // Common Handling of top bar for all fragments like header name, icon on top bar in case of moving to other fragment and coming back again
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    fun <T> setUp(currentState: AppFragmentState, keys: T?) {

        when (currentState) {

            AppFragmentState.F_LOGIN -> {
                /*activity.supportActionBar!!.setTitle("")
                activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                activity.supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)*/
            }


            AppFragmentState.F_HOME -> {
                activity.supportActionBar!!.setTitle(R.string.title_home)
                activity.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                activity.supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(false)
            }

            AppFragmentState.F_MAP -> {
                activity.supportActionBar!!.setTitle(R.string.map)
                activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                activity.supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
            }

            AppFragmentState.F_PROFILE -> {
                activity.supportActionBar!!.setTitle(R.string.profile)
                activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                activity.supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
            }
        }
    }

    // called when fragment backpressed
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    internal fun notifyFragment(isAnimation: Boolean) {
        if (stack.size > 1) {
            popFragment(isAnimation)
        } else {
            this.activity.finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun <T> replaceWithCurrentFragment(
        fragmentEnum: AppFragmentState, keys: T?, isAnimation: Boolean
    ) {
        ft = fragmentManager.beginTransaction()
        if (isAnimation) {
            ft!!.setCustomAnimations(
                R.anim.fragment_fade_enter, R.anim.fragment_fade_exit
            )
        }
        val fragment = Fragment.instantiate(this.activity, fragmentEnum.fragment.name)
        if (keys != null && keys is Bundle) {
            fragment.arguments = keys
        }
        ft!!.add(containerId, fragment, fragmentEnum.fragment.name)

        if (!stack.isEmpty()) {
            stack.lastElement().onPause()
            stack.remove(stack.lastElement())
        }
        stack.push(fragment)
        ft!!.commitAllowingStateLoss()
        setUp(fragmentEnum, keys)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @Deprecated("Not providing proper solution", ReplaceWith("replaceWithCurrentFragment", ""))
    fun <T> replaceFragment(fragmentEnum: AppFragmentState, keys: T?, isAnimation: Boolean) {
        ft = fragmentManager.beginTransaction()
        for (i in 0..stack.size) {
            if (!stack.isEmpty()) {
                stack.lastElement().onPause()
                ft!!.remove(stack.pop())
            }
        }
        if (isAnimation) {
            ft!!.setCustomAnimations(
                R.anim.fragment_fade_enter, R.anim.fragment_fade_exit
            )
        }
        val fragment = Fragment.instantiate(this.activity, fragmentEnum.fragment.name)

        if (keys != null && keys is Bundle) {
            fragment.arguments = keys
        }

        ft!!.add(containerId, fragment, fragmentEnum.fragment.name)
        if (!stack.isEmpty()) {
            stack.lastElement().onPause()
            ft!!.hide(stack.lastElement())
        }
        stack.push(fragment)
        //        ft.commit();
        ft!!.commitAllowingStateLoss()
        setUp(fragmentEnum, keys)
    }


    // Call For Fragment Switch
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun <T> addFragmentInStack(
        fragmentEnum: AppFragmentState, keys: T?, isAnimation: Boolean
    ) {
        ft = fragmentManager.beginTransaction()
        if (isAnimation) {
            ft!!.setCustomAnimations(
                R.anim.fragment_fade_enter, R.anim.fragment_fade_exit
            )
        }
        val fragment = Fragment.instantiate(this.activity, fragmentEnum.fragment.name)
        if (keys != null && keys is Bundle) {
            fragment.arguments = keys
        }
        ft!!.add(containerId, fragment, fragmentEnum.fragment.name)
        if (!stack.isEmpty()) {
            stack.lastElement().onPause()
            ft!!.hide(stack.lastElement())
        }
        stack.push(fragment)
        ft!!.commitAllowingStateLoss()
        setUp(fragmentEnum, keys)
    }

    // When to resume last fragment and to pop only one fragment
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun popFragment(isAnimation: Boolean) {
        ft = fragmentManager.beginTransaction()

        if (isAnimation) {
            ft!!.setCustomAnimations(
                R.anim.fragment_fade_enter, R.anim.fragment_fade_exit
            )
        }
        stack.lastElement().onPause()
        ft!!.remove(stack.pop())
        stack.lastElement().onResume()
        ft!!.show(stack.lastElement())
        ft!!.commit()
        setUp<Any>(AppFragmentState.getValue(stack.lastElement().javaClass), null)
    }

    // When not to resume last fragment
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun popFragment(numberOfFragment: Int) {
        val fragmentManager = activity.supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.setCustomAnimations(
            R.anim.fragment_fade_enter, R.anim.fragment_fade_exit
        )
        for (i in 0 until numberOfFragment) {
            if (!stack.isEmpty()) {
                stack.lastElement().onPause()
                ft.remove(stack.pop())
                //                fragmentStack.lastElement().onResume();
            }
        }
        if (!stack.isEmpty()) ft.show(stack.lastElement())
        ft.commit()
        setUp<Any>(AppFragmentState.getValue(stack.lastElement().javaClass), null)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun popAddedFragment() {
        if (stack.size > 1) {
            popFragment(stack.size - 1)
        }
    }

    // When not to resume last fragment
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun <T> popFragment(numberOfFragment: Int, appFragmentState: AppFragmentState, bundle: T) {
        ft = fragmentManager.beginTransaction()
        ft!!.setCustomAnimations(
            R.anim.fragment_fade_enter, R.anim.fragment_fade_exit
        )
        for (i in 0 until numberOfFragment) {
            if (!stack.isEmpty()) {
                stack.lastElement().onPause()
                ft!!.remove(stack.pop())
            }
        }
        val fragment = stack.lastElement()
        passDataBetweenFragment(appFragmentState, bundle)
        if (!stack.isEmpty()) ft!!.show(fragment)
        ft!!.commit()
        setUp<Any>(AppFragmentState.getValue(stack.lastElement().javaClass), null)
    }

    // To bring already fragment in stack to top
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun <T> moveFragmentToTop(
        appFragmentState: AppFragmentState, `object`: T, isAnimation: Boolean
    ) {
        ft = fragmentManager.beginTransaction()
        if (isAnimation) {
            ft!!.setCustomAnimations(
                R.anim.fragment_fade_enter, R.anim.fragment_fade_exit
            )
        }
        val fragment = getFragment(appFragmentState)
        // passDataBetweenFragment(appFragmentState, `object`)
        val position = stack.indexOf(fragment)
        if (position > -1) {
            stack.removeAt(position)
            if (!stack.isEmpty()) {
                stack.lastElement().onPause()
                ft!!.hide(stack.lastElement())
            }
            stack.push(fragment)
            if (!stack.isEmpty()) {
                stack.lastElement().onResume()
                ft!!.show(stack.lastElement())
            }
            ft!!.commit()
        }
        setUp<Any>(appFragmentState, null)
    }

    fun <T> passDataBetweenFragment(appFragmentState: AppFragmentState, bundle: T) {
        val fragment = getFragment(appFragmentState) as AppFragment
        if (bundle != null) {
//            fragment?.switchData(bundle)
        }
        //        switch (appFragmentState) {
        //            case F_ONE:
        //                break;
        //            case F_TWO:
        //                if (fragment instanceof TwoFragment) {
        //                    ((TwoFragment) fragment).onPassingBundle(bundle);
        //                }
        //                break;
        //            case F_THREE:
        //                break;
        //            case F_FOUR:
        //                if (fragment instanceof FourFragment) {
        //                    ((FourFragment) fragment).onPassingBundle(bundle);
        //                }
        //                break;
        //        }
    }

    fun getFragment(appFragmentState: AppFragmentState): Fragment? {
        return fragmentManager.findFragmentByTag(appFragmentState.fragment.name)
    }

    fun getFragment(): Fragment? {
        return fragmentManager.findFragmentById(containerId)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun <T> addFragment(fragmentEnum: AppFragmentState, keys: Bundle?, isAnimation: Boolean) {
        KeyboardUtils.hideKeyboard(activity)
        val availableFragment = getFragment(fragmentEnum)
        if (availableFragment != null) {
            moveFragmentToTop(fragmentEnum, keys, isAnimation)
        } else {
            addFragmentInStack(fragmentEnum, keys, isAnimation)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun <T> addFragmentAlwasNew(
        fragmentEnum: AppFragmentState, keys: Bundle?, isAnimation: Boolean
    ) {
        KeyboardUtils.hideKeyboard(activity)
        addFragmentInStack(fragmentEnum, keys, isAnimation)
    }

    private fun internetConnectionErrorFragmentAdd(
        fragmentEnum: AppFragmentState, keys: Bundle?, isAnimation: Boolean
    ) {
        val bundle = Bundle()
//        bundle.putSerializable(Constants.FRAGMENT_ENUM, fragmentEnum)
//        bundle.putBundle(Constants.BUNDLE, keys)
//        bundle.putBoolean(Constants.ANIMATION, isAnimation)
//        val availableFragment = getFragment(AppFragmentState.F_NO_INTERNET)
//        if (availableFragment != null) {
//            moveFragmentToTop(AppFragmentState.F_NO_INTERNET, bundle, false)
//        } else {
//            addFragmentInStack<Any>(AppFragmentState.F_NO_INTERNET, bundle, false)
//        }
    }

    fun clearAllFragment() {
        val supportFragmentManager = activity.supportFragmentManager
        for (entry in 0 until supportFragmentManager.backStackEntryCount) {
            val tag = supportFragmentManager.getBackStackEntryAt(entry).name
            val showFragment = activity.supportFragmentManager.findFragmentByTag(tag)
            if (showFragment != null && entry != 0) {
                showFragment.userVisibleHint = false
            }
        }
        activity.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}
