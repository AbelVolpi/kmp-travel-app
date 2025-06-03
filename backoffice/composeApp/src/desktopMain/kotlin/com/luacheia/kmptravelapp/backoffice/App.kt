package com.luacheia.kmptravelapp.backoffice

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.luacheia.kmptravelapp.backoffice.components.BoxBackGround
import com.luacheia.kmptravelapp.backoffice.sections.accommodations.AccommodationsSectionUI
import com.luacheia.kmptravelapp.backoffice.sections.categoriesplaces.CategoriesAndPlacesSectionUI
import com.luacheia.kmptravelapp.backoffice.sections.core.Section
import com.luacheia.kmptravelapp.backoffice.sections.core.SideNavigationBar
import com.luacheia.kmptravelapp.backoffice.sections.guidelines.GuidelinesSectionUI
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var currentSection: Section by remember { mutableStateOf(Section.CategoriesAndPlaces) }

        Row(modifier = Modifier.fillMaxSize()) {
            SideNavigationBar(
                currentSection = currentSection,
                onScreenSelected = { currentSection = it }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                BoxBackGround()
                when (currentSection) {
                    is Section.CategoriesAndPlaces -> CategoriesAndPlacesSectionUI()
                    is Section.Guidelines -> GuidelinesSectionUI()
                    is Section.Accommodations -> AccommodationsSectionUI()
                }
            }
        }
    }
}