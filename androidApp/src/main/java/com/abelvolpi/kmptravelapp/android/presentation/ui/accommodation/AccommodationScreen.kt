package com.abelvolpi.kmptravelapp.android.presentation.ui.accommodation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abelvolpi.kmptravelapp.android.presentation.components.IconSource
import com.abelvolpi.kmptravelapp.android.presentation.components.InfoBox
import com.abelvolpi.kmptravelapp.android.presentation.components.LoadingIndicator
import com.abelvolpi.kmptravelapp.android.presentation.theme.backgroundColor
import com.abelvolpi.kmptravelapp.data.model.Accommodation
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccommodationScreen(
    accommodationViewModel: AccommodationViewModel = koinViewModel(),
    onAccommodationClicked: () -> Unit,
    onBackButtonClicked: () -> Unit
) {
    val accommodationUIData =
        accommodationViewModel.accommodationsModel.collectAsStateWithLifecycle().value

    if (accommodationUIData != null) {
        AccommodationUI(
            accommodations = accommodationUIData,
            onAccommodationClicked = onAccommodationClicked,
            onBackButtonClicked = onBackButtonClicked
        )
    } else {
        LoadingIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccommodationUI(
    accommodations: List<Accommodation>,
    onAccommodationClicked: () -> Unit,
    onBackButtonClicked: () -> Unit
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                ),
                title = {
                    Text(
                        "Acomodações",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackButtonClicked.invoke()
                    }) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(backgroundColor)
                .padding(top = padding.calculateTopPadding())
        ) {
            Text(
                text = "Fique por dentro das nossas acomodações:",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp, bottom = 30.dp)

            )
            accommodations.forEach {
                AccommodationInfoBox(
                    title = it.title,
                    iconUrl = it.iconUrl,
                    onAccommodationClicked = onAccommodationClicked
                )
            }
        }
    }
}

@Composable
fun AccommodationInfoBox(
    title: String,
    iconUrl: String,
    onAccommodationClicked: () -> Unit
) {
    InfoBox(
        title = title,
        iconSource = IconSource.Remote(iconUrl),
        onInfoBoxClicked = onAccommodationClicked
    )
}