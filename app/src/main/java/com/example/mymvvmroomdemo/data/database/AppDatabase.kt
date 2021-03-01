package com.example.mymvvmroomdemo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymvvmroomdemo.utils.Constants.DATABASE_NAME
import com.example.mymvvmroomdemo.utils.Constants.DATABASE_VERSION
import com.mindorks.example.paging3.data.response.Data
import com.mindorks.example.paging3.data.response.MyDAO


@Database(entities = [Data::class], version = DATABASE_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getData(): MyDAO

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, DATABASE_NAME
            ).build()
        }
    }
}