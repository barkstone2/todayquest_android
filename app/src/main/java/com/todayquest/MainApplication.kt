package com.todayquest

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        APPLICATION_CONTEXT = applicationContext
    }

    companion object {
        private lateinit var APPLICATION_CONTEXT: Context

        fun getContext(): Context {
            return APPLICATION_CONTEXT
        }
    }

}