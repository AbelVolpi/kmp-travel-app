package com.luacheia.kmptravelapp.android.presentation.ui.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.luacheia.kmptravelapp.android.presentation.components.LoadingIndicator
import com.luacheia.kmptravelapp.android.presentation.components.SearchBarComponent
import com.luacheia.kmptravelapp.android.presentation.theme.MyApplicationTheme
import com.luacheia.kmptravelapp.android.presentation.theme.tertiaryColor
import com.luacheia.kmptravelapp.android.presentation.ui.home.HomeScreen
import com.luacheia.kmptravelapp.data.model.Category
import com.luacheia.kmptravelapp.data.model.Place
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExploreScreen(
    exploreViewModel: ExploreViewModel = koinViewModel(),
    onCategoryClicked: (String, String) -> Unit,
    onPlaceClicked: (String) -> Unit
) {
    val exploreUIData = exploreViewModel.exploreModel.collectAsStateWithLifecycle().value

    if (exploreUIData != null) {
        ExploreUI(
            exploreUIData,
            onCategoryClicked,
            onPlaceClicked
        )
    } else {
        LoadingIndicator()
    }
}

@Composable
fun ExploreUI(
    exploreUIData: ExploreModel,
    onCategoryClicked: (String, String) -> Unit,
    onPlaceClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 45.dp)
    ) {
        SearchBarComponent()
        CategoriesTitle()
        CategoriesList(
            categories = exploreUIData.categories,
            onCategoryClicked = onCategoryClicked
        )
        RecommendationsTitle()
        RecommendationsGrid(
            places = exploreUIData.places,
            onPlaceClicked = onPlaceClicked
        )
    }
}

@Composable
fun CategoriesTitle() {
    Text(
        text = "Categorias",
        color = Color.White,
        modifier = Modifier.padding(top = 20.dp, bottom = 15.dp, end = 20.dp, start = 20.dp),
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CategoriesList(
    categories: List<Category>,
    onCategoryClicked: (String, String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        itemsIndexed(categories) { index, item ->
            CategoryItem(
                item.id,
                item.name,
                item.iconUrl,
                index,
                categories.lastIndex,
                onCategoryClicked
            )
        }
    }
}

@Composable
fun CategoryItem(
    id: String,
    name: String,
    iconUrl: String,
    position: Int,
    lastIndex: Int,
    onCategoryClicked: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                start = if (position == 0) 20.dp else 0.dp,
                end = if (position == lastIndex) 30.dp else 0.dp
            )
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(color = tertiaryColor)
                .clickable {
                    onCategoryClicked.invoke(id, name)
                }
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(20.dp)
                    .size(40.dp),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(iconUrl)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = null
            )
        }
        Text(
            text = name,
            fontSize = 12.sp,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .widthIn(max = 80.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun RecommendationsTitle() {
    Text(
        text = "Recomendações dos Anfitriões",
        color = Color.White,
        modifier = Modifier.padding(top = 20.dp, bottom = 15.dp, end = 20.dp, start = 20.dp),
        fontSize = 21.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun RowScope.RecommendationItem(
    id: String,
    name: String,
    iconUrl: String,
    onPlaceClicked: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .weight(1f)
            .padding(horizontal = 4.dp)
            .clip(shape = RoundedCornerShape(30.dp))
            .clickable {
                onPlaceClicked.invoke(id)
            }
    ) {
        AsyncImage(
            model = iconUrl,
            modifier = Modifier.fillMaxSize(),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .widthIn(100.dp, 120.dp)
                .padding(top = 10.dp, start = 10.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(Color.White)
        ) {
            Text(
                text = name,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 2.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecommendationsGrid(
    places: List<Place>,
    onPlaceClicked: (String) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        maxItemsInEachRow = 2
    ) {
        places.forEach { place ->
            RecommendationItem(
                place.id,
                place.name,
                place.imageUrls.first(),
                onPlaceClicked
            )
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        HomeScreen()
    }
}
