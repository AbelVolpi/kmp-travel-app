package com.luacheia.kmptravelapp.backoffice.ui.sections.guidances

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luacheia.kmptravelapp.backoffice.ui.components.ReloadTopBar
import com.luacheia.kmptravelapp.backoffice.ui.components.Topic
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.AsyncImage
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.loadImageBitmap
import com.luacheia.kmptravelapp.backoffice.ui.theme.backgroundColor
import com.luacheia.kmptravelapp.data.model.Guidance
import com.luacheia.kmptravelapp.presentation.utils.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GuidancesListScreen(
    viewModel: GuidancesListViewModel = koinViewModel(),
    onGuidanceClicked: (String) -> Unit,
    onAddGuidanceClicked: () -> Unit
) {
    val state by viewModel.guidancesState.collectAsState()
    Column {
        ReloadTopBar(
            onClick = { viewModel.fetchGuidances() }
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Topic(
                        text = "Recomendações",
                        onAddClick = onAddGuidanceClicked
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
                            ?: "Erro ao carregar recomendações", color = Color.Red
                    )

                    is UiState.Success -> {
                        val guidances = (state as UiState.Success<List<Guidance>>).data
                        androidx.compose.foundation.lazy.LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(guidances.size) { idx ->
                                val g = guidances[idx]
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onGuidanceClicked(g.id) }
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
                                            load = { loadImageBitmap(g.iconUrl) },
                                            painterFor = { BitmapPainter(it) },
                                            contentDescription = g.title,
                                            modifier = Modifier.padding(20.dp)
                                                .size(40.dp),
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column {
                                        Text(g.title)
                                        Text(g.subtitle, color = Color.Gray, fontSize = 12.sp)
                                    }
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
