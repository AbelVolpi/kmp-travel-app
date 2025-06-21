package com.luacheia.kmptravelapp.backoffice.ui.sections.guidances

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luacheia.kmptravelapp.backoffice.ui.sections.guidances.GuidancesListScreen
import com.luacheia.kmptravelapp.backoffice.ui.sections.guidances.GuidanceDetailScreen
import com.luacheia.kmptravelapp.backoffice.ui.sections.guidances.AddGuidanceScreen

@Composable
fun GuidelinesSectionNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "guidancesList") {
        composable("guidancesList") {
            GuidancesListScreen(
                onGuidanceClicked = { id ->
                    navController.navigate("guidanceDetail/$id")
                },
                onAddGuidanceClicked = {
                    navController.navigate("addGuidance")
                }
            )
        }
        composable("guidanceDetail/{guidanceId}") { backStackEntry ->
            val guidanceId = backStackEntry.arguments?.getString("guidanceId") ?: return@composable
            GuidanceDetailScreen(
                guidanceId = guidanceId,
                onClose = { navController.popBackStack() }
            )
        }
        composable("addGuidance") {
            AddGuidanceScreen(
                onDismiss = { navController.popBackStack() }
            )
        }
    }
}
