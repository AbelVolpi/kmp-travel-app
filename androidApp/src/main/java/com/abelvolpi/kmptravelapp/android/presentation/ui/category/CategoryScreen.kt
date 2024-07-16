package com.abelvolpi.kmptravelapp.android.presentation.ui.category

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.abelvolpi.kmptravelapp.android.presentation.components.LoadingIndicator
import com.abelvolpi.kmptravelapp.android.presentation.components.SearchBarComponent
import com.abelvolpi.kmptravelapp.android.presentation.theme.backgroundColor
import com.abelvolpi.kmptravelapp.data.model.Place
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel = koinViewModel(),
    categoryId: String,
    categoryName: String,
    onPlaceClicked: (String) -> Unit,
    onBackButtonClicked: () -> Unit
) {
    val categoryUIData = categoryViewModel.placesModel.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit){
        categoryViewModel.getPlacesByCategory(categoryId)
    }

    if (categoryUIData != null) {
        CategoryUI(
            categoryName,
            categoryUIData,
            onPlaceClicked,
            onBackButtonClicked
        )
    } else {
        LoadingIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryUI(
    categoryName: String,
    places: List<Place>,
    onPlaceClicked: (String) -> Unit,
    onBackButtonClicked: () -> Unit
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                ),
                title = {
                    Text(
                        text = categoryName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackButtonClicked.invoke() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(top = padding.calculateTopPadding())
        ) {
            item { SearchBarComponent() }
            places.forEach { place ->
                item { Spacer(modifier = Modifier.padding(10.dp)) }
                item {
                    PlaceComponent(
                        placeId = place.id,
                        placeTitle = place.name,
                        placeDescription = place.description,
                        imageUrl = place.imageUrls.first(),
                        onPlaceClicked = onPlaceClicked
                    )
                }
            }
        }
    }
}

@Composable
fun PlaceComponent(
    placeId: String,
    placeTitle: String,
    placeDescription: String,
    imageUrl: String,
    onPlaceClicked: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(horizontal = 20.dp)
            .border(
                width = 1.dp,
                color = Color.White.copy(0.5f),
                shape = RoundedCornerShape(20.dp)
            )
            .clip(shape = RoundedCornerShape(20.dp))
            .clickable {
                onPlaceClicked.invoke(placeId)
            }
            .padding(10.dp)

    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .align(Alignment.CenterVertically)
                .clip(shape = RoundedCornerShape(20.dp)),
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,

            )
        Column(
            modifier = Modifier
                .padding(start = 10.dp)
        ) {
            Text(
                text = placeTitle,
                fontSize = 18.sp,
                color = Color.White,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = placeDescription,
                fontSize = 15.sp,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Justify
            )
        }
    }
}
