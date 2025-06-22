package com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.categories.categorydetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luacheia.kmptravelapp.data.model.Category
import com.luacheia.kmptravelapp.presentation.utils.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoryDetailScreen(
    viewModel: CategoryDetailViewModel = koinViewModel(),
    categoryId: String,
    onClose: () -> Unit
) {
    val categoryState by viewModel.categoryDetailState.collectAsState()
    val editState by viewModel.editCategoryState.collectAsState()
    val deleteState by viewModel.deleteCategoryState.collectAsState()
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var iconUrl by remember { mutableStateOf("") }

    LaunchedEffect(categoryId) {
        viewModel.loadCategoryById(categoryId)
    }

    when (categoryState) {
        is UiState.Loading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { Text("Carregando...") }

        is UiState.Failure -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { Text("Erro ao carregar categoria") }

        is UiState.Success -> {
            val category = (categoryState as UiState.Success<Category>).data
            if (!isEditing) {
                name = category.name
                iconUrl = category.iconUrl
            }
            Column(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text("Categoria", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(Modifier.height(16.dp))
                if (isEditing) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nome") })
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = iconUrl,
                        onValueChange = { iconUrl = it },
                        label = { Text("URL do Ícone") })
                } else {
                    Text("Nome: ${category.name}")
                    Spacer(Modifier.height(8.dp))
                    Text("Ícone: ${category.iconUrl}")
                }
                Spacer(Modifier.height(24.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (isEditing) {
                        Button(onClick = {
                            viewModel.updateCategory(category.copy(name = name, iconUrl = iconUrl))
                            isEditing = false
                        }, enabled = editState !is UiState.Loading) { Text("Salvar") }
                        Button(onClick = { isEditing = false }) { Text("Cancelar") }
                    } else {
                        Button(onClick = { isEditing = true }) { Text("Editar") }
                        Button(
                            onClick = {
                                viewModel.deleteCategory(category.id)
                                onClose()
                            },
                            enabled = deleteState !is UiState.Loading,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) { Text("Excluir") }
                        Button(onClick = onClose) { Text("Fechar") }
                    }
                }
                if (editState is UiState.Failure) {
                    Text("Erro ao editar categoria", color = Color.Red)
                }
                if (deleteState is UiState.Failure) {
                    Text("Erro ao excluir categoria", color = Color.Red)
                }
            }
        }

        else -> {}
    }
}
