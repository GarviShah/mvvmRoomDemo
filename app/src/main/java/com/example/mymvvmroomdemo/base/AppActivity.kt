package com.example.mymvvmroomdemo.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mymvvmroomdemo.MyMVVMRoomApplication
import com.example.mymvvmroomdemo.R
import com.example.mymvvmroomdemo.utils.Constants
import com.example.mymvvmroomdemo.utils.ToastUtils
import com.example.mymvvmroomdemo.utils.baseinrerface.ConnectionBridge
import com.example.mymvvmroomdemo.utils.broadcasts.ConnectivityUtils
import com.example.mymvvmroomdemo.utils.broadcasts.NetworkChangeReceiver
import com.google.android.material.snackbar.Snackbar
import android.view.MenuItem as MenuItem1


abstract class AppActivity : AppCompatActivity(), ConnectionBridge {

    private lateinit var localBroadcastManager: androidx.localbroadcastmanager.content.LocalBroadcastManager
    private var networkBroadcastReceiver: NetworkBroadcastReceiver? = null

    private var mSnackbar: Snackbar? = null

    protected abstract fun defineLayoutResource(): Int //Activity's layout can be declare here
    protected abstract fun initializeComponents() //Activity's components initialization here
    abstract fun trackScreen()

    var appFragmentManager: AppFragmentManager? = null
        private set


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(defineLayoutResource())
        createAppFragmentManager(this, R.id.activity_main_container)
        super.onCreate(savedInstanceState)
        initializeComponents()
        trackScreen()
        initFields()
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams

    }

    fun createAppFragmentManager(activity: AppActivity, container: Int) {
        appFragmentManager = AppFragmentManager(
            activity = activity,
            containerId = container
        )
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBackPressed() {
        appFragmentManager!!.notifyFragment(true)
    }

    private val mFinishAppReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onReceive(context: Context, intent: Intent) {
            finishAndRemoveTask()
        }
    }

    private inner class NetworkBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
//            val activeConnection = intent
//                .getBooleanExtra(NetworkChangeReceiver.EXTRA_IS_ACTIVE_CONNECTION, false)
//            if (activeConnection) {
//                checkShowingConnectionError()
//            } else {
//                checkShowingConnectionError()
//            }
        }
    }

    override fun checkNetworkAvailableWithError(): Boolean {
        return if (!isNetworkAvailable()) {
            ToastUtils.showToast(getString(R.string.error_message_network))
            false
        } else {
            true
        }
    }

    override fun isNetworkAvailable(): Boolean {
        return ConnectivityUtils.isNetworkAvailable(this)
    }

    protected fun checkShowingConnectionError() {
//        if (!isNetworkAvailable()) {
//            showStyledSankBar()
//        } else {
//            hideSnackBar()
//        }
    }


    private fun showStyledSankBar() {
        val rootView = window.decorView.rootView
        if (rootView != null) {
            mSnackbar =
                Snackbar.make(rootView, R.string.error_message_network, Snackbar.LENGTH_INDEFINITE)
            mSnackbar!!.setActionTextColor(Color.WHITE)
            mSnackbar!!.setAction("Retry") {
                // retry to send email here
                if (!isNetworkAvailable()) {
                    showStyledSankBar()
                } else {
                    hideSnackBar()
                }
            }

            val snackBarView = mSnackbar!!.view
            val snackBarTextId = com.google.android.material.R.id.snackbar_text
            val textView = snackBarView.findViewById<View>(snackBarTextId) as TextView
            textView.setTextColor(Color.WHITE)
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    MyMVVMRoomApplication.instance!!,
                    R.color.colorAccent
                )
            )
            mSnackbar!!.show()
            // recursively call this method again when the snackbar was dismissed through a swipe
            mSnackbar!!.addCallback(object : Snackbar.Callback() {
                override fun onDismissed(snackbar: Snackbar?, event: Int) {
                    if (event == Snackbar.Callback.DISMISS_EVENT_SWIPE) showStyledSankBar()
                }
            })
        } else {

        }
    }


    private fun hideSnackBar() {
        if (mSnackbar != null) {
            mSnackbar!!.dismiss()
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterBroadcastReceivers()
    }

    override fun onResume() {
        super.onResume()
        registerBroadcastReceivers()
        checkShowingConnectionError()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onOptionsItemSelected(item: MenuItem1): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                /*this.appFragmentManager?.addFragment<Any>(
                    AppFragmentState.F_MESSAGES, null, false
                )*/
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity, menu)
        return true
    }*/

    private fun initFields() {
        networkBroadcastReceiver = NetworkBroadcastReceiver()
        localBroadcastManager =
            androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(this)

    }

    private fun registerBroadcastReceivers() {
        registerReceiver(mFinishAppReceiver, IntentFilter(Constants.EXIT_APP_STATUS_PREF_KEY))
        localBroadcastManager.registerReceiver(
            networkBroadcastReceiver!!,
            IntentFilter(NetworkChangeReceiver.ACTION_LOCAL_CONNECTIVITY)
        )
    }

    private fun unregisterBroadcastReceivers() {
        localBroadcastManager.unregisterReceiver(networkBroadcastReceiver!!)
    }



    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mFinishAppReceiver)
    }

}
