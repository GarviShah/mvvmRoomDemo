package com.example.mymvvmroomdemo.utils.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.mymvvmroomdemo.MyMVVMRoomApplication

class NetworkChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val activeConnection = ConnectivityUtils.isNetworkAvailable(context)
        notifyAboutChangeConnection(activeConnection)
    }

    private fun notifyAboutChangeConnection(activeConnection: Boolean) {
        val intent = Intent(ACTION_LOCAL_CONNECTIVITY)
        intent.putExtra(EXTRA_IS_ACTIVE_CONNECTION, activeConnection)
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(MyMVVMRoomApplication.instance!!)
            .sendBroadcast(intent)
    }

    companion object {

        const val ACTION_LOCAL_CONNECTIVITY = "action_local_connectivity"
        const val EXTRA_IS_ACTIVE_CONNECTION = "extra_is_active_connection"
    }
}