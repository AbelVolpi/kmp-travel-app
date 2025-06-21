package com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luacheia.kmptravelapp.backoffice.ui.components.Topic
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.AsyncImage
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.loadImageBitmap
import com.luacheia.kmptravelapp.backoffice.ui.theme.backgroundColor
import com.luacheia.kmptravelapp.data.model.Category
import com.luacheia.kmptravelapp.data.model.Place
import com.luacheia.kmptravelapp.presentation.utils.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoriesAndPlacesScreen(
    viewModel: CategoriesAndPlacesViewModel = koinViewModel(),
    onAddCategoryClicked: () -> Unit = {},
    onAddPlaceClicked: () -> Unit = {},
    onCategoryClicked: (String) -> Unit = { _ -> },
    onPlaceClicked: (String) -> Unit = { _ -> }
) {
    val categoriesAndPlacesUiState by viewModel.categoriesAndPlacesUiState.collectAsState()

    when (val uiState = categoriesAndPlacesUiState) {
        is UiState.Success -> {
            CategoriesAndPlacesSuccessLayout(
                categories = uiState.data.categories,
                places = uiState.data.places,
                onCategoryClicked = { id ->
                    onCategoryClicked.invoke(id)
                },
                onPlaceClicked = onPlaceClicked,
                onAddCategoryClick = onAddCategoryClicked,
                onAddPlaceClick = onAddPlaceClicked
            )
        }

        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Loading...", fontSize = 20.sp, color = Color.Black)
            }
        }

        is UiState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error loading data", fontSize = 20.sp, color = Color.Red)
            }
        }

        else -> {}
    }
}

@Composable
fun CategoriesAndPlacesSuccessLayout(
    categories: List<Category>,
    onCategoryClicked: (String) -> Unit,
    places: List<Place>,
    onPlaceClicked: (String) -> Unit,
    onAddCategoryClick: () -> Unit,
    onAddPlaceClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Topic("Categorias", onAddCategoryClick)
        CategoriesList(categories, onCategoryClicked)
        Spacer(modifier = Modifier.height(20.dp))
        Topic("Lugares", onAddPlaceClick)
        PlacesGrid(places, onPlaceClicked)
    }
}

@Composable
fun Title(
    text: String,
) {
    Text(
        text = text,
        color = Color.Black,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
    )
}


@Composable
fun CategoriesList(
    categories: List<Category>,
    onCategoryClicked: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        itemsIndexed(categories) { index, item ->
            CategoryItem(
                id = item.id,
                name = item.name,
                iconUrl = item.iconUrl,
                onCategoryClicked = onCategoryClicked
            )
        }
    }
}

@Composable
fun CategoryItem(
    id: String,
    name: String,
    iconUrl: String,
    onCategoryClicked: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(backgroundColor)
                .clickable { onCategoryClicked.invoke(id) },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                load = { loadImageBitmap(iconUrl) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "",
                modifier = Modifier.padding(20.dp)
                    .size(40.dp),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun PlacesGrid(
    places: List<Place>,
    onPlaceClicked: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier.fillMaxWidth().height(1000.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(places) { place ->
            PlaceItem(
                id = place.id,
                name = place.name,
                iconUrl = place.imageUrls.first(),
                onPlaceClicked = onPlaceClicked
            )
        }
    }
}

@Composable
fun PlaceItem(
    id: String,
    name: String,
    iconUrl: String,
    onPlaceClicked: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(backgroundColor)
                .clickable { onPlaceClicked.invoke(id) }
        ) {
            AsyncImage(
                load = { loadImageBitmap(iconUrl) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun AddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(20.dp)
            .clip(shape = CircleShape)
            .background(backgroundColor)
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            tint = Color.White,
            contentDescription = "Add"
        )
    }
}
