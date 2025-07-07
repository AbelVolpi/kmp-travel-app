package com.luacheia.kmptravelapp.backoffice.ui.sections.guidances

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.luacheia.kmptravelapp.backoffice.ui.theme.backgroundColor
import com.luacheia.kmptravelapp.presentation.utils.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddGuidanceScreen(
    viewModel: AddGuidanceViewModel = koinViewModel(),
    onDismiss: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var iconUrl by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (uiState is UiState.Success) {
            onDismiss()
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.width(350.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Adicionar Recomendação")
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                enabled = uiState !is UiState.Loading
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = subtitle,
                onValueChange = { subtitle = it },
                label = { Text("Subtítulo") },
                enabled = uiState !is UiState.Loading
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = iconUrl,
                onValueChange = { iconUrl = it },
                label = { Text("URL do Ícone") },
                enabled = uiState !is UiState.Loading
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                enabled = uiState !is UiState.Loading
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (uiState is UiState.Failure) {
                Text(
                    (uiState as UiState.Failure<Unit>).exception?.message ?: "Erro",
                    color = androidx.compose.ui.graphics.Color.Red
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
                    onClick = { viewModel.addGuidance(title, subtitle, iconUrl, description) },
                    enabled = uiState !is UiState.Loading
                ) {
                    Text("Salvar")
                }
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
                    onClick = onDismiss,
                    enabled = uiState !is UiState.Loading
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}
