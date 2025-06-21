package com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.places.addplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.data.model.Category
import com.luacheia.kmptravelapp.data.repository.CategoryRepository
import com.luacheia.kmptravelapp.data.repository.PlaceRepository
import com.luacheia.kmptravelapp.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddPlaceViewModel(
    private val placeRepository: PlaceRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiState: StateFlow<UiState<Unit>> = _uiState

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            categoryRepository.getRemoteCategories().collect { cats ->
                _categories.value = cats
            }
        }
    }

    fun addPlace(
        name: String,
        imageUrls: List<String>,
        description: String,
        address: String,
        city: String,
        categoryId: String,
        price: String
    ) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            placeRepository.createPlace(name, imageUrls, description, address, city, categoryId, price).collect { success ->
                _uiState.value = if (success) UiState.Success(Unit) else UiState.Failure(Exception("Erro ao adicionar lugar"))
            }
        }
    }

    fun resetState() {
        _uiState.value = UiState.Idle
    }
}
