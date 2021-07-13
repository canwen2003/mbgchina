package com.mbg.module.common.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

// At the top level of your kotlin file:
val Context.defaultDataStore: DataStore<Preferences> by preferencesDataStore(name = "default")
val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val Context.mainDataStore: DataStore<Preferences> by preferencesDataStore(name = "main")
val Context.otherDataStore: DataStore<Preferences> by preferencesDataStore(name = "other")


class AppDataStore {
    private lateinit var mContext: Context

    fun init(context: Context){
        mContext=context.applicationContext
    }

    fun getDefault():DataStore<Preferences>{
        return mContext.defaultDataStore
    }

    fun getSetting():DataStore<Preferences>{
        return mContext.defaultDataStore
    }

    fun getMain():DataStore<Preferences>{
        return mContext.defaultDataStore
    }

    fun getOther():DataStore<Preferences>{
        return mContext.defaultDataStore
    }

    companion object{
        private val appDataStore:AppDataStore by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            AppDataStore()
        }

        @JvmStatic
        fun get()= appDataStore
    }
}