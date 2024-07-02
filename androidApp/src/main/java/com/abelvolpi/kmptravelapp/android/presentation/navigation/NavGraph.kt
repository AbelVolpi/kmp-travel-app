package com.abelvolpi.kmptravelapp.android.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.abelvolpi.kmptravelapp.android.R
import com.abelvolpi.kmptravelapp.android.presentation.ui.accommodation.AccommodationScreen
import com.abelvolpi.kmptravelapp.android.presentation.ui.category.CategoryScreen
import com.abelvolpi.kmptravelapp.android.presentation.ui.chalet.ChaletScreen
import com.abelvolpi.kmptravelapp.android.presentation.ui.explore.ExploreScreen
import com.abelvolpi.kmptravelapp.android.presentation.ui.guidance.GuidanceScreen
import com.abelvolpi.kmptravelapp.android.presentation.ui.place.PlaceScreen


fun NavGraphBuilder.navGraph() {
    navigation(startDestination = Screen.Explore.route, route = Section.Explore.route) {
        composable(Screen.Explore.route) { ExploreScreen() }
        composable(Screen.Category.route) { CategoryScreen() }
        composable(Screen.Place.route) { PlaceScreen() }
    }
    navigation(startDestination = Screen.Chalet.route, route = Section.Chalet.route) {
        composable(Screen.Chalet.route) { ChaletScreen() }
        composable(Screen.Accommodation.route) { AccommodationScreen() }
        composable(Screen.Guidance.route) { GuidanceScreen() }
    }
}

sealed class Section(
    val route: String,
    @DrawableRes val navigationIcon: Int,
) {
    data object Explore : Section("explore-section", R.drawable.explore_icon)
    data object Chalet : Section("chalet-section", R.drawable.chalet_icon)
}

sealed class Screen(
    val route: String
) {
    data object Explore : Screen("explore")
    data object Category : Screen("category")
    data object Place : Screen("place")
    data object Chalet : Screen("chalet")
    data object Accommodation : Screen("accommodation")
    data object Guidance : Screen("guidance")
}