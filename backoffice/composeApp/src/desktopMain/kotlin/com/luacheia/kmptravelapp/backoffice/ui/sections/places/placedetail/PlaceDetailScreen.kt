package com.luacheia.kmptravelapp.backoffice.ui.sections.places.placedetail

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.places.placedetail.PlaceDetailViewModel
import com.luacheia.kmptravelapp.data.model.Category
import com.luacheia.kmptravelapp.data.model.Place
import com.luacheia.kmptravelapp.presentation.utils.UiState
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailScreen(
    viewModel: PlaceDetailViewModel = koinViewModel(),
    placeId: String,
    onClose: () -> Unit
) {
    val placeState by viewModel.placeDetailState.collectAsState()
    val editState by viewModel.editPlaceState.collectAsState()
    val deleteState by viewModel.deletePlaceState.collectAsState()
    val categories by viewModel.categories.collectAsState()
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var imageUrlsText by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory: Category? by remember { mutableStateOf(null) }
    var initialized by remember { mutableStateOf(false) }

    LaunchedEffect(placeId) {
        viewModel.loadPlaceById(placeId)
        initialized = false
    }

    // Atualiza os campos apenas na primeira vez que carrega o place
    LaunchedEffect(placeState, categories) {
        if (!initialized && placeState is UiState.Success) {
            val place = (placeState as UiState.Success<Place>).data
            name = place.name
            imageUrlsText = place.imageUrls.joinToString(", ")
            description = place.description
            address = place.address
            city = place.city
            price = place.price
            selectedCategory = categories.find { it.id == place.categoryId }
            initialized = true
        }
    }

    when (placeState) {
        is UiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Carregando...") }
        is UiState.Failure -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Erro ao carregar lugar") }
        is UiState.Success -> {
            val place = (placeState as UiState.Success<Place>).data
            Column(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Lugar", color = Color.Black)
                Spacer(Modifier.height(16.dp))
                if (isEditing) {
                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nome") })
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(value = imageUrlsText, onValueChange = { imageUrlsText = it }, label = { Text("URLs das Imagens") })
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descrição") })
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Endereço") })
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("Cidade") })
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Preço") })
                    Spacer(Modifier.height(8.dp))
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
                } else {
                    Text("Nome: ${place.name}")
                    Spacer(Modifier.height(8.dp))
                    Text("Imagens: ${place.imageUrls.joinToString(", ")}")
                    Spacer(Modifier.height(8.dp))
                    Text("Descrição: ${place.description}")
                    Spacer(Modifier.height(8.dp))
                    Text("Endereço: ${place.address}")
                    Spacer(Modifier.height(8.dp))
                    Text("Cidade: ${place.city}")
                    Spacer(Modifier.height(8.dp))
                    Text("Preço: ${place.price}")
                    Spacer(Modifier.height(8.dp))
                    Text("Categoria: ${categories.find { it.id == place.categoryId }?.name ?: place.categoryId}")
                }
                Spacer(Modifier.height(24.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (isEditing) {
                        Button(onClick = {
                            val updatedPlace = place.copy(
                                name = name,
                                imageUrls = imageUrlsText.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                                description = description,
                                address = address,
                                city = city,
                                price = price,
                                categoryId = selectedCategory?.id ?: place.categoryId
                            )
                            viewModel.updatePlace(updatedPlace)
                            isEditing = false
                        }) { Text("Salvar") }
                        Button(onClick = { isEditing = false }) { Text("Cancelar") }
                    } else {
                        Button(onClick = { isEditing = true }) { Text("Editar") }
                        Button(onClick = {
                            viewModel.deletePlace(place.id)
                            onClose()
                        }) { Text("Excluir") }
                        Button(onClick = onClose) { Text("Fechar") }
                    }
                }
                if (editState is UiState.Failure) {
                    Text("Erro ao editar lugar", color = Color.Red)
                }
                if (deleteState is UiState.Failure) {
                    Text("Erro ao deletar lugar", color = Color.Red)
                }
            }
        }
        else -> {}
    }
}
