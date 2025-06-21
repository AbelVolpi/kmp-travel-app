package com.luacheia.kmptravelapp.backoffice.ui.sections.accommodations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luacheia.kmptravelapp.backoffice.ui.sections.accommodations.accommodationdetail.AccommodationDetailScreen
import com.luacheia.kmptravelapp.backoffice.ui.sections.accommodations.addaccommodation.AddAccommodationScreen
import com.luacheia.kmptravelapp.backoffice.ui.sections.accommodations.home.AccommodationsListScreen

@Composable
fun AccommodationsNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "accommodationsList") {
        composable("accommodationsList") {
            AccommodationsListScreen(
                onAccommodationClicked = { id ->
                    navController.navigate("accommodationDetail/$id")
                },
                onAddAccommodationClicked = {
                    navController.navigate("addAccommodation")
                }
            )
        }
        composable("accommodationDetail/{accommodationId}") { backStackEntry ->
            val accommodationId = backStackEntry.arguments?.getString("accommodationId") ?: return@composable
            AccommodationDetailScreen(
                accommodationId = accommodationId,
                onClose = { navController.popBackStack() }
            )
        }
        composable("addAccommodation") {
            AddAccommodationScreen(
                onDismiss = { navController.popBackStack() }
            )
        }
    }
}
