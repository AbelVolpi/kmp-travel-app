package com.luacheia.kmptravelapp.backoffice.ui.sections.guidances

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
import com.luacheia.kmptravelapp.data.model.Guidance
import com.luacheia.kmptravelapp.presentation.utils.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GuidanceDetailScreen(
    guidanceId: String,
    viewModel: GuidanceDetailViewModel = koinViewModel(),
    onClose: () -> Unit
) {
    val state by viewModel.guidanceState.collectAsState()
    val editState by viewModel.editState.collectAsState()
    val deleteState by viewModel.deleteState.collectAsState()
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var iconUrl by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }

    LaunchedEffect(guidanceId) {
        viewModel.loadGuidanceById(guidanceId)
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
            is UiState.Failure -> Text((state as UiState.Failure<Guidance>).exception.message ?: "Erro ao carregar recomendação")
            is UiState.Success -> {
                val guidance = (state as UiState.Success<Guidance>).data
                if (!isEditing) {
                    title = guidance.title
                    subtitle = guidance.subtitle
                    iconUrl = guidance.iconUrl
                    description = guidance.description
                }
                Column(
                    modifier = Modifier.width(350.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Detalhes da Recomendação")
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Título") },
                        enabled = isEditing && editState !is UiState.Loading
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = subtitle,
                        onValueChange = { subtitle = it },
                        label = { Text("Subtítulo") },
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
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Descrição") },
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
                                    viewModel.updateGuidance(
                                        guidance.copy(title = title, subtitle = subtitle, iconUrl = iconUrl, description = description)
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
                                onClick = { viewModel.deleteGuidance(guidance.id) },
                                enabled = deleteState !is UiState.Loading,
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                                ) { Text("Excluir") }
                            Button(onClick = onClose) { Text("Fechar") }
                        }
                    }
                }
            }
            else -> {}
        }
    }
}
