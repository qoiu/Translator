package com.qoiu.translator

import android.app.Activity
import android.app.Application
import org.koin.core.context.startKoin

class TranslatorApp : Application() {


    override fun onCreate() {
     super.onCreate()
        startKoin {
            modules(listOf(application, mainScreen))
        }
    }
}