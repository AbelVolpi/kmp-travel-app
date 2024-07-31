package com.abelvolpi.kmptravelapp.android.presentation.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.abelvolpi.kmptravelapp.android.presentation.navigation.navGraph
import com.abelvolpi.kmptravelapp.android.presentation.navigation.Section
import com.abelvolpi.kmptravelapp.android.presentation.theme.backgroundColor

@Composable
fun HomeScreen() {
    val items = listOf(Section.Explore, Section.Chalet)
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(items, navController)
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Section.Explore.route,
            Modifier.padding(innerPadding)
        ) {
            navGraph(navController)
        }
    }
}

@Composable
fun BottomNavigationBar(
    sections: List<Section>,
    navController: NavController
) {
    BottomNavigation(
        backgroundColor = backgroundColor,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        sections.forEach { section ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = section.navigationIcon),
                        contentDescription = "Localized description",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White,
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == section.route } == true,
                onClick = {
                    navController.navigate(section.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

