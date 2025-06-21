package com.luacheia.kmptravelapp.backoffice.ui.sections.accommodations.accommodationdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.data.model.Accommodation
import com.luacheia.kmptravelapp.data.repository.AccommodationRepository
import com.luacheia.kmptravelapp.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccommodationDetailViewModel(
    private val accommodationRepository: AccommodationRepository
) : ViewModel() {
    private val _accommodationState = MutableStateFlow<UiState<Accommodation>>(UiState.Idle)
    val accommodationState: StateFlow<UiState<Accommodation>> = _accommodationState
    private val _editState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val editState: StateFlow<UiState<Unit>> = _editState
    private val _deleteState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val deleteState: StateFlow<UiState<Unit>> = _deleteState

    fun loadAccommodationById(id: String) {
        viewModelScope.launch {
            _accommodationState.value = UiState.Loading
            accommodationRepository.getAccommodationById(id).collect { acc ->
                if (acc != null) {
                    _accommodationState.value = UiState.Success(acc)
                } else {
                    _accommodationState.value = UiState.Failure(Exception("Acomodação não encontrada"))
                }
            }
        }
    }

    fun updateAccommodation(accommodation: Accommodation) {
        viewModelScope.launch {
            _editState.value = UiState.Loading
            accommodationRepository.updateAccommodation(accommodation).collect { success ->
                if (success) {
                    _editState.value = UiState.Success(Unit)
                } else {
                    _editState.value = UiState.Failure(Exception("Erro ao editar acomodação"))
                }
            }
        }
    }

    fun deleteAccommodation(id: String) {
        viewModelScope.launch {
            _deleteState.value = UiState.Loading
            accommodationRepository.deleteAccommodation(id).collect { success ->
                if (success) {
                    _deleteState.value = UiState.Success(Unit)
                } else {
                    _deleteState.value = UiState.Failure(Exception("Erro ao deletar acomodação"))
                }
            }
        }
    }
}
