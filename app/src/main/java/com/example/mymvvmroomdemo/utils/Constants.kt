package com.example.mymvvmroomdemo.utils

object Constants {
    const val MAX_CLICK_INTERVAL: Long = 500//Max time interval to prevent double click
    const val EXIT_APP_STATUS_PREF_KEY = "EXIT_APP_STATUS_PREF_KEY"
    const val APP_SHARED_PREFERENCE_NAME: String = "appname_shared_preference"
    const val DATABASE_NAME = "my_database.db"
    const val DATABASE_VERSION = 1

    enum class BundleKey() {
        HANDSET_MODEL,
        COMMUNITY_MODEL
    }
}
