package com.luacheia.kmptravelapp.backoffice.ui.sections.guidances

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.data.model.Guidance
import com.luacheia.kmptravelapp.data.repository.GuidanceRepository
import com.luacheia.kmptravelapp.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GuidanceDetailViewModel(
    private val guidanceRepository: GuidanceRepository
) : ViewModel() {
    private val _guidanceState = MutableStateFlow<UiState<Guidance>>(UiState.Idle)
    val guidanceState: StateFlow<UiState<Guidance>> = _guidanceState
    private val _editState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val editState: StateFlow<UiState<Unit>> = _editState
    private val _deleteState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val deleteState: StateFlow<UiState<Unit>> = _deleteState

    fun loadGuidanceById(id: String) {
        viewModelScope.launch {
            _guidanceState.value = UiState.Loading
            guidanceRepository.getGuidanceById(id).collect { g ->
                if (g != null) {
                    _guidanceState.value = UiState.Success(g)
                } else {
                    _guidanceState.value = UiState.Failure(Exception("Recomendação não encontrada"))
                }
            }
        }
    }

    fun updateGuidance(guidance: Guidance) {
        viewModelScope.launch {
            _editState.value = UiState.Loading
            guidanceRepository.updateGuidance(guidance).collect { success ->
                if (success) {
                    _editState.value = UiState.Success(Unit)
                } else {
                    _editState.value = UiState.Failure(Exception("Erro ao editar recomendação"))
                }
            }
        }
    }

    fun deleteGuidance(id: String) {
        viewModelScope.launch {
            _deleteState.value = UiState.Loading
            guidanceRepository.deleteGuidance(id).collect { success ->
                if (success) {
                    _deleteState.value = UiState.Success(Unit)
                } else {
                    _deleteState.value = UiState.Failure(Exception("Erro ao deletar recomendação"))
                }
            }
        }
    }
}
