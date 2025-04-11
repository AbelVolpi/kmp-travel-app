package com.luacheia.kmptravelapp.android.presentation.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.luacheia.kmptravelapp.android.presentation.navigation.Section
import com.luacheia.kmptravelapp.android.presentation.navigation.navGraph
import com.luacheia.kmptravelapp.android.presentation.theme.backgroundColor
import com.luacheia.kmptravelapp.data.repository.AccommodationRepository
import com.luacheia.kmptravelapp.data.repository.CategoryRepository
import com.luacheia.kmptravelapp.data.repository.GuidanceRepository
import com.luacheia.kmptravelapp.data.repository.InfoRepository
import com.luacheia.kmptravelapp.data.repository.PlaceRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    placeRepository: PlaceRepository,
    categoryRepository: CategoryRepository,
    guidanceRepository: GuidanceRepository,
    accommodationRepository: AccommodationRepository,
    infoRepository: InfoRepository
) {
    val coroutineScope = rememberCoroutineScope()

    coroutineScope.launch {
        val places = async { placeRepository.fetchPlaces() }
        val categories = async { categoryRepository.fetchCategories() }
        val guidelines = async { guidanceRepository.fetchGuidelines() }
        val accommodations = async { accommodationRepository.fetchAccommodations() }
        val infos = async { infoRepository.fetchInfos() }

        val results = listOf(places.await(), categories.await(), guidelines.await(), accommodations.await(), infos.await())

        println("Todas as chamadas concluídas: $results")
    }

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
        backgroundColor = backgroundColor
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
                        tint = Color.White
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
