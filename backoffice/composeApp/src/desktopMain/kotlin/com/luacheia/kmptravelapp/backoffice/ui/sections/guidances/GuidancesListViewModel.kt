package com.luacheia.kmptravelapp.backoffice.ui.sections.guidances

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.data.model.Guidance
import com.luacheia.kmptravelapp.data.repository.GuidanceRepository
import com.luacheia.kmptravelapp.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GuidancesListViewModel(
    private val guidanceRepository: GuidanceRepository
) : ViewModel() {
    private val _guidancesState = MutableStateFlow<UiState<List<Guidance>>>(UiState.Idle)
    val guidancesState: StateFlow<UiState<List<Guidance>>> = _guidancesState

    init {
        fetchGuidances()
    }

    fun fetchGuidances() {
        viewModelScope.launch {
            guidanceRepository.getRemoteGuidances().collect { list ->
                _guidancesState.value = UiState.Success(list)
            }
        }
    }
}
