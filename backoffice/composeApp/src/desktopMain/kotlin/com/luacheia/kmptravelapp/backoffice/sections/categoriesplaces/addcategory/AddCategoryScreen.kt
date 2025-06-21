package com.luacheia.kmptravelapp.backoffice.sections.categoriesplaces.addcategory

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.luacheia.kmptravelapp.presentation.utils.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddCategoryScreen(
    viewModel: AddCategoryViewModel = koinViewModel(),
    onDismiss: () -> Unit,
) {
    val uiState by  viewModel.uiState.collectAsState()
    var name by remember { mutableStateOf("") }
    var iconUrl by remember { mutableStateOf("") }

    // Close the screen on success
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
            Text("Adicionar Categoria")
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome") },
                enabled = uiState !is UiState.Loading
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = iconUrl,
                onValueChange = { iconUrl = it },
                label = { Text("URL da Imagem") },
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
                    onClick = { viewModel.addCategory(name, iconUrl) },
                    enabled = uiState !is UiState.Loading
                ) { Text("Salvar") }
                Button(
                    onClick = onDismiss,
                    enabled = uiState !is UiState.Loading
                ) { Text("Cancelar") }
            }
        }
    }
}