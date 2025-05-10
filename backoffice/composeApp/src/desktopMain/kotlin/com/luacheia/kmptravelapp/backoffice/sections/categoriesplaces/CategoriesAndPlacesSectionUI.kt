package com.luacheia.kmptravelapp.backoffice.sections.categoriesplaces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import coil.compose.AsyncImage
//import coil.decode.SvgDecoder
//import coil.request.ImageRequest
//import com.luacheia.kmptravelapp.data.model.Category
//import com.luacheia.kmptravelapp.data.model.Place

@Composable
fun CategoriesAndPlacesSectionUI(
//    categories: List<Category>,
//    places: List<Place>,
//    onCategoryClicked: (String, String) -> Unit,
//    onPlaceClicked: (String) -> Unit
) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//            .padding(16.dp)
//    ) {
//        CategoriesTitle()
//        CategoriesList(categories, onCategoryClicked)
//        Spacer(modifier = Modifier.height(20.dp))
//        PlacesTitle()
//        PlacesGrid(places, onPlaceClicked)
//    }
//}
//
//@Composable
//fun CategoriesTitle() {
//    Text(
//        text = "Categorias",
//        color = Color.White,
//        fontSize = 25.sp,
//        fontWeight = FontWeight.Bold,
//        modifier = Modifier.padding(bottom = 15.dp)
//    )
//}
//
//@Composable
//fun CategoriesList(
//    categories: List<Category>,
//    onCategoryClicked: (String, String) -> Unit
//) {
//    LazyRow(
//        horizontalArrangement = Arrangement.spacedBy(15.dp)
//    ) {
//        itemsIndexed(categories) { index, item ->
//            CategoryItem(
//                id = item.id,
//                name = item.name,
//                iconUrl = item.iconUrl,
//                onCategoryClicked = onCategoryClicked
//            )
//        }
//    }
//}
//
//@Composable
//fun CategoryItem(
//    id: String,
//    name: String,
//    iconUrl: String,
//    onCategoryClicked: (String, String) -> Unit
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .padding(8.dp)
//            .clickable { onCategoryClicked(id, name) }
//    ) {
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(iconUrl)
//                .decoderFactory(SvgDecoder.Factory())
//                .build(),
//            contentDescription = null,
//            modifier = Modifier
//                .size(60.dp)
//                .padding(8.dp)
//        )
//        Text(
//            text = name,
//            fontSize = 12.sp,
//            color = Color.White,
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis,
//            textAlign = TextAlign.Center
//        )
//    }
//}
//
//@Composable
//fun PlacesTitle() {
//    Text(
//        text = "Lugares",
//        color = Color.White,
//        fontSize = 25.sp,
//        fontWeight = FontWeight.Bold,
//        modifier = Modifier.padding(bottom = 15.dp)
//    )
//}
//
//@Composable
//fun PlacesGrid(
//    places: List<Place>,
//    onPlaceClicked: (String) -> Unit
//) {
//    Column(
//        verticalArrangement = Arrangement.spacedBy(10.dp),
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        places.forEach { place ->
//            PlaceItem(
//                id = place.id,
//                name = place.name,
//                iconUrl = place.imageUrls.firstOrNull() ?: "",
//                onPlaceClicked = onPlaceClicked
//            )
//        }
//    }
//}
//
//@Composable
//fun PlaceItem(
//    id: String,
//    name: String,
//    iconUrl: String,
//    onPlaceClicked: (String) -> Unit
//) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .clickable { onPlaceClicked(id) }
//    ) {
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(iconUrl)
//                .build(),
//            contentDescription = null,
//            modifier = Modifier
//                .size(50.dp)
//                .padding(8.dp)
//        )
//        Text(
//            text = name,
//            fontSize = 16.sp,
//            color = Color.White,
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis
//        )
//    }
}