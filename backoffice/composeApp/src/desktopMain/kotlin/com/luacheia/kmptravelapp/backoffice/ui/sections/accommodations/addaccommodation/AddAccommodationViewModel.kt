package com.luacheia.kmptravelapp.backoffice.ui.sections.accommodations.addaccommodation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.data.repository.AccommodationRepository
import com.luacheia.kmptravelapp.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddAccommodationViewModel(
    private val accommodationRepository: AccommodationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiState: StateFlow<UiState<Unit>> = _uiState

    fun addAccommodation(title: String, iconUrl: String, link: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            accommodationRepository.createAccommodation(title, iconUrl, link).collect { success ->
                _uiState.value = if (success) UiState.Success(Unit) else UiState.Failure(Exception("Erro ao adicionar acomodação"))
            }
        }
    }

    fun resetState() {
        _uiState.value = UiState.Idle
    }
}
