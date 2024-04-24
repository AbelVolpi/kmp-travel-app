package com.abelvolpi.kmptravelapp.android.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.abelvolpi.kmptravelapp.android.R
import com.abelvolpi.kmptravelapp.android.presentation.theme.MyApplicationTheme
import com.abelvolpi.kmptravelapp.android.presentation.theme.backgroundColor
import com.abelvolpi.kmptravelapp.android.presentation.ui.chalet.ChaletScreen
import com.abelvolpi.kmptravelapp.android.presentation.ui.explore.ExploreScreen
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
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = backgroundColor,
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.navigationIcon),
                                contentDescription = "Localized description",
                                modifier = Modifier.size(30.dp),
                                tint = Color.White,
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Explore.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Explore.route) { ExploreScreen() }
            composable(Screen.Chalet.route) { ChaletScreen() }
        }
    }
}

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val navigationIcon: Int,
) {
    object Explore : Screen("profile", R.string.explore, R.drawable.explore_icon)
    object Chalet : Screen("chalet", R.string.chalet, R.drawable.chalet_icon)
}

val items = listOf(
    Screen.Explore,
    Screen.Chalet,
)