package com.qoiu.translator

import android.app.Activity
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TranslatorApp : Application() {


    override fun onCreate() {
     super.onCreate()
        startKoin {
            androidContext(this@TranslatorApp)
            //modules(listOf(application, mainScreen,historyScreen))
           // androidLogger()
        }
    }
}