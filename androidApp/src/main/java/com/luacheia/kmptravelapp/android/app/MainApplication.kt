package com.luacheia.kmptravelapp.android.app

import android.app.Application
import com.luacheia.kmptravelapp.android.di.androidModule
import com.luacheia.kmptravelapp.di.appModule
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
