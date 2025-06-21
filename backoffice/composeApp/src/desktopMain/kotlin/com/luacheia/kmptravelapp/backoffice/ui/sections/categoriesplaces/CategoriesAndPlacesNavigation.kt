package com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.addcategory.AddCategoryScreen
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.categorydetail.CategoryDetailScreen
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.home.AddButton
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.home.CategoriesAndPlacesScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

@Composable
fun CategoriesAndPlacesNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "categories") {
        composable("categories") {
            CategoriesAndPlacesScreen(
                onCategoryClicked = { categoryId ->
                    navController.navigate("categoryDetail/$categoryId")
                },
                onAddCategoryClicked = {
                    navController.navigate("addCategory")
                }
            )
        }
        composable("categoryDetail/{categoryId}") { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: return@composable
            CategoryDetailScreen(
                categoryId = categoryId,
                onClose = { navController.popBackStack() }
            )
        }
        composable("addCategory") {
            AddCategoryScreen(
                onDismiss = { navController.popBackStack() }
            )

        }
    }
}

@Composable
fun <T> AsyncImage(
    load: suspend () -> T,
    painterFor: @Composable (T) -> Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val image: T? by produceState<T?>(null) {
        value = withContext(Dispatchers.IO) {
            try {
                load()
            } catch (e: IOException) {
                // instead of printing to console, you can also write this to log,
                // or show some error placeholder
                e.printStackTrace()
                null
            }
        }
    }

    if (image != null) {
        Image(
            painter = painterFor(image!!),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}
//fun loadImageBitmap(file: File): ImageBitmap =
//    file.inputStream().buffered().use(::loadImageBitmap)

//fun loadSvgPainter(file: File, density: Density): Painter =
//    file.inputStream().buffered().use { loadSvgPainter(it, density) }
//
//fun loadXmlImageVector(file: File, density: Density): ImageVector =
//    file.inputStream().buffered().use { loadXmlImageVector(InputSource(it), density) }

/* Loading from network with java.net API */

fun loadImageBitmap(url: String): ImageBitmap =
    URL(url).openStream().buffered().use(::loadImageBitmap)
//
//fun loadSvgPainter(url: String, density: Density): Painter =
//    URL(url).openStream().buffered().use { loadSvgPainter(it, density) }
//
//fun loadXmlImageVector(url: String, density: Density): ImageVector =
//    URL(url).openStream().buffered().use { loadXmlImageVector(InputSource(it), density) }

@Preview
@Composable
fun AddButtonPreview() {
    AddButton(onClick = { }, modifier = Modifier.padding(16.dp))
}