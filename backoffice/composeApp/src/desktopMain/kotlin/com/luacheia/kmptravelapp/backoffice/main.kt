package com.luacheia.kmptravelapp.backoffice

import android.app.Application
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.google.firebase.FirebaseOptions
import com.google.firebase.FirebasePlatform
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.luacheia.kmptravelapp.backoffice.sections.categoriesplaces.CategoriesViewModel
import com.luacheia.kmptravelapp.di.appModule
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import java.io.File


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

val desktopModule = module {

    viewModel { CategoriesAndPlacesViewModel(get(),get()) }
}