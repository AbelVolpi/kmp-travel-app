package com.abelvolpi.kmptravelapp.android.presentation.ui.explore

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.abelvolpi.kmptravelapp.android.R
import com.abelvolpi.kmptravelapp.android.presentation.theme.MyApplicationTheme
import com.abelvolpi.kmptravelapp.android.presentation.theme.secondaryColor
import com.abelvolpi.kmptravelapp.android.presentation.theme.tertiaryColor
import com.abelvolpi.kmptravelapp.android.presentation.ui.home.HomeScreen
import com.abelvolpi.kmptravelapp.data.model.Category
import com.abelvolpi.kmptravelapp.data.model.Place
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExploreScreen(
    exploreViewModel: ExploreViewModel = koinViewModel()
) {
    val exploreUIData = exploreViewModel.exploreModel.collectAsStateWithLifecycle().value

    if (exploreUIData != null) {
        ExploreUI(exploreUIData)
    } else {
        LoadingIndicator()
    }
}

@Composable
fun ExploreUI(
    exploreUIData: ExploreModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 45.dp)
    ) {
        SearchBarComponent()
        CategoriesTitle()
        CategoriesList(exploreUIData.categories)
        RecommendationsTitle()
        RecommendationsGrid(exploreUIData.places)
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

//TODO move to shared components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent() {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(0.dp, LocalConfiguration.current.screenHeightDp.dp)
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp),
        colors = SearchBarDefaults.colors(
            containerColor = secondaryColor,
            inputFieldColors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                unfocusedPlaceholderColor = Color.White,
                focusedPlaceholderColor = Color.White,
            )
        ),
        query = text,
        onQueryChange = { text = it },
        onSearch = { active = false },
        active = active,
        onActiveChange = { active = it },
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = "Pesquisar",
                tint = Color.White
            )
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        if (text.isNotEmpty()) {
                            text = ""
                        } else {
                            active = false
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear",
                    tint = Color.White
                )
            }
        }
    ) {
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
    categories: List<Category>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        itemsIndexed(categories) { index, item ->
            CategoryItem(
                item.name,
                item.iconUrl,
                index,
                categories.lastIndex
            )
        }
    }
}

@Composable
fun CategoryItem(
    name: String,
    iconUrl: String,
    position: Int,
    lastIndex: Int
) {
    Column(
        modifier = Modifier
            .padding(
                start = if (position == 0) 20.dp else 0.dp,
                end = if (position == lastIndex) 30.dp else 0.dp,
            )
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(color = tertiaryColor)
                .clickable { }
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(20.dp)
                    .size(40.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(iconUrl)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = null,
            )
        }
        Text(
            text = name,
            fontSize = 12.sp,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center,
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
    name: String,
    iconUrl: String,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .weight(1f)
            .padding(horizontal = 4.dp)
            .clip(shape = RoundedCornerShape(30.dp))
            .clickable { }
    ) {
        AsyncImage(
            model = iconUrl,
            modifier = Modifier.fillMaxSize(),
            contentDescription = ""
        )
        Box(
            modifier = Modifier
                .widthIn(100.dp, 120.dp)
                .padding(top = 10.dp, start = 10.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(Color.White),

            ) {
            Text(
                text = name,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
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
    places: List<Place>
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
                place.name,
                place.imageUrls.first()
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
