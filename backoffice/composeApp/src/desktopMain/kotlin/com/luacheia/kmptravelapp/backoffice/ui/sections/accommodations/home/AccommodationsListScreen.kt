package com.luacheia.kmptravelapp.backoffice.ui.sections.accommodations.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.luacheia.kmptravelapp.backoffice.ui.components.ReloadTopBar
import com.luacheia.kmptravelapp.presentation.utils.UiState
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.AsyncImage
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.loadImageBitmap
import com.luacheia.kmptravelapp.backoffice.ui.components.Topic
import com.luacheia.kmptravelapp.backoffice.ui.theme.backgroundColor
import com.luacheia.kmptravelapp.data.model.Accommodation
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AccommodationsListScreen(
    viewModel: AccommodationsListViewModel = koinViewModel(),
    onAccommodationClicked: (String) -> Unit,
    onAddAccommodationClicked: () -> Unit
) {
    val state by viewModel.accommodationsState.collectAsState()
    Column {
        ReloadTopBar(
            onClick = { viewModel.fetchAccommodations() }
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Topic(
                        text = "Acomodações",
                        onAddClick = onAddAccommodationClicked
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                when (state) {
                    is UiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is UiState.Failure -> Text(
                        (state as UiState.Failure<List<*>>).exception.message
                            ?: "Erro ao carregar acomodações", color = Color.Red
                    )

                    is UiState.Success -> {
                        val accommodations = (state as UiState.Success<List<Accommodation>>).data
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(accommodations.size) { idx ->
                                val acc = accommodations[idx]
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onAccommodationClicked(acc.id) }
                                        .padding(vertical = 8.dp, horizontal = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(backgroundColor),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        AsyncImage(
                                            load = { loadImageBitmap(acc.iconUrl) },
                                            painterFor = { BitmapPainter(it) },
                                            contentDescription = acc.title,
                                            modifier = Modifier.padding(20.dp)
                                                .size(40.dp),
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        acc.title,
                                        modifier = Modifier.weight(1f),
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}
