package com.luacheia.kmptravelapp.android.presentation.ui.place

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.luacheia.kmptravelapp.android.R
import com.luacheia.kmptravelapp.android.presentation.components.LoadingIndicator
import com.luacheia.kmptravelapp.android.presentation.theme.backgroundColor
import com.luacheia.kmptravelapp.android.presentation.utils.formatBreakLines
import com.luacheia.kmptravelapp.data.model.Place
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlaceScreen(
    placeId: String,
    placeViewModel: PlaceViewModel = koinViewModel(),
    onBackButtonClicked: () -> Unit,
    onTraceRouteClicked: (String) -> Unit
) {
    val placeData = placeViewModel.placeModel.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        placeViewModel.getPlace(placeId)
    }

    if (placeData != null) {
        PlaceUI(placeData, onBackButtonClicked, onTraceRouteClicked)
    } else {
        LoadingIndicator()
    }
}

@Composable
fun PlaceUI(
    place: Place,
    onBackButtonClicked: () -> Unit,
    onTraceRouteClicked: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        item {
            ImagesPagerComponent(
                imageUrls = place.imageUrls,
                onBackButtonClicked = onBackButtonClicked
            )
        }
        item {
            PlaceTitle(
                title = place.name
            )
        }
        item {
            PlaceDescription(
                description = place.description.formatBreakLines()
            )
        }
        item {
            TraceRoute(
                onTraceRouteClicked = onTraceRouteClicked,
                routeAddress = "${place.name} - ${place.city}"
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageIndicator(
    pagerState: PagerState,
    modifier: Modifier
) {
    Row(modifier = modifier) {
        repeat(pagerState.pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) Color.LightGray else Color.DarkGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(12.dp)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesPagerComponent(
    imageUrls: List<String>,
    onBackButtonClicked: () -> Unit
) {
    val pagerState = rememberPagerState(
        pageCount = { imageUrls.size }
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            key = { it },
            pageSize = PageSize.Fill,
            modifier = Modifier.fillMaxSize()
        ) { index ->
            AsyncImage(
                model = imageUrls[index],
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        PageIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(bottom = 10.dp)
                .clip(shape = RoundedCornerShape(40.dp))
                .background(Color.Black.copy(0.3f))
                .clickable { }
                .padding(5.dp)
                .align(Alignment.BottomCenter)
        )
        IconButton(
            onClick = { onBackButtonClicked.invoke() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .background(Color.Black.copy(0.3f), CircleShape)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                tint = Color.White,
                contentDescription = "Back"
            )
        }
    }
}

@Composable
fun PlaceTitle(title: String) {
    Text(
        text = title,
        color = Color.White,
        modifier = Modifier
            .padding(top = 45.dp, bottom = 15.dp, start = 30.dp, end = 30.dp),
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun PlaceDescription(description: String) {
    Text(
        text = description,
        color = Color.White,
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(
                start = 30.dp,
                end = 30.dp
            )
    )
}

@Composable
fun TraceRoute(
    onTraceRouteClicked: (String) -> Unit,
    routeAddress: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(vertical = 40.dp)
            .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(50.dp))
            .clip(shape = RoundedCornerShape(50.dp))
            .padding(horizontal = 20.dp, vertical = 15.dp)
            .clickable {
                onTraceRouteClicked.invoke(routeAddress)
            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.route_icon),
            contentDescription = "WhatsApp",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(30.dp)
        )
        Text(
            text = "Traçar rota",
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 10.dp)
        )
    }
}
