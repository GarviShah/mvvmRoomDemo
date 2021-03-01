package com.example.mymvvmroomdemo.view

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mymvvmroomdemo.R
import com.example.mymvvmroomdemo.base.AppActivity
import com.example.mymvvmroomdemo.base.AppFragmentState

class MainActivity : AppActivity() {

    lateinit var navView: BottomNavigationView

    override fun defineLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun initializeComponents() {

        navView = findViewById(R.id.nav_view)

        appFragmentManager?.addFragment<Any>(AppFragmentState.F_HOME, null, true)

        navView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    (this as AppActivity).appFragmentManager?.addFragment<Any>(
                        AppFragmentState.F_HOME, null, false
                    )
                    true
                }
                R.id.navigation_map -> {
                    (this as AppActivity).appFragmentManager?.addFragment<Any>(
                        AppFragmentState.F_MAP, null, false
                    )
                    true
                }
                R.id.navigation_profile -> {
                    (this as AppActivity).appFragmentManager?.addFragment<Any>(
                        AppFragmentState.F_PROFILE, null, false
                    )
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun trackScreen() {
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.activity_main_container)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_map, R.id.navigation_profile
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}