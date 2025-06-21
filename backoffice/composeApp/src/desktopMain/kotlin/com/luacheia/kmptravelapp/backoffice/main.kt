package com.luacheia.kmptravelapp.backoffice

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.luacheia.kmptravelapp.backoffice.di.desktopModule
import com.luacheia.kmptravelapp.di.appModule
import org.koin.core.context.startKoin


fun main() {
    NetworkInitializer.initFirebase()
    startKoin {
        printLogger()
        modules(appModule, desktopModule)
    }
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Lua Cheia - Backoffice",
            state = WindowState(placement = WindowPlacement.Maximized)
        ) {
            App()
        }
    }
}
