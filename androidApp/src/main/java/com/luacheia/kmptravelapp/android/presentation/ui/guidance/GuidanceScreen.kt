package com.luacheia.kmptravelapp.android.presentation.ui.guidance

import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luacheia.kmptravelapp.android.presentation.components.LoadingIndicator
import com.luacheia.kmptravelapp.android.presentation.theme.backgroundColor
import com.luacheia.kmptravelapp.android.presentation.utils.formatBreakLines
import com.luacheia.kmptravelapp.data.model.Guidance
import org.koin.androidx.compose.koinViewModel

@Composable
fun GuidanceScreen(
    guidanceId: String,
    guidanceViewModel: GuidanceViewModel = koinViewModel(),
    onBackButtonClicked: () -> Unit
) {
    val guidanceUIData = guidanceViewModel.guidanceModel.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        guidanceViewModel.getGuidance(guidanceId)
    }

    if (guidanceUIData != null) {
        GuidanceUI(
            guidanceUIData,
            onBackButtonClicked
        )
    } else {
        LoadingIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuidanceUI(
    guidance: Guidance,
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
                    containerColor = backgroundColor
                ),
                title = {
                    Text(
                        text = guidance.title,
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
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        val descriptionFormatted = guidance.description.toAnnotatedString()
        Text(
            text = descriptionFormatted,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(
                    top = padding.calculateTopPadding(),
                    start = 30.dp,
                    end = 30.dp
                )
                .verticalScroll(rememberScrollState())
        )
    }
}