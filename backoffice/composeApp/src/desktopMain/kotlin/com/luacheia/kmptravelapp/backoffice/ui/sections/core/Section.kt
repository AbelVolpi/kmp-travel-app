package com.luacheia.kmptravelapp.backoffice.ui.sections.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.House
import androidx.compose.material.icons.outlined.Place
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Section(
    val name: String,
    val icon: ImageVector
) {
    data object CategoriesAndPlaces : Section("Categorias e Lugares", Icons.Outlined.Place)
    data object Accommodations : Section("Acomodações", Icons.Outlined.House)
    data object Guidelines : Section("Recomendações", Icons.Outlined.Book)
}
