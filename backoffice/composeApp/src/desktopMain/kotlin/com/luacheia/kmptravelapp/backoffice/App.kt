package com.luacheia.kmptravelapp.backoffice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.luacheia.kmptravelapp.backoffice.sections.accommodations.AccommodationsSectionUI
import com.luacheia.kmptravelapp.backoffice.sections.auth.AuthWindow
import com.luacheia.kmptravelapp.backoffice.sections.categoriesplaces.CategoriesAndPlacesNavigation
import com.luacheia.kmptravelapp.backoffice.sections.core.Section
import com.luacheia.kmptravelapp.backoffice.sections.core.SideNavigationBar
import com.luacheia.kmptravelapp.backoffice.sections.guidelines.GuidelinesSectionUI
import com.luacheia.kmptravelapp.backoffice.ui.theme.backgroundColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var currentSection: Section by remember { mutableStateOf(Section.CategoriesAndPlaces) }
        var showLogin by remember { mutableStateOf(false) }

        Row(modifier = Modifier.fillMaxSize()) {
            SideNavigationBar(
                currentSection = currentSection,
                onScreenSelected = { currentSection = it }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                TopBar(
                    onLoginClick = { showLogin = true }
                )
                when (currentSection) {
                    is Section.CategoriesAndPlaces -> CategoriesAndPlacesNavigation()
                    is Section.Guidelines -> GuidelinesSectionUI()
                    is Section.Accommodations -> AccommodationsSectionUI()
                }
                if (showLogin) {
                    AuthWindow(
                        onClose = { showLogin = false }
                    )
                }
            }
        }
    }
}

@Composable
fun TopBar(
    onLoginClick: () -> Unit = {},
){
    Box(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
    ) {
        FilledTonalIconButton(
            onClick = onLoginClick,
            modifier = Modifier
                .padding(end = 16.dp)
                .size(20.dp)
                .clip(shape = CircleShape)
                .background(backgroundColor)
                .align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Add"
            )
        }
    }
}