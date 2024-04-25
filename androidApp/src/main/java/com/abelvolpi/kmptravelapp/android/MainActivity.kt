package com.abelvolpi.kmptravelapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abelvolpi.kmptravelapp.data.model.Place
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.initialize(this)
        setContent {
            App()
        }
    }
}

@Composable
fun App(mainViewModel: MainViewModel = koinViewModel()) {
    MyApplicationTheme {
        val places by mainViewModel.greetingList.collectAsStateWithLifecycle()
        GreetingView(places = places)
    }
}

@Composable
fun GreetingView(places: List<Place>) {
    Column(
        modifier = Modifier.padding(all = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        places.forEach { category ->
            Text(category.toString())
            Divider()
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
//        GreetingView(
//           // listOf("a", "b")
//        )
    }
}
