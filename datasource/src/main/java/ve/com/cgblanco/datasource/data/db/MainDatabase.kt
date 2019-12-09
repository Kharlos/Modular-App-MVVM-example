package com.app.network.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ve.com.cgblanco.datasource.data.model.user.User
import ve.com.cgblanco.datasource.data.model.user.UserDao


/**
 * Created by Alireza Rafeezadeh on 6/12/2019.
 */


@Database(entities = [User::class], version = 1)
abstract class MainDatabase : RoomDatabase() {


    abstract fun userDao(): UserDao


    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getDatabase(context: Context): MainDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "MainDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}
