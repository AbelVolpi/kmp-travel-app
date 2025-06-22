package com.luacheia.kmptravelapp.backoffice.ui.sections.accommodations.accommodationdetail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.luacheia.kmptravelapp.data.model.Accommodation
import com.luacheia.kmptravelapp.presentation.utils.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AccommodationDetailScreen(
    accommodationId: String,
    viewModel: AccommodationDetailViewModel = koinViewModel(),
    onClose: () -> Unit
) {
    val state by viewModel.accommodationState.collectAsState()
    val editState by viewModel.editState.collectAsState()
    val deleteState by viewModel.deleteState.collectAsState()
    var title by remember { mutableStateOf("") }
    var iconUrl by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }

    LaunchedEffect(accommodationId) {
        viewModel.loadAccommodationById(accommodationId)
    }

    LaunchedEffect(editState) {
        if (editState is UiState.Success && isEditing) {
            isEditing = false
        }
    }
    LaunchedEffect(deleteState) {
        if (deleteState is UiState.Success) {
            onClose()
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (state) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Failure -> Text((state as UiState.Failure<Accommodation>).exception.message ?: "Erro ao carregar acomodação")
            is UiState.Success -> {
                val accommodation = (state as UiState.Success<Accommodation>).data
                if (!isEditing) {
                    title = accommodation.title
                    iconUrl = accommodation.iconUrl
                    link = accommodation.link
                }
                Column(
                    modifier = Modifier.width(350.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Detalhes da Acomodação")
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Título") },
                        enabled = isEditing && editState !is UiState.Loading
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = iconUrl,
                        onValueChange = { iconUrl = it },
                        label = { Text("URL do Ícone") },
                        enabled = isEditing && editState !is UiState.Loading
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = link,
                        onValueChange = { link = it },
                        label = { Text("Link") },
                        enabled = isEditing && editState !is UiState.Loading
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (editState is UiState.Failure) {
                        Text((editState as UiState.Failure<Unit>).exception?.message ?: "Erro ao editar", color = androidx.compose.ui.graphics.Color.Red)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    if (deleteState is UiState.Failure) {
                        Text((deleteState as UiState.Failure<Unit>).exception?.message ?: "Erro ao deletar", color = androidx.compose.ui.graphics.Color.Red)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (isEditing) {
                            Button(
                                onClick = {
                                    viewModel.updateAccommodation(
                                        accommodation.copy(title = title, iconUrl = iconUrl, link = link)
                                    )
                                },
                                enabled = editState !is UiState.Loading
                            ) { Text("Salvar") }
                            Button(
                                onClick = { isEditing = false },
                                enabled = editState !is UiState.Loading
                            ) { Text("Cancelar") }
                        } else {
                            Button(onClick = { isEditing = true }) { Text("Editar") }
                            Button(
                                onClick = { viewModel.deleteAccommodation(accommodation.id) },
                                enabled = deleteState !is UiState.Loading,
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                                ) { Text("Excluir") }
                            Button(
                                onClick = onClose
                            ) {
                                Text("Fechar")
                            }
                        }
                    }
                }
            }
            else -> {}
        }
    }
}
