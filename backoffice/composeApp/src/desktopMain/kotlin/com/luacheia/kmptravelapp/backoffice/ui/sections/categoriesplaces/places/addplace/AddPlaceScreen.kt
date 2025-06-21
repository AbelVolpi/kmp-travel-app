package com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.places.addplace

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.luacheia.kmptravelapp.data.model.Category
import com.luacheia.kmptravelapp.presentation.utils.UiState
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaceScreen(
    viewModel: AddPlaceViewModel = koinViewModel(),
    onDismiss: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val categories by viewModel.categories.collectAsState()
    var name by remember { mutableStateOf("") }
    var imageUrlsText by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory: Category? by remember { mutableStateOf(null) }

    LaunchedEffect(uiState) {
        if (uiState is UiState.Success) {
            onDismiss()
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.width(400.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Adicionar Lugar")
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome") },
                enabled = uiState !is UiState.Loading
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = imageUrlsText,
                onValueChange = { imageUrlsText = it },
                label = { Text("URLs das Imagens (separadas por vírgula)") },
                enabled = uiState !is UiState.Loading
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                enabled = uiState !is UiState.Loading
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Endereço") },
                enabled = uiState !is UiState.Loading
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Cidade") },
                enabled = uiState !is UiState.Loading
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Preço") },
                enabled = uiState !is UiState.Loading
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Dropdown de categorias
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedCategory?.name ?: "Selecione uma categoria",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoria") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    enabled = uiState !is UiState.Loading
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                selectedCategory = category
                                expanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (uiState is UiState.Failure) {
                Text((uiState as UiState.Failure<Unit>).exception?.message ?: "Erro", color = androidx.compose.ui.graphics.Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        val imageUrls = imageUrlsText.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                        val categoryId = selectedCategory?.id ?: ""
                        viewModel.addPlace(name, imageUrls, description, address, city, categoryId, price)
                    },
                    enabled = uiState !is UiState.Loading && selectedCategory != null
                ) { Text("Salvar") }
                Button(
                    onClick = onDismiss,
                    enabled = uiState !is UiState.Loading
                ) { Text("Cancelar") }
            }
        }
    }
}
