package com.luacheia.kmptravelapp.android.presentation.ui.accommodation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.data.model.Accommodation
import com.luacheia.kmptravelapp.data.repository.AccommodationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccommodationViewModel(
    private val accommodationRepository: AccommodationRepository
) : ViewModel() {

    private val _accommodationsModel = MutableStateFlow<List<Accommodation>?>(null)
    val accommodationsModel: StateFlow<List<Accommodation>?> get() = _accommodationsModel

    init {
        getAccommodations()
    }

    fun getAccommodations() {
        viewModelScope.launch {
            accommodationRepository.getAccommodations().collect { accommodations ->
                _accommodationsModel.value = accommodations
            }
        }
    }
}
