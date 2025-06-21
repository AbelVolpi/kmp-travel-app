package com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.places.placedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.data.model.Category
import com.luacheia.kmptravelapp.data.model.Place
import com.luacheia.kmptravelapp.data.repository.CategoryRepository
import com.luacheia.kmptravelapp.data.repository.PlaceRepository
import com.luacheia.kmptravelapp.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaceDetailViewModel(
    private val placeRepository: PlaceRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private val _placeDetailState = MutableStateFlow<UiState<Place>>(UiState.Success(Place()))
    val placeDetailState: StateFlow<UiState<Place>> = _placeDetailState
    private val _editPlaceState = MutableStateFlow<UiState<Unit>>(UiState.Success(Unit))
    val editPlaceState: StateFlow<UiState<Unit>> = _editPlaceState
    private val _deletePlaceState = MutableStateFlow<UiState<Unit>>(UiState.Success(Unit))
    val deletePlaceState: StateFlow<UiState<Unit>> = _deletePlaceState

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

    fun loadPlaceById(placeId: String) {
        viewModelScope.launch {
            _placeDetailState.value = UiState.Loading
            placeRepository.getPlaceById(placeId).collect { place ->
                if (place != null) {
                    _placeDetailState.value = UiState.Success(place)
                } else {
                    _placeDetailState.value = UiState.Failure(Exception("Lugar não encontrado"))
                }
            }
        }
    }

    fun updatePlace(place: Place) {
        viewModelScope.launch {
            _editPlaceState.value = UiState.Loading
            placeRepository.updatePlace(place).collect { success ->
                if (success) {
                    _editPlaceState.value = UiState.Success(Unit)
                } else {
                    _editPlaceState.value = UiState.Failure(Exception("Erro ao editar lugar"))
                }
            }
        }
    }

    fun deletePlace(placeId: String) {
        viewModelScope.launch {
            _deletePlaceState.value = UiState.Loading
            placeRepository.deletePlace(placeId).collect { success ->
                if (success) {
                    _deletePlaceState.value = UiState.Success(Unit)
                } else {
                    _deletePlaceState.value = UiState.Failure(Exception("Erro ao deletar lugar"))
                }
            }
        }
    }
}
