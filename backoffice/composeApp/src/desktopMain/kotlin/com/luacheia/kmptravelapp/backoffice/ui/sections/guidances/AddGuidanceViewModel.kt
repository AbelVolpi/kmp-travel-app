package com.luacheia.kmptravelapp.backoffice.ui.sections.guidances

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.data.repository.GuidanceRepository
import com.luacheia.kmptravelapp.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddGuidanceViewModel(
    private val guidanceRepository: GuidanceRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiState: StateFlow<UiState<Unit>> = _uiState

    fun addGuidance(title: String, subtitle: String, iconUrl: String, description: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            // Implemente o método createGuidance no GuidanceRepository
            guidanceRepository.createGuidance(title, subtitle, iconUrl, description).collect { success ->
                _uiState.value = if (success) UiState.Success(Unit) else UiState.Failure(Exception("Erro ao adicionar recomendação"))
            }
        }
    }

    fun resetState() {
        _uiState.value = UiState.Idle
    }
}
