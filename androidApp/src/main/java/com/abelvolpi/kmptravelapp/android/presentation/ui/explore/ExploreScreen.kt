package com.abelvolpi.kmptravelapp.android.presentation.ui.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abelvolpi.kmptravelapp.android.R
import com.abelvolpi.kmptravelapp.android.app.HomeScreen
import com.abelvolpi.kmptravelapp.android.presentation.theme.MyApplicationTheme
import com.abelvolpi.kmptravelapp.android.presentation.theme.secondaryColor
import com.abelvolpi.kmptravelapp.android.presentation.theme.tertiaryColor

@Composable
fun ExploreScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item { SearchBarComponent() }
        item { CategoriesTitle() }
        item { CategoriesList() }
        item { RecommendationsTitle() }
        item { RecommendationsGrid() }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent() {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 45.dp, start = 30.dp, end = 30.dp),
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
        modifier = Modifier.padding(top = 20.dp, bottom = 15.dp, end = 30.dp, start = 30.dp),
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CategoriesList() {
    val categoryList = listOf(
        R.drawable.waterfall_icon,
        R.drawable.restaurant_icon,
        R.drawable.trail_icon,
        R.drawable.trail_icon,
        R.drawable.attraction_icon
    )
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        itemsIndexed(categoryList) { index, item ->
            CategoryItem(item, index, categoryList.lastIndex)
        }
    }
}

@Composable
fun CategoryItem(resourceId: Int, position: Int, lastIndex: Int) {
    Column(
        modifier = Modifier.padding(
            start = if (position == 0) 30.dp else 0.dp,
            end = if (position == lastIndex) 30.dp else 0.dp,
        )
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(color = tertiaryColor)
        ) {
            Icon(
                modifier = Modifier
                    .padding(20.dp)
                    .size(30.dp),
                painter = painterResource(id = resourceId),
                contentDescription = "",
                tint = Color.White
            )
        }
        Text(
            text = "Cachoeiras",
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}


@Composable
fun RecommendationsTitle() {
    Text(
        text = "Recomendações dos Anfitriões",
        color = Color.White,
        modifier = Modifier.padding(top = 20.dp, bottom = 15.dp, end = 30.dp, start = 30.dp),
        fontSize = 21.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun RecommendationItem() {
    Box(
        modifier = Modifier
            .size(150.dp)
            .clip(shape = RoundedCornerShape(30.dp))
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.recomendation),
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
                text = "Ponto Turístico",
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
fun RecommendationsGrid() {
    FlowRow(
        modifier = Modifier.padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        maxItemsInEachRow = 2
    ) {
        repeat(6) {
            RecommendationItem()
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
