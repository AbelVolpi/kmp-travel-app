package com.abelvolpi.kmptravelapp.android.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.abelvolpi.kmptravelapp.android.presentation.theme.MyApplicationTheme
import com.abelvolpi.kmptravelapp.android.presentation.ui.home.HomeScreen
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        Firebase.initialize(this)
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    MyApplicationTheme {
        HomeScreen()
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        HomeScreen()
    }
}

