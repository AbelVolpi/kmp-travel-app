package com.abelvolpi.kmptravelapp.android

import android.app.Application
import com.abelvolpi.kmptravelapp.android.di.androidModule
import com.abelvolpi.kmptravelapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(appModule() + androidModule)
        }
    }
}