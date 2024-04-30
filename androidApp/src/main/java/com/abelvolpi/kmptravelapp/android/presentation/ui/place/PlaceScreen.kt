package com.abelvolpi.kmptravelapp.android.presentation.ui.place

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abelvolpi.kmptravelapp.android.R
import com.abelvolpi.kmptravelapp.android.presentation.theme.backgroundColor

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun PlaceScreen() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        item { ImagesPagerComponent(pagerState = pagerState) }
        item { PlaceTitle("Morro da Igreja") }
        item { PlaceDescription(example_text) }
        item { TraceRoute() }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageIndicator(pagerState: PagerState, modifier: Modifier) {
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
fun ImagesPagerComponent(pagerState: PagerState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
    ) {
        HorizontalPager(
            state = pagerState,
            key = { it },
            pageSize = PageSize.Fill,
            modifier = Modifier.fillMaxSize()
        ) { index ->
            Image(
                painter = painterResource(id = R.drawable.recomendation),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
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
            onClick = { /* Handle back button click */ },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .background(Color.Black.copy(0.3f), CircleShape),
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
fun TraceRoute() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(vertical = 40.dp)
            .border(width = 2.dp, color = Color.White, shape =  RoundedCornerShape(50.dp))
            .clip(shape = RoundedCornerShape(50.dp))
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .clickable { }
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


const val example_text =
    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry."