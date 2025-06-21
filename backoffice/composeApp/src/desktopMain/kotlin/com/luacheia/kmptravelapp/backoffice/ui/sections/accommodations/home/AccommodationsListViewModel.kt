package com.luacheia.kmptravelapp.backoffice.ui.sections.accommodations.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.data.model.Accommodation
import com.luacheia.kmptravelapp.data.repository.AccommodationRepository
import com.luacheia.kmptravelapp.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccommodationsListViewModel(
    private val accommodationRepository: AccommodationRepository
) : ViewModel() {
    private val _accommodationsState = MutableStateFlow<UiState<List<Accommodation>>>(UiState.Loading)
    val accommodationsState: StateFlow<UiState<List<Accommodation>>> = _accommodationsState

    init {
        fetchAccommodations()
    }

    fun fetchAccommodations() {
        viewModelScope.launch {
            accommodationRepository.getRemoteAccommodations().collect { list ->
                _accommodationsState.value = UiState.Success(list)
            }
        }
    }
}
