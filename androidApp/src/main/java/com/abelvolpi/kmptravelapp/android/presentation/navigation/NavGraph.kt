package com.abelvolpi.kmptravelapp.android.presentation.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.abelvolpi.kmptravelapp.android.R
import com.abelvolpi.kmptravelapp.android.app.utils.checkAppIsInstalled
import com.abelvolpi.kmptravelapp.android.app.utils.showToast
import com.abelvolpi.kmptravelapp.android.presentation.navigation.Constants.AppPackages.MAPS_PACKAGE
import com.abelvolpi.kmptravelapp.android.presentation.navigation.Constants.AppPackages.WHATSAPP_PACKAGE
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
import java.net.URLEncoder

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
            val context = LocalContext.current

            PlaceScreen(
                placeId = placeId,
                onBackButtonClicked = {
                    navController.popBackStack()
                },
                onTraceRouteClicked = { address ->
                    if (context.checkAppIsInstalled(MAPS_PACKAGE)) {
                        openMaps(context, address)
                    } else {
                        context.showToast("Instale o aplicativo do Maps")
                    }
                }
            )
        }
    }
    navigation(startDestination = Screen.Chalet.route, route = Section.Chalet.route) {
        composable(Screen.Chalet.route) {
            val context = LocalContext.current

            ChaletScreen(
                onWhatsAppInfoClicked = { number, message ->
                    if (context.checkAppIsInstalled(WHATSAPP_PACKAGE)) {
                        openWhatsApp(context, message, number)
                    } else {
                        context.showToast("Instale o Whatsapp")
                    }
                },
                onAccommodationsClicked = {
                    navController.navigate(Screen.Accommodation.route)
                },
                onGuidanceClicked = { guidanceId ->
                    navController.navigate(
                        route = "${Screen.Guidance.route}/$guidanceId"
                    )
                }
            )
        }
        composable(Screen.Accommodation.route) {
            val context = LocalContext.current

            AccommodationScreen(
                onAccommodationClicked = { link ->
                    openAccommodation(context, link)
                },
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "${Screen.Guidance.route}/{$GUIDANCE_ID}",
            arguments = listOf(
                navArgument(GUIDANCE_ID) { type = NavType.StringType }
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

private fun openWhatsApp(context: Context, message: String, number: String) {
    val encodedMessage = URLEncoder.encode(message, "UTF-8")
    val url = "https://api.whatsapp.com/send?phone=${number}&text=${encodedMessage}"
    val uri = Uri.parse(url)

    val mapsIntent = Intent(Intent.ACTION_VIEW).apply {
        setPackage(WHATSAPP_PACKAGE)
        setData(uri)
    }
    context.startActivity(mapsIntent)
}

private fun openMaps(context: Context, address: String) {
    val uri = Uri.parse("google.navigation:q=$address")
    val mapsIntent = Intent(Intent.ACTION_VIEW).apply {
        setPackage(MAPS_PACKAGE)
        setData(uri)
    }
    context.startActivity(mapsIntent)
}

private fun openAccommodation(context: Context, link: String) {
    val uri = Uri.parse(link)
    val mapsIntent = Intent(Intent.ACTION_VIEW).apply {
        setData(uri)
    }
    context.startActivity(mapsIntent)
}

sealed class Section(
    val route: String,
    @DrawableRes val navigationIcon: Int
) {
    data object Explore : Section("explore-section", R.drawable.explore_icon)
    data object Chalet : Section("chalet-section", R.drawable.chalet_icon)
}

sealed class Screen(val route: String) {
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

    object AppPackages {
        const val WHATSAPP_PACKAGE = "com.whatsapp"
        const val MAPS_PACKAGE = "com.google.android.apps.maps"
    }
}
