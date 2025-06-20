package com.luacheia.kmptravelapp.backoffice.sections.categoriesplaces.addcategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.data.model.Category
import com.luacheia.kmptravelapp.data.repository.CategoryRepository
import com.luacheia.kmptravelapp.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// usar viewmodels separados (single responsibility principle) para cada ação
class AddCategoryViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiState: StateFlow<UiState<Unit>> = _uiState

    fun addCategory(name: String, iconUrl: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            categoryRepository.createCategory(Category(name = name, iconUrl = iconUrl)).collect { success ->
                _uiState.value = if (success) UiState.Success(Unit) else UiState.Failure(Exception("Erro ao adicionar categoria"))
            }
        }
    }

    fun resetState() {
        _uiState.value = UiState.Idle
    }
}