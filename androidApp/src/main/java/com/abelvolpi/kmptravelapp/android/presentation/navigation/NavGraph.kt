package com.abelvolpi.kmptravelapp.android.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.abelvolpi.kmptravelapp.android.R
import com.abelvolpi.kmptravelapp.android.presentation.navigation.Constants.Arguments.CATEGORY_ID
import com.abelvolpi.kmptravelapp.android.presentation.navigation.Constants.Arguments.CATEGORY_NAME
import com.abelvolpi.kmptravelapp.android.presentation.navigation.Constants.Arguments.GUIDANCE_ID
import com.abelvolpi.kmptravelapp.android.presentation.navigation.Constants.Arguments.PLACE_ID
import com.abelvolpi.kmptravelapp.android.presentation.ui.accommodation.AccommodationScreen
import com.abelvolpi.kmptravelapp.android.presentation.ui.category.CategoryScreen
import com.abelvolpi.kmptravelapp.android.presentation.ui.chalet.ChaletScreen
import com.abelvolpi.kmptravelapp.android.presentation.ui.explore.ExploreScreen
import com.abelvolpi.kmptravelapp.android.presentation.ui.guidance.GuidanceScreen
import com.abelvolpi.kmptravelapp.android.presentation.ui.place.PlaceScreen

fun NavGraphBuilder.navGraph(navController: NavController) {
    navigation(startDestination = Screen.Explore.route, route = Section.Explore.route) {
        composable(Screen.Explore.route) {
            ExploreScreen(
                onCategoryClicked = { categoryId, categoryName ->
                    navController.navigate("${Screen.Category.route}/$categoryId/$categoryName")
                },
                onPlaceClicked = { placeId ->
                    navController.navigate("${Screen.Place.route}/$placeId")
                }
            )
        }
        composable(
            route = "${Screen.Category.route}/{$CATEGORY_ID}/{$CATEGORY_NAME}",
            arguments = listOf(
                navArgument(CATEGORY_ID) { type = NavType.StringType },
                navArgument(CATEGORY_NAME) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString(CATEGORY_ID) ?: return@composable
            val categoryName =
                backStackEntry.arguments?.getString(CATEGORY_NAME) ?: return@composable
            CategoryScreen(
                categoryId = categoryId,
                categoryName = categoryName,
                onPlaceClicked = { placeId ->
                    navController.navigate("${Screen.Place.route}/$placeId")
                },
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "${Screen.Place.route}/{$PLACE_ID}",
            arguments = listOf(navArgument(PLACE_ID) { type = NavType.StringType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString(PLACE_ID) ?: return@composable
            PlaceScreen(
                placeId = placeId,
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
    navigation(startDestination = Screen.Chalet.route, route = Section.Chalet.route) {
        composable(Screen.Chalet.route) {
            ChaletScreen(
                onWhatsAppInfoClicked = {
                    // TODO()
                },
                onAccommodationsClicked = {
                    navController.navigate(Screen.Accommodation.route)
                },
                onGuidanceClicked = { guidanceId ->
                    navController.navigate(
                        route = "${Screen.Guidance.route}/$guidanceId",
                    )
                }
            )
        }
        composable(Screen.Accommodation.route) {
            AccommodationScreen(
                onAccommodationClicked = {
                    // TODO()
                },
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "${Screen.Guidance.route}/{$GUIDANCE_ID}",
            arguments = listOf(
                navArgument(GUIDANCE_ID) { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val guidanceId = backStackEntry.arguments?.getString(GUIDANCE_ID) ?: return@composable
            GuidanceScreen(
                guidanceId = guidanceId,
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
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

object Constants {
    object Arguments {
        const val PLACE_ID = "placeId"
        const val CATEGORY_ID = "categoryId"
        const val CATEGORY_NAME = "categoryName"
        const val GUIDANCE_ID = "guidanceId"
    }
}