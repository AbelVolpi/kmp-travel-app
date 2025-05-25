package com.luacheia.kmptravelapp.backoffice

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luacheia.kmptravelapp.backoffice.components.BoxBackGround
import com.luacheia.kmptravelapp.backoffice.sections.accommodations.AccommodationsSectionUI
import com.luacheia.kmptravelapp.backoffice.sections.categoriesplaces.CategoriesAndPlacesSectionUI
import com.luacheia.kmptravelapp.backoffice.sections.categoriesplaces.CategoriesViewModel
import com.luacheia.kmptravelapp.backoffice.sections.core.Section
import com.luacheia.kmptravelapp.backoffice.sections.core.SideNavigationBar
import com.luacheia.kmptravelapp.backoffice.sections.guidelines.GuidelinesSectionUI
import com.luacheia.kmptravelapp.data.model.Category
import com.luacheia.kmptravelapp.data.model.Place
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.get
import org.koin.core.context.startKoin
import kotlin.math.log

@Composable
@Preview
fun App() {
    val viewModel: CategoriesViewModel = koinViewModel()
    val categoriesRemote by viewModel.placesModel.collectAsState()

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
                    is Section.CategoriesAndPlaces -> {
                        viewModel.getAllPlacesFromRemote()
                        CategoriesAndPlacesSectionUI(categories, places)
                    }
                    is Section.Guidelines -> GuidelinesSectionUI()
                    is Section.Accommodations -> AccommodationsSectionUI()
                }
            }
        }
    }
}

val categories = listOf(
    Category(
        id = "1",
        name = "Praia",
        iconUrl = "https://example.com/icon1.svg"
    ),
    Category(
        id = "2",
        name = "Montanha",
        iconUrl = "https://example.com/icon2.svg"
    ),
    Category(
        id = "3",
        name = "Floresta",
        iconUrl = "https://example.com/icon3.svg"
    ),
)

val places = List(20) {
    Place(
        id = "1",
        name = "Praia do Sol",
        imageUrls = listOf("https://example.com/praia.jpg"),
        description = "Uma bela praia com águas cristalinas.",
        address = "Av. Beira Mar, 123",
        city = "Rio de Janeiro",
        categoryId = "1",
        price = "Gratuito"
    )
}
